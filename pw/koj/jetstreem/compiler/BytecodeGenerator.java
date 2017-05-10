package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.compiler.ir.*;
import org.objectweb.asm.*;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.*;

public class BytecodeGenerator {

    private static final String JOBJ = "java/lang/Object";
    private static final String TOBJ = "Ljava/lang/Object;";

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

    public byte[] generate(Namespace ir, String className) {
        this.className = className;

        this.cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
        ir.accept(this);
        this.cw.visitEnd();

        return this.cw.toByteArray();
    }

    public void visit(Namespace ns) {
        BytecodeGenerator inner = new BytecodeGenerator(
            ns.getName(),
            new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS));

        if (ns.getParent() == null) {
            inner.cw().visit(V1_8, ACC_PUBLIC + ACC_SUPER, ns.getName(), null, JOBJ, null);
        }
        else {
            String name = fullNameOf(ns);
            inner.cw().visit(v1_8, ACC_SUPER, name, null, JOBJ, null);
            //generate all inner classes by cw.visitInnerClass() 
            //add generation of this inner class to all parent classes
        }

        for (Object stmt : ns.getStmts()) {
            stmt.accept(inner);
        }

        inner.cw().visitEnd();
    }
}

