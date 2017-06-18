package pw.koj.jetstreem.compiler;

import java.util.*;
import java.io.*;
import org.objectweb.asm.*;
import pw.koj.jetstreem.compiler.ir.*;
import pw.koj.jetstreem.runtime.type.*;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.*;

public class BytecodeGenerator {

    private static final String JOBJ = "java/lang/Object";
    private static final String STRMNS = "pw/koj/jetstreem/runtime/type/StrmNamespace";
    private static final String TOBJ = "Ljava/lang/Object;";
    private static final String TSTRMNS = "Lpw/koj/jetstreem/runtime/type/StrmNamespace";

    private String simpleName;
    private ClassWriter cw;
    private MethodVisitor mv;
    private BytecodeGenerator topLevel;
    private BytecodeGenerator outer;
    private List<BytecodeGenerator> innerBgs;
    private int nFuncs;

    public BytecodeGenerator(BytecodeGenerator outer) {
        this.cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
        if (outer == null) {
            this.topLevel = this;
        }
        else {
            this.topLevel = outer.topLevel();
        }
        this.outer = outer;

        this.nFuncs = 0;
    }

    public BytecodeGenerator(String simpleName, BytecodeGenerator outer) {
        this(outer);
        this.simpleName = simpleName;
    }

    public String className() {
        if (outer == null) {
            return simpleName;
        }
        else {
            return outerName() + "$" + simpleName;
        }
    }

