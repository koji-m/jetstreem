package pw.koj.jetstreem.parser;

import java.util.List;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class PatternLambdaNode extends ExprNode {
    protected Node pattern;
    protected Node condition;
    protected List<Node> body;
    protected PatternLambdaNode next;


    public PatternLambdaNode() {}

    public PatternLambdaNode(Node pat, Node cond, List<Node> body, PatternLambdaNode next) {
        super();
        this.pattern = pat;
        this.condition = cond;
        this.body = body;
        this.next = next;
    }

    public Node getPattern() {
        return pattern;
    }

    public void setPattern(Node pat) {
        this.pattern = pat;
    }

    public Node getCondition() {
        return condition;
    }

    public void setCondition(Node cond) {
        this.condition = cond;
    }

    public List<Node> getBody() {
        return body;
    }

    public void setBody(List<Node> body) {
        this.body = body;
    }

    public PatternLambdaNode getNext() {
        return next;
    }

    public void setNext(PatternLambdaNode next) {
        this.next = next;
    }

    public Location location() {
        return pattern.location();
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

