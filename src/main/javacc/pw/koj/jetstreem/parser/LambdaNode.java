package pw.koj.jetstreem.parser;

import java.util.*;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class LambdaNode extends ExprNode {
    protected Location location;
    protected ArgsNode args;
    protected List<Node> body;
    protected boolean blockp;

    public LambdaNode() {}

    public LambdaNode(Location loc, ArgsNode args, List<Node> body, boolean blkp) {
        super();
        this.location = loc;
        this.args = args;
        this.body = body;
        this.blockp = blkp;
    }

    public LambdaNode(Location loc, ArgsNode args, Node expr, boolean blkp) {
        super();
        this.location = loc;
        this.args = args;
        List<Node> body = new LinkedList<Node>();
        body.add(expr);
        this.body = body;
        this.blockp = blkp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public ArgsNode getArgs() {
        return args;
    }

    public List<IdentifierNode> getArgList() {
        return args.getArgs();
    }

    public void setArgs(ArgsNode args) {
        this.args = args;
    }

    public List<Node> getBody() {
        return body;
    }

    public void setBody(List<Node> body) {
        this.body = body;
    }

    public boolean getBlockp() {
        return blockp;
    }

    public void setBlockp(boolean p) {
        this.blockp = p;
    }

    public boolean isBlock() {
        return blockp;
    }

    public void isBlock(boolean blkp) {
        this.blockp = blkp;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public Location location() {
        return location;
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

