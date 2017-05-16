import java.util.*;
import java.lang.invoke.*;
import org.objectweb.asm.*;

public class IndyTestFactory implements Opcodes {

    public static byte[] dump () throws Exception {
        String className = "TestIndy";
        String methodName = "callIndy";

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        MethodType bsmMethodType = MethodType.methodType(
              CallSite.class,
              MethodHandles.Lookup.class,
              String.class,
              MethodType.class);

        Handle bsmMethodHandle = new Handle(
              H_INVOKESTATIC,
              IndyTestMethodFactory.class.getName().replace('.', '/'),
              "bootstrap",
              bsmMethodType.toMethodDescriptorString());

        cw.visit(52, ACC_SUPER, className, null, "java/lang/Object", null);

        mv = cw.visitMethod(0, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitIntInsn(BIPUSH, 24);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitIntInsn(BIPUSH, 48);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitMethodInsn(INVOKESTATIC, className, "test", "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(3, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "test", "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInvokeDynamicInsn(
              "adder",
              "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;",
              bsmMethodHandle);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        cw.visitEnd();

        return cw.toByteArray();
    }
}