    public String classNameOf(Namespace ns) {
        if (ns.getParent() == null) {
            return ns.getName();
        }
        else {
            return classNameOf(ns.getParent()) + "$" + ns.getName();
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

    public int nextFuncIndex() {
        return nFuncs++;
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

    public void popIfNeeded(RuntimeContext<RuntimeScope> ctx) {
        if (ctx.discard()) {
            mv.visitInsn(POP);
        }
    }

    public List<BytecodeGenerator> innerBgs() {
        return innerBgs;
    }

    public String writeClassFiles() throws Exception {
        FileOutputStream out = new FileOutputStream(new File(className() + ".class"));
        out.write(cw.toByteArray());

        for (BytecodeGenerator g : innerBgs) {
            out = new FileOutputStream(new File(g.className() + ".class"));
            out.write(g.cw().toByteArray());
        }

        return className();
    }

    public void generate(Namespace ns, RuntimeContext<RuntimeScope> ctx) throws Exception {
        this.simpleName = ns.getName();
        this.topLevel = this;
        this.innerBgs = new ArrayList<>();

        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, ns.getName(), null, STRMNS, null);

        RefTable refTbl = ns.getRefTable();

        Map<String, Integer> refs = refTbl.getLocalRefs();
        for (String name : refs.keySet()) {
            FieldVisitor fv = cw.visitField(ACC_STATIC, name, TOBJ, null, null);
            fv.visitEnd();
        }

        RuntimeScope scope = new RuntimeScope(refTbl);
        ctx.push(scope);

        String topMethodName = className() + "$define";
        String topMethodDesc = "([Ljava/lang/invoke/SwitchPoint;)V";

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, topMethodName, topMethodDesc, null, null);
        mv.visitCode();

        //for implicit tmp var (used for storing return val of if expression)
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, refTbl.tmpVarIndex());

        for (IrNode stmt : ns.getStmts()) {
            stmt.accept(this, ctx);
        }
        mv.visitMethodInsn(INVOKESTATIC, "pw/koj/jetstreem/runtime/type/StrmNamespace", "strmEnv", "()Lpw/koj/jetstreem/runtime/StreamEnv;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "pw/koj/jetstreem/runtime/StreamEnv", "waitForCompletion", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        ctx.pop();

        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "([Ljava/lang/Object;)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmNamespace", "<init>", "([Ljava/lang/Object;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "([Ljava/lang/Object;[Lpw/koj/jetstreem/runtime/type/StrmString;)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmNamespace", "<init>", "([Ljava/lang/Object;[Lpw/koj/jetstreem/runtime/type/StrmString;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        mv.visitInsn(ICONST_1);
        mv.visitFieldInsn(PUTSTATIC, ns.getName(), "topp", "Z");

        mv.visitIntInsn(BIPUSH, scope.numOfSwp());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/invoke/SwitchPoint");
        mv.visitMethodInsn(INVOKESTATIC, className(), topMethodName, topMethodDesc, false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();

    }

    public void visit(Namespace ns, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        BytecodeGenerator inner = new BytecodeGenerator(
            ns.getName(),
            this);
        topLevel.innerBgs().add(inner);


        ClassWriter incw = inner.cw();
        incw.visit(V1_8, ACC_SUPER, inner.className(), null, STRMNS, null);

        for (BytecodeGenerator bg = inner; bg != topLevel; bg = bg.outer()) {
            incw.visitInnerClass(bg.className(), bg.outerName(), bg.innerName(), ACC_STATIC);
            bg.outer().cw().visitInnerClass(inner.className(), inner.outerName(), inner.innerName(), ACC_STATIC);
        }

        RefTable refTbl = ns.getRefTable();

        Map<String, Integer> refs = refTbl.getLocalRefs();
        for (String name : refs.keySet()) {
            FieldVisitor infv = incw.visitField(ACC_STATIC, name, TOBJ, null, null);
            infv.visitEnd();
        }

        RuntimeScope scope = new RuntimeScope(refTbl);
        ctx.push(scope);

        String topMethodName = inner.className() + "$define";
        String topMethodDesc = "([Ljava/lang/invoke/SwitchPoint;)V";

        MethodVisitor inmv = inner.mv(incw.visitMethod(ACC_PUBLIC + ACC_STATIC, topMethodName, topMethodDesc, null, null));
        inmv.visitCode();
        
        //for implicit tmp var
        inmv.visitInsn(ACONST_NULL);
        inmv.visitVarInsn(ASTORE, refTbl.tmpVarIndex());

        for (IrNode stmt : ns.getStmts()) {
            ctx.discard(true);
            stmt.accept(inner, ctx);
        }
        inmv.visitInsn(RETURN);
        inmv.visitMaxs(0, 0);
        inmv.visitEnd();

        ctx.pop();

        ctx.discard(orgDiscard);

        inmv = inner.mv(incw.visitMethod(ACC_PUBLIC, "<init>", "([Ljava/lang/Object;)V", null, null));
        inmv.visitCode();
        inmv.visitVarInsn(ALOAD, 0);
        inmv.visitVarInsn(ALOAD, 1);
        inmv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmNamespace", "<init>", "([Ljava/lang/Object;)V", false);
        inmv.visitInsn(RETURN);
        inmv.visitMaxs(0, 0);
        inmv.visitEnd();

        inmv = inner.mv(incw.visitMethod(ACC_PUBLIC, "<init>", "([Ljava/lang/Object;[Lpw/koj/jetstreem/runtime/type/StrmString;)V", null, null));
        inmv.visitCode();
        inmv.visitVarInsn(ALOAD, 0);
        inmv.visitVarInsn(ALOAD, 1);
        inmv.visitVarInsn(ALOAD, 2);
        inmv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmNamespace", "<init>", "([Ljava/lang/Object;[Lpw/koj/jetstreem/runtime/type/StrmString;)V", false);
        inmv.visitInsn(RETURN);
        inmv.visitMaxs(0, 0);
        inmv.visitEnd();

        incw.visitEnd();


        mv.visitIntInsn(BIPUSH, scope.numOfSwp());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/invoke/SwitchPoint");
        mv.visitMethodInsn(INVOKESTATIC, inner.className(), topMethodName, topMethodDesc, false);
    }

    public void visit(Let let, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        String name = let.getName();
        RefTable ref = ctx.peek().refTable();
        RefTable target = ref.lookupRef(name);

        ctx.discard(false);
        let.getExpr().accept(this, ctx);

        ctx.discard(orgDiscard);

        if (target instanceof NsRefTable) {
            NsRefTable nsRef = (NsRefTable)target;
            String className = nsRef.fullName();
            mv.visitFieldInsn(PUTSTATIC, className, name, TOBJ);
        }
        else if (target instanceof FuncRefTable) {
            FuncRefTable fnRef = (FuncRefTable)target;
            mv.visitVarInsn(ASTORE, fnRef.localIndexOf(name));
        }
        else {
            throw new CompileError("found illegal RefTable");
        }
    }

    public void visit(BinaryOp binOp, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        RuntimeScope scope = ctx.peek();

        mv.visitVarInsn(ALOAD, 0);

        ctx.discard(false);
        binOp.getLhs().accept(this, ctx);

        ctx.discard(false);
        binOp.getRhs().accept(this, ctx);

        ctx.discard(orgDiscard);

        mv.visitInvokeDynamicInsn(
                binOp.getOp(),
                "([Ljava/lang/invoke/SwitchPoint;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                new Handle(Opcodes.H_INVOKESTATIC,
                    "pw/koj/jetstreem/runtime/OpSupport",
                    "bootstrap",
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/Integer;)Ljava/lang/invoke/CallSite;",
                    false),
                new Object[]{new Integer(scope.nextIndex())});

        popIfNeeded(ctx);
    }

    public void visit(IntegerConstant intConst, RuntimeContext<RuntimeScope> ctx) {
        mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmInteger");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(new Long(intConst.getValue()));
        mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmInteger", "<init>", "(J)V", false);

        popIfNeeded(ctx);
    }

    public void visit(BoolConstant bc, RuntimeContext<RuntimeScope> ctx) throws Exception {
        if (bc.getValue()) {
            mv.visitFieldInsn(GETSTATIC, "pw/koj/jetstreem/runtime/type/StrmBool", "TRUE", "Lpw/koj/jetstreem/runtime/type/StrmBool;");
        }
        else {
            mv.visitFieldInsn(GETSTATIC, "pw/koj/jetstreem/runtime/type/StrmBool", "FALSE", "Lpw/koj/jetstreem/runtime/type/StrmBool;");
        }
            
        popIfNeeded(ctx);
    }

    public void visit(Call c, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        RefTable ref = c.getRef();
        if (ref == null) {
            mv.visitInsn(ACONST_NULL);
        }
        else {
            ref.bcPushVarRef(c.getName(), mv);
        }

        mv.visitFieldInsn(GETSTATIC, "pw/koj/jetstreem/runtime/BuiltIn", "nil_stream", "Ljava/lang/Object;");

        for (IrNode arg : c.getArgs()) {
            ctx.discard(false);
            arg.accept(this, ctx);
        }

        ctx.discard(orgDiscard);
        
        mv.visitInvokeDynamicInsn(
                c.getName(),
                c.descriptor(),
                new Handle(
                    Opcodes.H_INVOKESTATIC,
                    "pw/koj/jetstreem/runtime/DynamicDispatchSupport",
                    "bootstrap",
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                    false));

        popIfNeeded(ctx);
    }

    public void visit(CondBranch cb, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        ctx.discard(false);
        cb.getCond().accept(this, ctx);

        mv.visitInvokeDynamicInsn(
                "test",
                "(Ljava/lang/Object;)Z",
                new Handle(
                    Opcodes.H_INVOKESTATIC,
                    "pw/koj/jetstreem/runtime/BoolSupport",
                    "bootstrap",
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                    false));

        Label l0 = new Label();
        mv.visitJumpInsn(IFEQ, l0); 

        ctx.discard(orgDiscard);
        cb.getTruePart().accept(this, ctx);

        RefTable refTbl = ctx.peek().refTable();
        if (!orgDiscard) {
            mv.visitVarInsn(ASTORE, refTbl.tmpVarIndex());
        }

        if (cb.hasFalsePart()) {
            Label l1 = new Label();
            mv.visitJumpInsn(GOTO, l1);

            mv.visitLabel(l0);

            ctx.discard(orgDiscard);
            cb.getFalsePart().accept(this, ctx);

            if (!orgDiscard) {
                mv.visitVarInsn(ASTORE, refTbl.tmpVarIndex());
            }

            mv.visitLabel(l1);
        }
        else {
            mv.visitLabel(l0);
        }

        if (!orgDiscard) {
            mv.visitVarInsn(ALOAD, refTbl.tmpVarIndex());
        }

        ctx.discard(orgDiscard);
    }

    public void visit(DoubleConstant dc, RuntimeContext<RuntimeScope> ctx) throws Exception {
        mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmFloat");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(new Double(dc.getValue()));
        mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmFloat", "<init>", "(D)V", false);

        popIfNeeded(ctx);
    }

    public void visit(Emit em, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(FunCall fc, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(FuncArm fa, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Function f, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        MethodVisitor orgMv = mv;

        RefTable refTbl = f.getRefTable();
        RuntimeScope scope = new RuntimeScope(refTbl);
        ctx.push(scope);

        String methodName = "lambda$define$" + nextFuncIndex();
        String methodDesc = "([Ljava/lang/invoke/SwitchPoint;[Ljava/lang/Object;)Ljava/lang/Object;";

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, methodName, methodDesc, null, null);
        mv.visitCode();

        //for implicit tmp var
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, refTbl.tmpVarIndex());

        List<IrNode> body = f.getBody();
        int last = body.size() - 1;
        for (IrNode stmt : body) {
            if (body.indexOf(stmt) == last) {
                ctx.discard(false);
                stmt.accept(this, ctx);
            }
            else {
                ctx.discard(true);
                stmt.accept(this, ctx);
            }
        }
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/invoke/SwitchPoint", "invalidateAll", "([Ljava/lang/invoke/SwitchPoint;)V", false);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        ctx.pop();

        ctx.discard(orgDiscard);

        mv = orgMv;
        mv.visitIntInsn(BIPUSH, scope.numOfSwp());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/invoke/SwitchPoint");
        mv.visitInvokeDynamicInsn(
                "call",
                "([Ljava/lang/invoke/SwitchPoint;)Lpw/koj/jetstreem/runtime/type/StrmFunction;",
                new Handle(
                    Opcodes.H_INVOKESTATIC,
                    "java/lang/invoke/LambdaMetafactory",
                    "metafactory",
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                    false),
                new Object[]{
                    Type.getType("([Ljava/lang/Object;)Ljava/lang/Object;"),
                    new Handle(
                            Opcodes.H_INVOKESTATIC,
                            className(),
                            methodName,
                            methodDesc,
                            false),
                    Type.getType("([Ljava/lang/Object;)Ljava/lang/Object;")});

        popIfNeeded(ctx);
    }

    public void visit(Block b, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        List<IrNode> stmts = b.getStmts();
        int last = stmts.size() - 1;
        for (IrNode stmt : stmts) {
            if (stmts.indexOf(stmt) == last) {
                ctx.discard(false);
                stmt.accept(this, ctx);
            }
            else {
                ctx.discard(true);
                stmt.accept(this, ctx);
            }
        }

        ctx.discard(orgDiscard);

        popIfNeeded(ctx);
    }

    public void visit(GenArray ga, RuntimeContext<RuntimeScope> ctx) throws Exception {
        boolean orgDiscard = ctx.discard();

        List<IrNode> data = ga.getData();
        Namespace ns = ga.getNs();

        if (ns == null) {
            mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmArray");
            mv.visitInsn(DUP);

            ctx.discard(false);
            loadArrayData(data, mv, ctx);

            if (ga.getHeaders() == null) {
                mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmArray", "<init>", "([Ljava/lang/Object;)V", false);
            }
            else {
                ctx.discard(false);
                loadArrayHeaders(ga.getHeaders(), mv, ctx);
                mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmArray", "<init>", "([Ljava/lang/Object;[Lpw/koj/jetstreem/runtime/type/StrmString;)V", false);
            }
        }
        else { 
            String klass = classNameOf(ns);
            mv.visitTypeInsn(NEW, klass);
            mv.visitInsn(DUP);

            ctx.discard(false);
            loadArrayData(data, mv, ctx);

            if (ga.getHeaders() == null) {
                mv.visitMethodInsn(INVOKESPECIAL, klass, "<init>", "([Ljava/lang/Object;)V", false);
            }
            else {
                ctx.discard(false);
                loadArrayHeaders(ga.getHeaders(), mv, ctx);
                mv.visitMethodInsn(INVOKESPECIAL, klass, "<init>", "([Ljava/lang/Object;[Ljava/lang/StrmString;)V", false);
            }
        }

        ctx.discard(orgDiscard);

        popIfNeeded(ctx);
    }

    private void loadArrayData(List<IrNode> data, MethodVisitor mv, RuntimeContext<RuntimeScope> ctx) throws Exception {
        mv.visitIntInsn(BIPUSH, data.size());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        for (int i = 0; i < data.size(); i++) {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);
            data.get(i).accept(this, ctx);
            mv.visitInsn(AASTORE);
        }
    }

    private void loadArrayHeaders(List<String> headers, MethodVisitor mv, RuntimeContext<RuntimeScope> ctx) throws Exception {
        mv.visitIntInsn(BIPUSH, headers.size());
        mv.visitTypeInsn(ANEWARRAY, "pw/koj/jetstreem/runtime/type/StrmString");
        for (int i = 0; i < headers.size(); i++) {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);

            mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmString");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(headers.get(i));
            mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmString", "<init>", "(Ljava/lang/String;)V", false);

            mv.visitInsn(AASTORE);
        }
    }

    public void visit(GenericFunc gf, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Import im, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Nil n, RuntimeContext<RuntimeScope> ctx) throws Exception {
        mv.visitFieldInsn(GETSTATIC, "pw/koj/jetstreem/runtime/type/StrmNil", "NIL", "Ljava/lang/Object;");

        popIfNeeded(ctx);
    }

    public void visit(Pair p, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternArray pa, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternBool pb, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternDouble pd, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternFunc pf, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternInteger pi, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternNamespace pn, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternNil pn, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternString ps, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternStruct ps, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternVarBind pv, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(PatternVarRef pv, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Reference r, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Return r, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Skip s, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(Splat s, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }
    
    public void visit(StringConstant sc, RuntimeContext<RuntimeScope> ctx) throws Exception {
        mv.visitTypeInsn(NEW, "pw/koj/jetstreem/runtime/type/StrmString");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(sc.getValue());
        mv.visitMethodInsn(INVOKESPECIAL, "pw/koj/jetstreem/runtime/type/StrmString", "<init>", "(Ljava/lang/String;)V", false);

        popIfNeeded(ctx);

    }

    public void visit(TimeConstant tc, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(UnaryOp uo, RuntimeContext<RuntimeScope> ctx) throws Exception {
    }

    public void visit(VarRef vr, RuntimeContext<RuntimeScope> ctx) throws Exception {
        vr.bcPushVarVal(mv);

        popIfNeeded(ctx);
    }
}

