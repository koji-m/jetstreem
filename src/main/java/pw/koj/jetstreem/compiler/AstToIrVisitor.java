package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.parser.*;
import pw.koj.jetstreem.compiler.ir.*;
import java.util.*;


public class AstToIrVisitor implements Visitor {

    // dummy standard lib symbols
    private static final String[] STDLIB = {
        "stdin", "stdout", "seq", "map", "each", "filter",
        "tcp_server", "tcp_socket", "chan", "print"
    };

    private Context ctx;
    private ArrayList<String> pvs;
    
    // Entry point
    public Object transform(NamespaceNode ast) throws CompileError {
        ctx = new Context();
        return visit(ast);
    }


    public Object visit(NamespaceNode nsNode) throws CompileError {
        String nsName = nsNode.getName();
        List<Object> stmts = new LinkedList<>();
        NsRefTable refTable = new NsRefTable(nsName);
        Namespace currentNs = ctx.peekNsStack();
        Namespace ns;
        if (currentNs == null) {
            // import dummy standard lib symbols
            for (String s : STDLIB) {
                refTable.addLocal(s);
            }
            ns = new Namespace(nsName, stmts, refTable, null);
        }
        else if (currentNs.hasChild(nsName)) {
            throw new CompileError("duplicate namespace definition");
        }
        else {
            ns = new Namespace(nsName, stmts, refTable, currentNs);
            currentNs.addChild(ns);
        }

        ctx.enterNsTo(ns);
        for (Node stmt : nsNode.getStmts()) {
            stmts.add(stmt.accept(this));
        }
        ctx.exitNs();

        return ns;
   }

    public Object visit(ImportNode imp) throws CompileError {
        String id = imp.getIdentifier();
        Namespace ns = ctx.peekNsStack();

        Namespace n = ns.lookupNs(id);
        if (n == null) {
            throw new CompileError("namespace not found: " + id);
        }

        return new Import(n);
    }

    public Object visit(LetNode let) throws CompileError {
        Object rhs = let.getRhs().accept(this);

        RefTable refTable = ctx.peekRefTableStack();
        String name = ((IdentifierNode)let.getLhs()).getName();
        if (refTable.hasLocal(name)) {
            throw new CompileError("duplicate assignment: " + name);
        }
        refTable.addLocal(name);

        return new Let(name, rhs);
   }

    public Object visit(SkipNode skp) throws CompileError {
        return new Skip();
    }

