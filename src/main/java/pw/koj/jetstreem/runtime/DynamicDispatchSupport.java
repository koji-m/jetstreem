package pw.koj.jetstreem.runtime;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import pw.koj.jetstreem.runtime.type.*;

import static java.lang.invoke.MethodType.methodType;

public class DynamicDispatchSupport {

    private static final MethodHandle FALLBACK;
    private static final MethodHandle FALLBACK_VOID;
    private static final MethodHandle GUARD;
    private static final MethodHandle INVOKER;
    private static final MethodHandle INVOKER_VOID;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            FALLBACK = lookup.findStatic(
                DynamicDispatchSupport.class,
                "fallback",
                methodType(Object.class, StrmCallSite.class, String.class, Object[].class));

            FALLBACK_VOID = lookup.findStatic(
                DynamicDispatchSupport.class,
                "fallback",
                methodType(Object.class, StrmCallSite.class, String.class, Object.class));

            GUARD = lookup.findStatic(
                DynamicDispatchSupport.class,
                "guard",
                methodType(boolean.class, Object.class, Object.class));

            INVOKER = lookup.findStatic(
                DynamicDispatchSupport.class,
                "invoker",
                methodType(Object.class, StrmFunction.class, Object[].class));

            INVOKER_VOID = lookup.findStatic(
                DynamicDispatchSupport.class,
                "invoker",
                methodType(Object.class, StrmFunctionVoid.class));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("method handle initialization failed");
        }
    }

    public static CallSite bootstrap(Lookup caller,
                                     String name,
                                     MethodType type) throws Throwable {
        StrmCallSite callSite = new StrmCallSite(caller, type);
        MethodHandle fallbackHandle;
        int nArgs = type.parameterCount();
        if (nArgs == 1) {
            fallbackHandle = FALLBACK_VOID.bindTo(callSite)
                                          .bindTo(name)
                                          .asType(type);
        }
        else {
            fallbackHandle = FALLBACK.bindTo(callSite)
                                     .bindTo(name)
                                     .asCollector(Object[].class, nArgs)
                                     .asType(type);
        }

        callSite.fallback = fallbackHandle;
        callSite.setTarget(fallbackHandle);


        return callSite;
    }

    public static boolean guard(Object expect, Object actual) {
        return expect == actual;
    }

    public static Object invoker(StrmFunction fn, Object[] args) {
        System.out.println("invoker called");
        return fn.call(args);
    }

    public static Object invoker(StrmFunctionVoid fn) {
        return fn.call();
    }

    public static Object fallback(StrmCallSite callSite, String name, Object fallbackRef) {
        if (callSite.fallbackFuncVoid != null) {
            return callSite.fallbackFuncVoid.call();
        }
        else if (fallbackRef instanceof StrmFunctionVoid) {
            MethodHandle invoker = INVOKER_VOID.bindTo((StrmFunctionVoid)fallbackRef);
            invoker = MethodHandles.dropArguments(invoker, 0, Object.class);
            callSite.setTarget(invoker);
            callSite.fallbackFuncVoid = (StrmFunctionVoid)fallbackRef;
            return ((StrmFunctionVoid)fallbackRef).call();
        }
        else if (fallbackRef instanceof GenericFunction) {
            GenericFunction genfn = (GenericFunction)fallbackRef;
            Object[] newArgs = new Object[1];
            newArgs[0] = genfn.getFallbackRef();
            return fallback(callSite, genfn.getName(), newArgs);
        }
        else {
            throw new RuntimeException("no function found");
        }
    }

    public static Object fallback(StrmCallSite callSite, String name, Object[] args) {
        Object fallbackRef = args[0];
        Object firstArg = args[1];
        Object fn = null;
        
        MethodHandle mh = null;
        if (firstArg instanceof StrmObject) {
            StrmObject obj = (StrmObject)firstArg;
            for (Class klass = obj.getClass(); klass != null; klass = klass.getDeclaringClass()) {
                try {
                    mh = callSite.lookup.findStaticGetter(
                        klass,
                        name,
                        StrmFunction.class);
                    fn = mh.invoke();
                    break;
                } catch (Throwable e1) {
                    try {
                        mh = callSite.lookup.findStaticGetter(
                                klass,
                                name,
                                GenericFunction.class);
                        fn = mh.invoke();
                        break;
                    } catch (Throwable e2) {
                        System.out.println("lookup next");
                        //TBD
                    }
                }
            }
        }

        if (fn == null) {
            if (callSite.fallbackFunc != null) {
              Object[] ar = new Object[args.length-1];
              System.arraycopy(args, 1, ar, 0, ar.length);
              return callSite.fallbackFunc.call(ar);
            }
            else if (fallbackRef instanceof StrmFunction) {
                MethodHandle guard = GUARD.bindTo(firstArg);
                guard = MethodHandles.dropArguments(guard, 0, Object.class);

                MethodHandle invoker = INVOKER.bindTo((StrmFunction)fallbackRef)
                                 .asCollector(Object[].class, callSite.type().parameterCount() - 1);
                invoker = MethodHandles.dropArguments(invoker, 0, Object.class);

                MethodHandle root = MethodHandles.guardWithTest(guard, invoker, callSite.fallback);
                callSite.setTarget(root);
                callSite.fallbackFunc = (StrmFunction)fallbackRef;
                Object[] ar = new Object[args.length-1];
                System.arraycopy(args, 1, ar, 0, ar.length);
                return ((StrmFunction)fallbackRef).call(ar);
            }
            else if (fallbackRef instanceof GenericFunction) {
                GenericFunction genfn = (GenericFunction)fallbackRef;
                Object[] newArgs = new Object[args.length];
                System.arraycopy(args, 1, newArgs, 1, args.length - 1);
                newArgs[0] = genfn.getFallbackRef();
                // concern infinite loop (ex. a = &b b = &a)
                return fallback(callSite, genfn.getName(), newArgs);
            }
            else {
                try {
                    mh = callSite.lookup.findStaticGetter(
                        StrmNamespace.class,
                        name,
                        StrmFunction.class);
                    fn = mh.invoke();

                    MethodHandle guard = GUARD.bindTo(firstArg);
                    guard = MethodHandles.dropArguments(guard, 0, Object.class);

                    MethodHandle invoker = INVOKER.bindTo((StrmFunction)fn)
                                     .asCollector(Object[].class, callSite.type().parameterCount() - 1);
                    invoker = MethodHandles.dropArguments(invoker, 0, Object.class);

                    MethodHandle root = MethodHandles.guardWithTest(guard, invoker, callSite.fallback);
                    callSite.setTarget(root);
                    Object[] ar = new Object[args.length-1];
                    System.arraycopy(args, 1, ar, 0, ar.length);
                    return ((StrmFunction)fn).call(ar);
                } catch (Throwable e) {
                    throw new RuntimeException("no function found");
                }
            }
        }
        else {
            if (fn instanceof StrmFunction) {
                MethodHandle guard = GUARD.bindTo(firstArg)
                                          .asType(methodType(boolean.class, Object.class));
                guard = MethodHandles.dropArguments(guard, 0, Object.class);

                MethodHandle invoker = INVOKER.bindTo((StrmFunction)fn)
                                 .asCollector(Object[].class, callSite.type().parameterCount() - 1);
                invoker = MethodHandles.dropArguments(invoker, 0, Object.class);

                MethodHandle root = MethodHandles.guardWithTest(guard, invoker, callSite.fallback);
                callSite.setTarget(root);
                Object[] ar = new Object[args.length-1];
                System.arraycopy(args, 1, ar, 0, ar.length);
                return ((StrmFunction)fn).call(ar);
            }
            else if (fn instanceof GenericFunction) {
                GenericFunction genfn = (GenericFunction)fn;
                Object[] newArgs = new Object[args.length];
                System.arraycopy(args, 1, newArgs, 1, args.length - 1);
                newArgs[0] = genfn.getFallbackRef();
                // concern infinite loop (ex. a = &b b = &a)
                return fallback(callSite, genfn.getName(), newArgs);
            }
            else {
                throw new RuntimeException("no function found");
            }
        }
    }

}

