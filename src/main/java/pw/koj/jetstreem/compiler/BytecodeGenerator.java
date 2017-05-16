package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.compiler.ir.*;
import org.objectweb.asm.*;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.*;

public class BytecodeGenerator {

    private static final String JOBJ = "java/lang/Object";
    private static final String TOBJ = "Ljava/lang/Object;";
    private static final int N_AUG = 1;

    private String simpleName;
    private ClassWriter cw;
    private MethodVisitor mv;
    private BytecodeGenerator topLevel;
    private BytecodeGenerator outer;
    private List<BytecodeGenerator> innerBgs;

    public BytecodeGenerator(String simpleName, ClassWriter cw, BytecodeGenerator outer) {
        this.simpleName = simpleName;
        this.cw = cw;
        this.topLevel = outer.topLevel();
        this.outer = outer;
    }

    public String className() {
        if (outer == null) {
            return simpleName;
        }
        else {
            return outerName() + "$" + simpleName;
        }
    }

    public String outerName() {
        if (outer == null) {
            return "";
        }
        else {
            return outer.className();
        }
    }

    public String innerName() {
        return simpleName;
    }

    public BytecodeGenerator topLevel() {
        return topLevel;
    }

    public BytecodeGenerator outer() {
        return outer;
    }

    public ClassWriter cw() {
        return cw;
    }

    public MethodVisitor mv() {
        return mv;
    }

    public MethodVisitor mv(MethodVisitor mv) {
        this.mv = mv;
        return mv;
    }

    public List<BytecodeGenerator> innerBgs() {
        return innerBgs;
    }

    private String fullNameOf(RefTable ref) {
        return buildNameFrom(ref.getParent()) + ref.getName();
    }

    private String buildNameFrom(RefTable ref) {
        if (ref == null) {
            return "";
        }
        else {
            return fullNameOf(ref.getParent()) + ref.getName() + "$";
        }
    }

    public void writeClassFiles() {
        FileOutputStream out = new FileOutputStream(new File(className() + ".class"));
        out.write(cw.toByteArray());

        for (BytecodeGenerator g : innerBgs) {
            out = new FileOutputStream(new File(g.className() + ".class"));
            out.write(g.cw().toByteArray());
        }
    }

    public void generate(Namespace ns, Deque<RuntimeScope> ctx) {
        this.simpleName = ns.getName();
        this.topLevel = this;
        this.innerBgs = new ArrayList<>();


        cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, ns.getName(), null, JOBJ, null);

        Map<String, Integer> refs = ns.getRefTable().getLocalRefs();
        for (String name : refs) {
            FieldVisitor fv = incw.visitField(ACC_STATIC, name, TOBJ, null, null);
            fv.visitEnd();
        }

        RuntimeScope scope = new RuntimeScope(ns);
        ctx.push(scope);

        String topMethodName = className() + "$define";
        String topMethodDesc = "(L[java/lang/invoke/SwitchPoint;)V";

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, topMethodName, topMethodDesc, null, null);
        mv.visitCode();
        for (Object stmt : ns.getStmts()) {
            stmt.accept(this, ctx);
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        ctx.pop();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "(L[java/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitIntInsn(BIPUSH, scope.numOfSwp());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/invoke/SwitchPoint");
        mv.visitMethodInsn(INVOKESTATIC, className(), topMethodName, topMethodDesc, false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();

    }

    public void visit(Namespace ns, Deque<RuntimeScope> ctx) {
        BytecodeGenerator inner = new BytecodeGenerator(
            ns.getName(),
            new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS),
            this);
        topLevel.innerBgs().add(inner);


        ClassWriter incw = inner.cw();
        incw.visit(v1_8, ACC_SUPER, inner.className(), null, JOBJ, null);

        for (BytecodeGenerator bg = inner; bg != topLevel; bg = bg.outer()) {
            incw.visitInnerClass(bg.className(), bg.outerName(), bg.innerName(), ACC_STATIC);
            bg.outer().cw().visitInnerClass(inner.className(), inner.outerName(), inner.innerName(), ACC_STATIC);
        }

        Map<String, Integer> refs = ns.getRefTable().getLocalRefs();
        for (String name : refs) {
            FieldVisitor infv = incw.visitField(ACC_STATIC, name, TOBJ, null, null);
            infv.visitEnd();
        }

        RuntimeScope scope = new RuntimeScope(ns.getRefTable());
        ctx.push(scope);

        String topMethodName = inner.className() + "$define";
        String topMethodDesc = "(L[java/lang/invoke/SwitchPoint;)V";

        MethodVisitor inmv = inner.mv(incw.visitMethod(ACC_PUBLIC + ACC_STATIC, topMethodName, topMethodDesc, null, null));
        inmv.visitCode();
        for (Object stmt : ns.getStmts()) {
            stmt.accept(inner, ctx);
        }
        inmv.visitInsn(RETURN);
        inmv.visitMaxs(0, 0);
        inmv.visitEnd();

        ctx.pop();

        incw.visitEnd();


        mv.visitIntInsn(BIPUSH, scope.numOfSwp());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/invoke/SwitchPoint");
        mv.visitMethodInsn(INVOKESTATIC, inner.className(), topMethodName, topMethodDesc, false);
    }

    public void visit(Let let, Deque<RuntimeScope> ctx) throws CompileError {
        String name = let.getName();
        RefTable ref = ctx.peek().refTable();
        RefTable target = ref.lookupRef(name);

        let.getExpr().accept(this, ctx);

        if (target instanceof NsRefTable) {
            NsRefTable nsRef = (NsRefTable)target;
            String className = fullNameOf(nsRef);
            mv.visitFieldInsn(PUTSTATIC, className, name, TOBJ);
        }
        else if (target instanceof FuncRefTable) {
            FuncRefTable fnRef = (FuncRefTable)target;
            mv.visitVarInsn(ASTORE, fnRef.indexOf(name) + N_AUG);
        }
        else {
            throw new CompileError("found illegal RefTable");
        }
    }

    public void visit(BinaryOp binOp, Deque<RuntimeScope> ctx) {
        RuntimeScope scope = ctx.peek();

        mv.visitVarInsn(ALOAD, 0);
        binOp.getLhs().accept(this, ctx);
        binOp.getRhs().accept(this, ctx);
        mv.visitInvokeDynamicInsn(
                binOp.getOp(),
                "(L[java/lang/invoke/SwitchPoint;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                new Handle(Opcodes.H_INVOKESTATIC,
                    "pw/koj/jetstreem/runtime/OpSupport",
                    "bootstrap",
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/Integer;)Ljava/lang/invoke/CallSite;",
                    false),
                new Object[]{new Integer(scope.nextIndex())});
    }

    public void visit(IntegerConstant intConst, Deque<RuntimeScope> ctx) {
        mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmInteger");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(new Long(intConst.getLongValue()));
        mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmInteger", "<init>", "(J)V", false);
    }

}

