package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;
import java.util.List;

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

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

