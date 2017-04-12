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
        byte[] b = dump03();
        fos.write(b);
        fos = new FileOutputStream("StrmTop$ns1.class", false);
        b = dump03inner();
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
         * foo(24)  #repeat 10 times
         *
         * #java#
         * public class StrmTop {
         *     private static StrmFunction foo;
         *
         *     public static void main(String[] args) {
         *         foo = (n) -> Sytem.out.println(n);
         *         x = 24
         *
         *         for (int i = 0; i < 10; i++) {
         *             foo(x); //INDY
         *         }
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
        mv.visitTypeInsn(NEW, "java/lang/Integer");
        mv.visitInsn(DUP);
        mv.visitIntInsn(BIPUSH, 24);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V", false);
        mv.visitVarInsn(ASTORE, 1);
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ISTORE, 2);
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"Ljava/lang/Integer;", Opcodes.INTEGER}, 0, null);
        mv.visitVarInsn(ILOAD, 2);
        mv.visitIntInsn(BIPUSH, 10);
        Label l1 = new Label();
        mv.visitJumpInsn(IF_ICMPGE, l1);
        mv.visitFieldInsn(GETSTATIC, className, "foo", "LStrmFunction;");
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInvokeDynamicInsn("foo", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", new Handle(H_INVOKESTATIC, "DynamicDispatchSupport", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false));
        mv.visitInsn(POP);
        mv.visitIincInsn(2, 1);
        mv.visitJumpInsn(GOTO, l0);
        mv.visitLabel(l1);
        mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);

        mv.visitInsn(RETURN);
        mv.visitMaxs(4, 3);
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

    public static byte[] dump03() throws Exception {
        /* #streem#
         * namespace ns1 {
         *   method foo(n) {
         *     println(n)
         *   }
         * }
         *
         * def foo(self, n) {
         *   println("not good")
         * }
         *
         * obj = new ns1 [1,2]
         * foo(obj, 24)  #repeat 10 times
         *
         * #java#
         * public class StrmTop {
         *     static class ns1 {
         *         public static StrmFunction foo;
         *
         *         public static void define() {
         *             foo = (self, n) -> Sytem.out.println(n);
         *         }
         *     }
         *
         *     public static void main(String[] args) {
         *         StrmTop.ns1.define();
         *         ns1 obj = new ns1([1,2]);
         *         for (int i = 0; i < 10; i++) {
         *             foo(obj, 24); //INDY
         *         }
         *     }
         * }
         */
        //from here
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

        cw.visitInnerClass(className + "$ns1", "StrmTop", "ns1", ACC_STATIC);
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
        mv.visitMethodInsn(INVOKESTATIC, "StrmTop$ns1", "define", "()V", false);
        mv.visitInvokeDynamicInsn("call", "()LStrmFunction;", new Handle(H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;"), new Object[]{Type.getType("()Ljava/lang/Object;"), new Handle(H_INVOKESTATIC, className, "lambda$main$0", "()Ljava/lang/Object;"), Type.getType("()Ljava/lang/Object;")});
        mv.visitFieldInsn(PUTSTATIC, className, "foo", "LStrmFunction;");
        mv.visitTypeInsn(NEW, "java/lang/Integer");
        mv.visitInsn(DUP);
        mv.visitIntInsn(BIPUSH, 24);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V", false);
        mv.visitVarInsn(ASTORE, 1);
        mv.visitTypeInsn(NEW, "StrmTop$ns1");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "StrmTop$ns1", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 2);
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ISTORE, 3);
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"Ljava/lang/Integer;", "LStrmTop$ns1;", Opcodes.INTEGER}, 0, null);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitIntInsn(BIPUSH, 10);
        Label l1 = new Label();
        mv.visitJumpInsn(IF_ICMPGE, l1);
        mv.visitFieldInsn(GETSTATIC, className, "foo", "LStrmFunction;");
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInvokeDynamicInsn("foo", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", new Handle(H_INVOKESTATIC, "DynamicDispatchSupport", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false));
        mv.visitInsn(POP);
        mv.visitIincInsn(3, 1);
        mv.visitJumpInsn(GOTO, l0);
        mv.visitLabel(l1);
        mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);

        mv.visitInsn(RETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    
        mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC, "lambda$main$0", "()Ljava/lang/Object;", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("not good");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
        mv.visitInsn(ACONST_NULL);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();
    
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dump03inner() throws Exception {
        /* #streem#
         * namespace ns1 {
         *   method foo(n) {
         *     println(n)
         *   }
         * }
         *
         * def foo(self, n) {
         *   println("not good")
         * }
         *
         * obj = new ns1 [1,2]
         * foo(obj, 24)  #repeat 10 times
         *
         * #java#
         * public class StrmTop {
         *     static class ns1 {
         *         public static StrmFunction foo;
         *
         *         public static void define() {
         *             foo = (self, n) -> Sytem.out.println(n);
         *         }
         *     }
         *
         *     public static void main(String[] args) {
         *         StrmTop.ns1.define();
         *         ns1 obj = new ns1([1,2]);
         *         for (int i = 0; i < 10; i++) {
         *             foo(obj, 24); //INDY
         *         }
         *     }
         * }
         */
        //from here
        String className = "StrmTop$ns1";

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

        cw.visit(52, ACC_SUPER, className, null, "StrmObject", null);

        cw.visitInnerClass(className, "StrmTop", "ns1", ACC_STATIC);
        cw.visitInnerClass("java/lang/invoke/MethodHandles$Lookup", "java/lang/invoke/MethodHandles", "Lookup", ACC_PUBLIC + ACC_FINAL + ACC_STATIC);
        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "foo", "LStrmFunction;", null, null);
        fv.visitEnd();

        mv = cw.visitMethod(0, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "StrmObject", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "define", "()V", null, null);
        mv.visitCode();
        mv.visitInvokeDynamicInsn("call", "()LStrmFunction;", new Handle(H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;"), new Object[]{Type.getType("([Ljava/lang/Object;)Ljava/lang/Object;"), new Handle(H_INVOKESTATIC, className, "lambda$define$0", "([Ljava/lang/Object;)Ljava/lang/Object;"), Type.getType("([Ljava/lang/Object;)Ljava/lang/Object;")});
        mv.visitFieldInsn(PUTSTATIC, className, "foo", "LStrmFunction;");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 0);
        mv.visitEnd();
    
        mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC, "lambda$define$0", "([Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitInsn(ICONST_1);
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