    public Object visit(EmitNode emt) throws CompileError {
        List<Node> arr = emt.getArgs().getData();
        List<Object> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this));
        }

        return new Emit(args);
    }

    public Object visit(ReturnNode ret) throws CompileError {
        List<Node> arr = ret.getArgs().getData();
        List<Object> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this));
        }

        return new Return(args);
    }

    public Object visit(LambdaNode lambda) throws CompileError {
        if (lambda.isBlock()) {
            List<Object> body = new LinkedList<>();
            for (Node stmt : lambda.getBody()) {
                body.add(stmt.accept(this));
            }
            return body;
        }

        FuncRefTable refTable = new FuncRefTable();
        List<IdentifierNode> args = lambda.getArgList();

        for (IdentifierNode id : args) {
            refTable.addArg(id.getName());
        }

        ctx.enterScopeTo(refTable);
        List<Object> body = new LinkedList<>();
        for (Node stmt : lambda.getBody()) {
            body.add(stmt.accept(this));
        }
        ctx.exitScope();

        return new Function(body, refTable);
    }


    public Object visit(IdentifierNode id) throws CompileError {
        RefTable current = ctx.peekRefTableStack();
        String name = id.getName();
        RefTable ref = current.resolveRef(name);
        if (ref == null) {
            throw new CompileError("variable not defined");
        }

        return new VarRef(name, ref);
   }

    public Object visit(ArrayNode arr) throws CompileError {
        GenArray ar = new GenArray();
        List<Node> data = arr.getData();
        for (Node expr : data) {
            ar.add(expr.accept(this));
        }
        ar.setHeaders(arr.getHeaders());
        ar.setNs(arr.getNs());
        return ar;
    }

    public Object visit(PairNode pair) throws CompileError {
        return new Pair(pair.getKey(), pair.getValue().accept(this));
    }

    public Object visit(SplatNode splt) throws CompileError {
        return new Splat(splt.getNode().accept(this));
    }

    public Object visit(IfNode ifn) throws CompileError {
        Object cond = ifn.getCond().accept(this);

        List<Object> truePart;
        Object thenBody = ifn.getThenBody().accept(this);
        if (thenBody instanceof List) {
            truePart = (List<Object>)thenBody;
        }
        else {
            truePart = new LinkedList<>();
            truePart.add(thenBody);
        }

        Node els = ifn.getElseBody();
        if (els == null) {
            return new CondBranch(cond, truePart);
        }

        List<Object> falsePart;
        Object elseBody = els.accept(this);
        if (elseBody instanceof List) {
            falsePart = (List<Object>)elseBody;
        }
        else {
            falsePart = new LinkedList<>();
            falsePart.add(elseBody);
        }

        return new CondBranch(cond, truePart, falsePart);
    }

    public Object visit(BinaryOpNode bin) throws CompileError {
        Object lhs = bin.getLhs().accept(this);
        Object rhs = bin.getRhs().accept(this);

        return new BinaryOp(bin.getOperator(), lhs, rhs);
    }

    public Object visit(UnaryOpNode una) throws CompileError {
        Object expr = una.getExpr().accept(this);

        return new UnaryOp(una.getOperator(), expr);
    }

    public Object visit(CallNode call) throws CompileError {
        //TBD need modification, runtime ref resolve
        RefTable current = ctx.peekRefTableStack();
        String name = call.getIdentifier().getName();
        RefTable ref = current.resolveRef(name);

        ArrayNode ar = call.getArgs();
        List<Node> data = ar.getData();
        ArrayList<Object> args = new ArrayList<>();
        for (Node a : data) {
            args.add(a.accept(this));
        }

        return new Call(name, ref, args, ar.getHeaders());
    }

    public Object visit(StringLiteralNode strn) throws CompileError {
        return new StringConstant(strn.getValue());
    }

    public Object visit(IntegerLiteralNode intn) throws CompileError {
        return new IntegerConstant(intn.getValue());
    }

    public Object visit(DoubleLiteralNode doublen) throws CompileError {
        return new DoubleConstant(doublen.getValue());
    }

    public Object visit(TimeLiteralNode time) throws CompileError {
        return new TimeConstant(time.getValue());
    }

    public Object visit(NilNode nil) throws CompileError {
        return new Nil();
    }

    public Object visit(BoolNode bool) throws CompileError {
        return new BoolConstant(bool.getValue());
    }

    public Object visit(GenFuncNode genf) throws CompileError {
        RefTable current = ctx.peekRefTableStack();
        String name = genf.getIdentifier().getName();
        RefTable ref = current.resolveRef(name);

        return new GenericFunc(name, ref);
    }

    public Object visit(FunCallNode fcall) throws CompileError {
        RefTable current = ctx.peekRefTableStack();
        String name = fcall.getId().getName();
        RefTable ref = current.resolveRef(name);

        ArrayNode ar = fcall.getArgs();
        List<Node> data = ar.getData();
        ArrayList<Object> args = new ArrayList<>();
        for (Node a : data) {
            args.add(a.accept(this));
        }

        return new FunCall(name, ref, args, ar.getHeaders());
    }

    public Object visit(PatternLambdaNode plambda) throws CompileError {
        PatternFunc pf = new PatternFunc();

        for (PatternLambdaNode pl = plambda; pl != null; pl = pl.getNext()) {
            pvs = new ArrayList<>();
            FuncArm arm = new FuncArm(pl.getPattern().accept(this));

            if (pl.getCondition() != null) {
                ctx.enterScopeTo(new PatternRefTable(pvs));
                arm.setCondition(pl.getCondition().accept(this));
                ctx.exitScope();
            }

            FuncRefTable refTable = new FuncRefTable();
            for (String arg : pvs) {
                refTable.addArg(arg);
            }

            ctx.enterScopeTo(refTable);
            List<Object> body = new LinkedList<>();
            for (Node stmt : pl.getBody()) {
                body.add(stmt.accept(this));
            }

            ctx.exitScope();

            arm.setBody(body);

            pf.add(arm);
        }

        return pf;
    }

    public Object visit(PatternSplatNode psplt) throws CompileError {
        Node headNode = psplt.getHead();
        PatternVarNode midNode = psplt.getMid();
        Node tailNode = psplt.getTail();

        if (headNode instanceof PatternStructNode) {
            Object head = headNode.accept(this);
            Object vvar = midNode.accept(this);

            return new PatternStruct((PatternStruct)head, vvar);
        }
        else if (headNode instanceof PatternArrayNode) {
            Object head = headNode.accept(this);
            Object vvar = midNode.accept(this);
            if (tailNode instanceof PatternArrayNode) {
                Object tail = tailNode.accept(this);
                return new PatternArray(head, vvar, tail);
            }
            return new PatternArray(head, vvar, null);
        }
        else if (headNode == null) {
            if (tailNode instanceof PatternArrayNode) {
                Object vvar = midNode.accept(this);
                Object tail = tailNode.accept(this);
                return new PatternArray(null, vvar, tail);
            }
            else if (tailNode == null) {
                return new PatternArray(null, midNode.accept(this), null);
            }
        }

        throw new CompileError("illegal splat pattern");
    }

    public Object visit(PatternArrayNode parr) throws CompileError {
        PatternArray pattern = new PatternArray();
        List<Node> pnodes = parr.getData();
        for (Node p : pnodes) {
            pattern.add(p.accept(this));
        }

        return pattern;
    }

    public Object visit(PatternStructNode pstruct) throws CompileError {
        PatternStruct pattern = new PatternStruct();
        List<Node> pnodes = pstruct.getData();
        for (Node p : pnodes) {
            PairNode pair = (PairNode)p;
            pattern.add(pair.getKey(), pair.getValue().accept(this));
        }

        return pattern;
    }

    public Object visit(PatternNamespaceNode pns) throws CompileError {
        PatternNamespace pattern = new PatternNamespace();
        pattern.setName(pns.getName());
        pattern.setPattern(pns.getPattern().accept(this));

        return pattern;
    }

    public Object visit(PatternVarNode pvar) throws CompileError {
        Object pattern;
        String name = pvar.getName();

        if (name.equals("_")) { return new PatternVarBind(-1); }

        int idx = pvs.lastIndexOf(name);
        if (idx < 0) {
            pattern = new PatternVarBind(pvs.size());
            pvs.add(name);
        }
        else {
            pattern = new PatternVarRef(idx);
        }

        return pattern;
    }

    public Object visit(PatternNumberNode pnum) throws CompileError {
        NumberLiteralNode num = pnum.getNumber();

        if (num instanceof IntegerLiteralNode) {
            return new PatternInteger(((IntegerLiteralNode)num).getValue());
        }
        else if (num instanceof DoubleLiteralNode) {
            return new PatternDouble(((DoubleLiteralNode)num).getValue());
        }

        throw new CompileError("not a number pattern");
    }

    public Object visit(PatternStringNode pstr) throws CompileError {
        return new PatternString(pstr.getStr());
    }

    public Object visit(PatternNilNode pnil) throws CompileError {
        return new PatternNil();
    }

    public Object visit(PatternBoolNode pbool) throws CompileError {
        return new PatternBool(pbool.getBool());
    }

    public Object visit(ArgsNode node) throws CompileError {
        throw new CompileError("internal error: AST to IR ArgsNode");
    }

    public Object visit(TypeNode node) throws CompileError {
        throw new CompileError("internal error: AST to IR TypeNode");
    }
}
