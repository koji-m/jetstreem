package pw.koj.jetstreem.runtime;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import pw.koj.jetstreem.runtime.type.*;

import static java.lang.invoke.MethodType.methodType;

public class BoolSupport {

    private static final MethodHandle TEST;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            TEST = lookup.findStatic(
                BoolSupport.class,
                "testify",
                methodType(boolean.class, Object.class));
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException("method handle initialization failed");
        }
    }

    public static CallSite bootstrap(Lookup caller,
                                     String name,
                                     MethodType type) throws Throwable {
        
        return new ConstantCallSite(TEST);
    }

    public static boolean testify(Object operand) throws Throwable {

        if (operand instanceof StrmBool) {
            return ((StrmBool)operand).value();
        }
        else  {
            return false;
        }
    }
    
}


