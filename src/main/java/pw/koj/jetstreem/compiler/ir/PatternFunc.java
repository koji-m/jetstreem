package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import org.objectweb.asm.MethodVisitor;
import pw.koj.jetstreem.compiler.*;

import static org.objectweb.asm.Opcodes.*;

public class PatternFunc implements IrNode {
    private List<FuncArm> arms;
    private PatternFuncRefTable refTable;

    public PatternFunc() {
    }

    public PatternFunc(PatternFuncRefTable refTable) {
        arms = new ArrayList<>();
        this.refTable = refTable;
    }

    public List<FuncArm> getArms() {
        return arms;
    }

    public void setArms(List<FuncArm> arms) {
        this.arms = arms;
    }

    public PatternFuncRefTable getRefTable() {
        return refTable;
    }

    public void add(FuncArm arm) {
        arms.add(arm);
    }
    public void bcGenerateMatchers (MethodVisitor mv,
                                    RuntimeContext<RuntimeScope> ctx,
                                    BytecodeGenerator visitor)
        throws Exception  {
   
        int nArms = arms.size();

        mv.visitIntInsn(BIPUSH, nArms);
        mv.visitTypeInsn(ANEWARRAY, "pw/koj/jetstreem/runtime/matcher/ArrayMatcher");

        for (int i = 0; i < nArms; i++) {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);
            arms.get(i).getPattern().accept(visitor, ctx);
            mv.visitInsn(AASTORE);
        }
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

