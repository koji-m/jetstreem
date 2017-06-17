package generator

import java.io.*
import org.objectweb.asm.*

import static org.objectweb.asm.Opcodes.*
import static org.objectweb.asm.ClassWriter.*

def genStrmMapOperator(dir) {
    cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS)

    cw.visit(52, ACC_PUBLIC + ACC_FINAL + ACC_SUPER, "pw/koj/jetstreem/runtime/type/StrmMapOperator", "Ljava/lang/Object;Lio/reactivex/FlowableOperator<Ljava/lang/Object;Ljava/lang/Object;>;Lpw/koj/jetstreem/runtime/type/StrmFilter;", "java/lang/Object", ["io/reactivex/FlowableOperator", "pw/koj/jetstreem/runtime/type/StrmFilter"] as String[])

    cw.visitInnerClass("pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "pw/koj/jetstreem/runtime/type/StrmMapOperator", "MapOp", ACC_FINAL + ACC_STATIC)
    
    fv = cw.visitField(ACC_PRIVATE, "mapper", "Lpw/koj/jetstreem/runtime/type/StrmFunction;", null, null)
    fv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lpw/koj/jetstreem/runtime/type/StrmFunction;)V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
    mv.visitVarInsn(ALOAD, 0)
    mv.visitVarInsn(ALOAD, 1)
    mv.visitFieldInsn(PUTFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator", "mapper", "Lpw/koj/jetstreem/runtime/type/StrmFunction;")
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "apply", "(Lorg/reactivestreams/Subscriber;)Lorg/reactivestreams/Subscriber;", "(Lorg/reactivestreams/Subscriber<-Ljava/lang/Object;>;)Lorg/reactivestreams/Subscriber<-Ljava/lang/Object;>;", null)
    mv.visitCode()
    mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp")
    mv.visitInsn(DUP)
    mv.visitVarInsn(ALOAD, 1)
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator", "mapper", "Lpw/koj/jetstreem/runtime/type/StrmFunction;")
    mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "<init>", "(Lorg/reactivestreams/Subscriber;Lpw/koj/jetstreem/runtime/type/StrmFunction;)V", false)
    mv.visitInsn(ARETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "hangOn", "(Lio/reactivex/Flowable;)Lio/reactivex/Flowable;", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 1)
    mv.visitMethodInsn(INVOKESTATIC, "io/reactivex/schedulers/Schedulers", "computation", "()Lio/reactivex/Scheduler;", false);
    mv.visitMethodInsn(INVOKEVIRTUAL, "io/reactivex/Flowable", "observeOn", "(Lio/reactivex/Scheduler;)Lio/reactivex/Flowable;", false)
    mv.visitVarInsn(ALOAD, 0)
    mv.visitMethodInsn(INVOKEVIRTUAL, "io/reactivex/Flowable", "lift", "(Lio/reactivex/FlowableOperator;)Lio/reactivex/Flowable;", false)
    mv.visitInsn(ARETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    cw.visitEnd()

    b = cw.toByteArray();

    output = new FileOutputStream(dir + "StrmMapOperator.class")
    output.write(b)
}

