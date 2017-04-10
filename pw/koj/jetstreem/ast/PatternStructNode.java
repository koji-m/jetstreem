package pw.koj.jetstreem.ast;

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

}

