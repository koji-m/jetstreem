package pw.koj.jetstreem.parser;

import java.util.List;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class PatternStructNode extends PatternArrayNode {

    public PatternStructNode() {
        super();
    }

    public PatternStructNode(List<Node> data) {
        super(data);
    }

    public void add(PairNode p) {
        super.add(p);
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

