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

    public byte[] generate(Namespace ir, String fileName) {
        this.fileName = fileName;

        this.cw = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
        ir.accept(this);
        this.cw.visitEnd();

        return this.cw.toByteArray();
    }

    public void visit(Namespace ns) {
        if (ns.getParent() == null) {
            cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, ns.getName(), null, JOBJ, null);
        }
        else {
            String name = fullNameOf(ns);
            cw.visit(v1_8, ACC_SUPER, name, null, JOBJ, null);
            //generate all inner classes by cw.visitInnerClass() 
            //add generation of this inner class to all parent classes
        }
    }
}

