package pw.koj.jetstreem.compiler;

import java.util.*;
import pw.koj.jetstreem.parser.*;
import pw.koj.jetstreem.compiler.ir.*;


public class AstToIrVisitor implements Visitor {

    // dummy standard lib symbols
    private static final String[] BUILTIN = {
        "stdin", "stdout", "seq", "map", "each", "filter",
        "tcp_server", "tcp_socket", "chan", "print" 
    };

    private static RefTable builtIn;

    static {
        builtIn = new NsRefTable("pw/koj/jetstreem/runtime/BuiltIn");
        for (String s : BUILTIN) {
            builtIn.addLocal(s);
        }
    }

    private Context ctx;
    private ArrayList<String> pvs;
    private int vlvarIndex;
    
    // Entry point
    public IrNode transform(NamespaceNode ast) throws CompileError {
        ctx = new Context();
        return visit(ast);
    }


    public IrNode visit(NamespaceNode nsNode) throws CompileError {
        String nsName = nsNode.getName();
        List<IrNode> stmts = new LinkedList<>();
        NsRefTable refTable = new NsRefTable(nsName);
        Namespace currentNs = ctx.peekNsStack();
        Namespace ns;
        if (currentNs == null) {
            
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

    public IrNode visit(ImportNode imp) throws CompileError {
        String id = imp.getIdentifier();
        Namespace ns = ctx.peekNsStack();

        Namespace n = ns.lookupNs(id);
        if (n == null) {
            throw new CompileError("namespace not found: " + id);
        }

        return new Import(n);
    }

    public IrNode visit(LetNode let) throws CompileError {
        IrNode rhs = let.getRhs().accept(this);

        RefTable refTable = ctx.peekRefTableStack();
        String name = ((IdentifierNode)let.getLhs()).getName();
        if (refTable.hasLocal(name)) {
            throw new CompileError("duplicate assignment: " + name);
        }
        refTable.addLocal(name);

        return new Let(name, rhs);
   }

    public IrNode visit(SkipNode skp) throws CompileError {
        return new Skip();
    }

    public IrNode visit(EmitNode emt) throws CompileError {
        List<Node> arr = emt.getArgs().getData();
        List<IrNode> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this));
        }

