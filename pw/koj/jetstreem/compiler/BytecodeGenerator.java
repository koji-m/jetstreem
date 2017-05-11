package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.compiler.ir.*;
import org.objectweb.asm.*;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.*;

public class BytecodeGenerator {

    private static final String JOBJ = "java/lang/Object";
    private static final String TOBJ = "Ljava/lang/Object;";

    private String simpleName;
    private ClassWriter cw;
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

    public List<BytecodeGenerator> innerBgs() {
        return innerBgs;
    }
/*
    private String fullNameOf(Namespace ns) {
        return buildNameFrom(ns.getParent()) + ns.getName();
    }

    private String buildNameFrom(Namespace ns) {
        if (ns == null) {
            return "";
        }
        else {
            return fullNameOf(ns.getParent()) + ns.getName() + "$";
        }
    }
*/
    public void generate(Namespace ns, Deque<RefTable> ctx) {
        this.simpleName = ns.getName();
        this.topLevel = this;
        this.innerBgs = new ArrayList<>();


        cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, ns.getName(), null, JOBJ, null);

        ctx.push(ns.getRefTable());

        for (Object stmt : ns.getStmts()) {
            stmt.accept(this);
        }

        ctx.pop();

        cw.visitEnd();

    }

    public void visit(Namespace ns, Deque<RefTable> ctx) {
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
            
        ctx.push(ns.getRefTable());

        for (Object stmt : ns.getStmts()) {
            stmt.accept(inner);
        }

        ctx.pop();

        incw.visitEnd();
    }

    public void visit(Let let, Deque<RefTable> ctx) {
        //from here
    }
}

