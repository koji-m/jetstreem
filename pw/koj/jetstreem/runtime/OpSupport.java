package pw.koj.jetstreem.runtime;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import pw.koj.jetstreem.runtime.type.*;
import static java.lang.invoke.MethodType.methodType;

public class OpSupport {

    private static final MethodHandle UNARY_OP;
    private static final MethodHandle BINARY_OP;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            UNARY_OP = lookup.findStatic(
                OpSupport.class,
                "unaryOp",
                methodType(Object.class, StrmCallSite.class, String.class, Integer.class, SwitchPoint[].class, Object.class));

            BINARY_OP = lookup.findStatic(
                OpSupport.class,
                "binaryOp",
                methodType(Object.class, StrmCallSite.class, String.class, Integer.class, SwitchPoint[].class, Object.class, Object.class));
/*
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
                */
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("method handle initialization failed");
        }
    }

    public static CallSite bootstrap(Lookup caller,
                                     String name,
                                     MethodType type,
                                     Integer idx) throws Throwable {
        StrmCallSite callSite = new StrmCallSite(caller, type);
        MethodHandle fallbackHandle;
        int nOperand = type.parameterCount();
        if (nOperand == 1) {
            fallbackHandle = UNARY_OP.bindTo(callSite)
                                     .bindTo(name)
                                     .bindTo(idx)
                                     .asType(type);
        }
        else {
            fallbackHandle = BINARY_OP.bindTo(callSite)
                                      .bindTo(name)
                                      .bindTo(idx)
                                      .asType(type);
        }

        callSite.fallback = fallbackHandle;
        callSite.setTarget(fallbackHandle);


        return callSite;
    }

    public static Object unaryOp(StrmCallSite callSite, String name, Integer idx, SwitchPoint[] swps, Object operand) throws Throwable {
        MethodHandle mh;

        if (operand instanceof StrmInteger) {
            mh = callSite.lookup.findVirtual(
                    StrmInteger.class,
                    name,
                    MethodType.methodType(StrmInteger.class));
        }
        else if (operand instanceof StrmFloat) {
            mh = callSite.lookup.findVirtual(
                    StrmFloat.class,
                    name,
                    MethodType.methodType(StrmFloat.class));
        }
        else if (operand instanceof StrmString) {
            mh = callSite.lookup.findVirtual(
                    StrmString.class,
                    name,
                    MethodType.methodType(StrmString.class));
        }
        else {
            throw new RuntimeException("unary operation error");
        }

        mh = MethodHandles.dropArguments(mh, 0, SwitchPoint[].class);
        mh = mh.asType(callSite.type());
        SwitchPoint swp = new SwitchPoint();
        swps[idx.intValue()] = swp;
        mh = swp.guardWithTest(mh, callSite.fallback);
        callSite.setTarget(mh);
        return mh.invokeExact(swps, operand);
    }

    public static Object binaryOp(StrmCallSite callSite, String name, Integer idx, SwitchPoint[] swps, Object lhs, Object rhs) throws Throwable {
        MethodHandle mh = null;

        MethodType type = MethodType.methodType(Object.class, Object.class);

        if (lhs instanceof StrmInteger) {
            if (rhs instanceof StrmInteger) {
                mh = callSite.lookup.findVirtual(
                        StrmInteger.class,
                        name,
                        MethodType.methodType(StrmInteger.class, StrmInteger.class));
            }
            else if (rhs instanceof StrmFloat) {
                mh = callSite.lookup.findVirtual(
                        StrmInteger.class,
                        name,
                        MethodType.methodType(StrmFloat.class, StrmFloat.class));
            }
        }
        else if (lhs instanceof StrmFloat) {
            if (rhs instanceof StrmInteger) {
                mh = callSite.lookup.findVirtual(
                        StrmFloat.class,
                        name,
                        MethodType.methodType(StrmFloat.class, StrmInteger.class));
            }
            else if (rhs instanceof StrmFloat) {
                mh = callSite.lookup.findVirtual(
                        StrmFloat.class,
                        name,
                        MethodType.methodType(StrmFloat.class, StrmFloat.class));
            }
        }
        else if (lhs instanceof StrmString) {
            mh = callSite.lookup.findVirtual(
                    StrmString.class,
                    name,
                    MethodType.methodType(StrmString.class, StrmString.class));
        }

        if (mh == null)  {
            throw new RuntimeException("binary operation error");
        }

        mh = MethodHandles.dropArguments(mh, 0, SwitchPoint[].class);
        mh = mh.asType(callSite.type());
        SwitchPoint swp = new SwitchPoint();
        swps[idx.intValue()] = swp;
        mh = swp.guardWithTest(mh, callSite.fallback);
        callSite.setTarget(mh);
        return mh.invokeExact(swps, lhs, rhs);
    }
}