        return new Emit(args);
    }

    public IrNode visit(ReturnNode ret) throws CompileError {
        List<Node> arr = ret.getArgs().getData();
        List<IrNode> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this));
        }

        return new Return(args);
    }

    public IrNode visit(LambdaNode lambda) throws CompileError {
        if (lambda.isBlock()) {
            List<IrNode> body = new LinkedList<>();
            for (Node stmt : lambda.getBody()) {
                body.add(stmt.accept(this));
            }
            return new Block(body);
        }

        FuncRefTable refTable = new FuncRefTable();
        List<IdentifierNode> args = lambda.getArgList();

        for (IdentifierNode id : args) {
            refTable.addArg(id.getName());
        }

        ctx.enterScopeTo(refTable);
        List<IrNode> body = new LinkedList<>();
        for (Node stmt : lambda.getBody()) {
            body.add(stmt.accept(this));
        }
        ctx.exitScope();

        return new Function(body, refTable);
    }


    public IrNode visit(IdentifierNode id) throws CompileError {
        RefTable current = ctx.peekRefTableStack();
        String name = id.getName();
        RefTable ref = current.resolveRef(name);
        if (ref == null) {
            ref = builtIn.resolveRef(name);
            if (ref == null) {
                throw new CompileError("variable not defined");
            }
        }

        return new VarRef(name, ref);
   }

    public IrNode visit(ArrayNode arr) throws CompileError {
        GenArray ar = new GenArray();
        List<Node> data = arr.getData();
        for (Node expr : data) {
            ar.add(expr.accept(this));
        }
        ar.setHeaders(arr.getHeaders());

        String nsName = arr.getNs();
        if (nsName != null) {
            Namespace currentNs = ctx.peekNsStack();
            Namespace ns = currentNs.lookupNs(nsName);
            if (ns == null) {
                throw new CompileError("namespace not found");
            }
            ar.setNs(ns);
        }
        return ar;
    }

    public IrNode visit(PairNode pair) throws CompileError {
        return new Pair(pair.getKey(), pair.getValue().accept(this));
    }

    public IrNode visit(SplatNode splt) throws CompileError {
        return new Splat(splt.getNode().accept(this));
    }

    public IrNode visit(IfNode ifn) throws CompileError {
        IrNode cond = ifn.getCond().accept(this);

        IrNode truePart = ifn.getThenBody().accept(this);

        Node els = ifn.getElseBody();
        if (els == null) {
            return new CondBranch(cond, truePart);
        }

        IrNode falsePart = els.accept(this);

        return new CondBranch(cond, truePart, falsePart);
    }

    public IrNode visit(BinaryOpNode bin) throws CompileError {
        IrNode lhs = bin.getLhs().accept(this);
        IrNode rhs = bin.getRhs().accept(this);

        return new BinaryOp(bin.getOperator(), lhs, rhs);
    }

    public IrNode visit(UnaryOpNode una) throws CompileError {
        IrNode expr = una.getExpr().accept(this);

        return new UnaryOp(una.getOperator(), expr);
    }

    public IrNode visit(CallNode call) throws CompileError {
        //TBD need modification, runtime ref resolve
        RefTable current = ctx.peekRefTableStack();
        String name = call.getIdentifier().getName();
        RefTable ref = current.resolveRef(name);
        if (ref == null) {
            ref = builtIn.resolveRef(name);
        }

        ArrayNode ar = call.getArgs();
        List<Node> data = ar.getData();
        ArrayList<IrNode> args = new ArrayList<>();
        for (Node a : data) {
            args.add(a.accept(this));
        }

        return new Call(name, ref, args, ar.getHeaders());
    }

    public IrNode visit(StringLiteralNode strn) throws CompileError {
        return new StringConstant(strn.getValue());
    }

    public IrNode visit(IntegerLiteralNode intn) throws CompileError {
        return new IntegerConstant(intn.getValue());
    }

    public IrNode visit(DoubleLiteralNode doublen) throws CompileError {
        return new DoubleConstant(doublen.getValue());
    }

    public IrNode visit(TimeLiteralNode time) throws CompileError {
        return new TimeConstant(time.getValue());
    }

    public IrNode visit(NilNode nil) throws CompileError {
        return new Nil();
    }

    public IrNode visit(BoolNode bool) throws CompileError {
        return new BoolConstant(bool.getValue());
    }

    public IrNode visit(GenFuncNode genf) throws CompileError {
        RefTable current = ctx.peekRefTableStack();
        String name = genf.getIdentifier().getName();
        RefTable ref = current.resolveRef(name);
        if (ref == null) {
            ref = builtIn.resolveRef(name);
        }

        return new GenericFunc(name, ref);
    }

    public IrNode visit(FunCallNode fcall) throws CompileError {
        RefTable current = ctx.peekRefTableStack();
        String name = fcall.getId().getName();
        RefTable ref = current.resolveRef(name);

        ArrayNode ar = fcall.getArgs();
        List<Node> data = ar.getData();
        ArrayList<IrNode> args = new ArrayList<>();
        for (Node a : data) {
            args.add(a.accept(this));
        }

        return new FunCall(name, ref, args, ar.getHeaders());
    }

    public IrNode visit(PatternLambdaNode plambda) throws CompileError {
        List<PatternLambdaNode> plst = new ArrayList<>();
        int nArms = 0;
        for (PatternLambdaNode p = plambda; p != null; p = p.getNext()) {
            plst.add(p);
            nArms++;
        }

        PatternFuncRefTable refTable = new PatternFuncRefTable(nArms);
        PatternFunc pf = new PatternFunc(refTable);

        ctx.enterScopeTo(refTable);

        int idx = 0;
        for (PatternLambdaNode pl : plst) {
            pvs = new ArrayList<>();
            vlvarIndex = -1;
            FuncArm arm = new FuncArm(pl.getPattern().accept(this));

            refTable.switchToNewLocalRefs(idx++);
            for (String arg : pvs) {
                refTable.addLocal(arg);
            }


            if (pl.getCondition() != null) {
                arm.setCondition(pl.getCondition().accept(this));
            }

            List<IrNode> body = new LinkedList<>();
            for (Node stmt : pl.getBody()) {
                body.add(stmt.accept(this));
            }

            arm.setBody(body);

            pf.add(arm);
        }

        ctx.exitScope();

        return pf;
    }

    public IrNode visit(PatternSplatNode psplt) throws CompileError {
        Node headNode = psplt.getHead();
        PatternVlenVarNode midNode = psplt.getMid();
        Node tailNode = psplt.getTail();

        if (headNode instanceof PatternStructNode) {
            IrNode head = headNode.accept(this);
            IrNode vvar = midNode.accept(this);

            return new PatternStruct((PatternStruct)head, vvar);
        }
        else if (headNode instanceof PatternArrayNode) {
            IrNode head = headNode.accept(this);
            IrNode vvar = midNode.accept(this);
            if (tailNode instanceof PatternArrayNode) {
                IrNode tail = tailNode.accept(this);
                return new PatternArray(head, vvar, tail);
            }
            return new PatternArray(head, vvar, null);
        }
        else if (headNode == null) {
            if (tailNode instanceof PatternArrayNode) {
                IrNode vvar = midNode.accept(this);
                IrNode tail = tailNode.accept(this);
                return new PatternArray(null, vvar, tail);
            }
            else if (tailNode == null) {
                return new PatternArray(null, midNode.accept(this), null);
            }
        }

        throw new CompileError("illegal splat pattern");
    }

    public IrNode visit(PatternArrayNode parr) throws CompileError {
        PatternArray pattern = new PatternArray();
        List<Node> pnodes = parr.getData();
        for (Node p : pnodes) {
            pattern.add(p.accept(this));
        }

        return pattern;
    }

    public IrNode visit(PatternStructNode pstruct) throws CompileError {
        PatternStruct pattern = new PatternStruct();
        List<Node> pnodes = pstruct.getData();
        for (Node p : pnodes) {
            PairNode pair = (PairNode)p;
            pattern.add(pair.getKey(), pair.getValue().accept(this));
        }

        return pattern;
    }

    public IrNode visit(PatternNamespaceNode pns) throws CompileError {
        PatternNamespace pattern = new PatternNamespace();
        pattern.setName(pns.getName());
        pattern.setPattern(pns.getPattern().accept(this));

        return pattern;
    }

    public IrNode visit(PatternVarNode pvar) throws CompileError {
        IrNode pattern;
        String name = pvar.getName();

        if (name.equals("_")) { return new PatternVarBind(-1); }

        int idx = pvs.lastIndexOf(name);
        if (idx < 0) {
            pattern = new PatternVarBind(pvs.size());
            pvs.add(name);
        }
        else if (idx != vlvarIndex) {
            pattern = new PatternVarRef(idx);
        }
        else {
            throw new CompileError("invalid pattern: *" + name + " already occured");
        }

        return pattern;
    }

    public IrNode visit(PatternVlenVarNode pvlvar) throws CompileError {
        IrNode pattern;
        String name = pvlvar.getName();

        if (name.equals("_")) { throw new CompileError("invalid pattern: *_"); }

        int idx = pvs.lastIndexOf(name);
        if (idx < 0) {
            pattern = new PatternVlenVarBind(pvs.size());
            vlvarIndex = pvs.size();
            pvs.add(name);
        }
        else {
            throw new CompileError("invalid pattern: *" + name);
        }

        return pattern;
    }

    public IrNode visit(PatternNumberNode pnum) throws CompileError {
        NumberLiteralNode num = pnum.getNumber();

        if (num instanceof IntegerLiteralNode) {
            return new PatternInteger(((IntegerLiteralNode)num).getValue());
        }
        else if (num instanceof DoubleLiteralNode) {
            return new PatternDouble(((DoubleLiteralNode)num).getValue());
        }

        throw new CompileError("not a number pattern");
    }

    public IrNode visit(PatternStringNode pstr) throws CompileError {
        return new PatternString(pstr.getStr());
    }

    public IrNode visit(PatternNilNode pnil) throws CompileError {
        return new PatternNil();
    }

    public IrNode visit(PatternBoolNode pbool) throws CompileError {
        return new PatternBool(pbool.getBool());
    }

    public IrNode visit(ArgsNode node) throws CompileError {
        throw new CompileError("internal error: AST to IR ArgsNode");
    }

    public IrNode visit(TypeNode node) throws CompileError {
        throw new CompileError("internal error: AST to IR TypeNode");
    }
}