def genMapOp(dir) {
    cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS)

    cw.visit(52, ACC_FINAL + ACC_SUPER, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "Ljava/lang/Object;Lorg/reactivestreams/Subscriber<Ljava/lang/Object;>;Lorg/reactivestreams/Subscription;", "java/lang/Object", ["org/reactivestreams/Subscriber", "org/reactivestreams/Subscription"] as String[])

    cw.visitInnerClass("pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "pw/koj/jetstreem/runtime/type/StrmMapOperator", "MapOp", ACC_FINAL + ACC_STATIC)

    fv = cw.visitField(ACC_FINAL, "actual", "Lorg/reactivestreams/Subscriber;", "Lorg/reactivestreams/Subscriber<-Ljava/lang/Object;>;", null)
    fv.visitEnd()
    
    fv = cw.visitField(0, "s", "Lorg/reactivestreams/Subscription;", null, null)
    fv.visitEnd()
    
    fv = cw.visitField(0, "mapper", "Lpw/koj/jetstreem/runtime/type/StrmFunction;", null, null)
    fv.visitEnd()
    
    fv = cw.visitField(0, "args", "[Ljava/lang/Object;", null, null)
    fv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/reactivestreams/Subscriber;Lpw/koj/jetstreem/runtime/type/StrmFunction;)V", "(Lorg/reactivestreams/Subscriber<-Ljava/lang/Object;>;Lpw/koj/jetstreem/runtime/type/StrmFunction;)V", null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
    mv.visitVarInsn(ALOAD, 0)
    mv.visitVarInsn(ALOAD, 1)
    mv.visitFieldInsn(PUTFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "actual", "Lorg/reactivestreams/Subscriber;")
    mv.visitVarInsn(ALOAD, 0)
    mv.visitVarInsn(ALOAD, 2)
    mv.visitFieldInsn(PUTFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "mapper", "Lpw/koj/jetstreem/runtime/type/StrmFunction;")
    mv.visitVarInsn(ALOAD, 0)
    mv.visitInsn(ICONST_2)
    mv.visitTypeInsn(ANEWARRAY, "java/lang/Object")
    mv.visitInsn(DUP)
    mv.visitInsn(ICONST_0)
    mv.visitVarInsn(ALOAD, 1)
    mv.visitInsn(AASTORE)
    mv.visitInsn(DUP)
    mv.visitInsn(ICONST_1)
    mv.visitInsn(ACONST_NULL)
    mv.visitInsn(AASTORE)
    mv.visitFieldInsn(PUTFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "args", "[Ljava/lang/Object;")
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "onSubscribe", "(Lorg/reactivestreams/Subscription;)V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitVarInsn(ALOAD, 1)
    mv.visitFieldInsn(PUTFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "s", "Lorg/reactivestreams/Subscription;")
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "actual", "Lorg/reactivestreams/Subscriber;")
    mv.visitVarInsn(ALOAD, 0)
    mv.visitMethodInsn(INVOKEINTERFACE, "org/reactivestreams/Subscriber", "onSubscribe", "(Lorg/reactivestreams/Subscription;)V", true)
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "onNext", "(Ljava/lang/Object;)V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "actual", "Lorg/reactivestreams/Subscriber;")
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "mapper", "Lpw/koj/jetstreem/runtime/type/StrmFunction;")
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "actual", "Lorg/reactivestreams/Subscriber;")
    mv.visitVarInsn(ALOAD, 1)
    mv.visitInvokeDynamicInsn(
        "_mapper\$func_",
        "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
        new Handle(
            H_INVOKESTATIC,
            "pw/koj/jetstreem/runtime/DynamicDispatchSupport",
            "bootstrap",
            "(Ljava/lang/invoke/MethodHandles\$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
            false))
    mv.visitMethodInsn(INVOKEINTERFACE, "org/reactivestreams/Subscriber", "onNext", "(Ljava/lang/Object;)V", true)
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "onError", "(Ljava/lang/Throwable;)V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "actual", "Lorg/reactivestreams/Subscriber;")
    mv.visitVarInsn(ALOAD, 1)
    mv.visitMethodInsn(INVOKEINTERFACE, "org/reactivestreams/Subscriber", "onError", "(Ljava/lang/Throwable;)V", true)
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "onComplete", "()V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "actual", "Lorg/reactivestreams/Subscriber;")
    mv.visitMethodInsn(INVOKEINTERFACE, "org/reactivestreams/Subscriber", "onComplete", "()V", true)
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "cancel", "()V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "s", "Lorg/reactivestreams/Subscription;")
    mv.visitMethodInsn(INVOKEINTERFACE, "org/reactivestreams/Subscription", "cancel", "()V", true)
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    mv = cw.visitMethod(ACC_PUBLIC, "request", "(J)V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitFieldInsn(GETFIELD, "pw/koj/jetstreem/runtime/type/StrmMapOperator\$MapOp", "s", "Lorg/reactivestreams/Subscription;")
    mv.visitVarInsn(LLOAD, 1)
    mv.visitMethodInsn(INVOKEINTERFACE, "org/reactivestreams/Subscription", "request", "(J)V", true)
    mv.visitInsn(RETURN)
    mv.visitMaxs(0, 0)
    mv.visitEnd()
    
    cw.visitEnd()

    b =  cw.toByteArray();

    output = new FileOutputStream(dir + "StrmMapOperator\$MapOp.class")
    output.write(b)
}

def generate(dir) {
    genStrmMapOperator(dir)
    genMapOp(dir)
}

