import java.io.*;
import java.util.*;
import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import org.objectweb.asm.*;

import static java.lang.invoke.MethodType.methodType;
import static org.objectweb.asm.Opcodes.*;

public class DynamicDispatchSupportTest {
    public static void main(String[] args) throws Throwable {
        FileOutputStream fos = new FileOutputStream("StrmTop.class", false);
        byte[] b = dump02();
        fos.write(b);
    }

    public static byte[] dump01() throws Exception {
        /* #streem#
         * def foo() {
         *   println("hello")
         * }
         *
         * foo()
         *
         * #java#
         * public class StrmTop {
         *     private static StrmFunctionVoid foo;
         *
         *     public static void main(String[] args) {
         *         foo = () -> Sytem.out.println("hello");
         *
         *         foo(); //INDY
         *     }
         * }
         */

        String className = "StrmTop";

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        FieldVisitor fv;

        MethodType bsmMethodType = methodType(
            CallSite.class,
            Lookup.class,
            String.class,
            MethodType.class);

        Handle bsmMethodHandle = new Handle(
            H_INVOKESTATIC,
            DynamicDispatchSupport.class.getName().replace('.', '/'),
            "bootstrap",
            bsmMethodType.toMethodDescriptorString());

        cw.visit(52, ACC_SUPER, className, null, "java/lang/Object", null);

        cw.visitInnerClass("java/lang/invoke/MethodHandles$Lookup", "java/lang/invoke/MethodHandles", "Lookup", ACC_PUBLIC + ACC_FINAL + ACC_STATIC);
        fv = cw.visitField(ACC_PRIVATE + ACC_STATIC, "foo", "LStrmFunctionVoid;", null, null);
        fv.visitEnd();

        mv = cw.visitMethod(0, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitInvokeDynamicInsn("call", "()LStrmFunctionVoid;", new Handle(H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;"), new Object[]{Type.getType("()Ljava/lang/Object;"), new Handle(H_INVOKESTATIC, className, "lambda$main$0", "()Ljava/lang/Object;"), Type.getType("()Ljava/lang/Object;")});
        mv.visitFieldInsn(PUTSTATIC, className, "foo", "LStrmFunctionVoid;");
        mv.visitFieldInsn(GETSTATIC, className, "foo", "LStrmFunctionVoid;");
        mv.visitInvokeDynamicInsn("foo", "(Ljava/lang/Object;)Ljava/lang/Object;", new Handle(H_INVOKESTATIC, "DynamicDispatchSupport", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false));
        //mv.visitMethodInsn(INVOKEINTERFACE, "StrmFunctionVoid", "call", "()Ljava/lang/Object;", true);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    
        mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC, "lambda$main$0", "()Ljava/lang/Object;", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("hello");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ACONST_NULL);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();
    
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dump02() throws Exception {
        /* #streem#
         * def foo(n) {
         *   println(n)
         * }
         *
         * foo(24)
         *
         * #java#
         * public class StrmTop {
         *     private static StrmFunction foo;
         *
         *     public static void main(String[] args) {
         *         foo = (n) -> Sytem.out.println(n);
         *
         *         foo(24); //INDY
         *     }
         * }
         */
        String className = "StrmTop";

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        FieldVisitor fv;

        MethodType bsmMethodType = methodType(
            CallSite.class,
            Lookup.class,
            String.class,
            MethodType.class);

        Handle bsmMethodHandle = new Handle(
            H_INVOKESTATIC,
            DynamicDispatchSupport.class.getName().replace('.', '/'),
            "bootstrap",
            bsmMethodType.toMethodDescriptorString());

        cw.visit(52, ACC_SUPER, className, null, "java/lang/Object", null);

        cw.visitInnerClass("java/lang/invoke/MethodHandles$Lookup", "java/lang/invoke/MethodHandles", "Lookup", ACC_PUBLIC + ACC_FINAL + ACC_STATIC);
        fv = cw.visitField(ACC_PRIVATE + ACC_STATIC, "foo", "LStrmFunction;", null, null);
        fv.visitEnd();

        mv = cw.visitMethod(0, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitInvokeDynamicInsn("call", "()LStrmFunction;", new Handle(H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;"), new Object[]{Type.getType("([Ljava/lang/Object;)Ljava/lang/Object;"), new Handle(H_INVOKESTATIC, className, "lambda$main$0", "([Ljava/lang/Object;)Ljava/lang/Object;"), Type.getType("([Ljava/lang/Object;)Ljava/lang/Object;")});
        mv.visitFieldInsn(PUTSTATIC, className, "foo", "LStrmFunction;");
        mv.visitFieldInsn(GETSTATIC, className, "foo", "LStrmFunction;");
        mv.visitTypeInsn(NEW, "java/lang/Integer");
        mv.visitInsn(DUP);
        mv.visitIntInsn(BIPUSH, 24);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V", false);
        mv.visitInvokeDynamicInsn("foo", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", new Handle(H_INVOKESTATIC, "DynamicDispatchSupport", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false));
        mv.visitInsn(RETURN);
        mv.visitMaxs(4, 1);
        mv.visitEnd();
    
        mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC, "lambda$main$0", "([Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitInsn(ICONST_0);
        mv.visitInsn(AALOAD);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
        mv.visitInsn(ACONST_NULL);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(3, 1);
        mv.visitEnd();
    
        cw.visitEnd();

        return cw.toByteArray();
    }


}

