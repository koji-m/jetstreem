package pw.koj.jetstreem.compiler;

import java.util.*;


public class AstToIrVisitor implements Visitor {
    List<Stmt> stmts;

    
    //
    // Entry point
    //
    
    public List<Ir> transform(List<Node> ast) {
        Context ctx = new Context("StrmTop");
        return visit(ast, ctx);
    }


    // 
    // visit functions
    // 

    public Ir visit(NamespaceNode nsNode, Context ctx) {
        String nsName = nsNode.getName();
        List<Ir> stmts = new LinkedList<>();
        NsRefTable refTable = new NsRefTable(nsName);
        Namespace ns = new Namespace(nsName, stmts, refTable, ctx.peekNsStack());

        ctx.enterNsTo(ns);
        for (Node stmt : nsNode.getStmts()) {
            stmts.add(stmt.accept(this, ctx));
        }
        ctx.exitNs();

        return ns;
    }

    public Ir visit(ImportNode imp, Context ctx) {
        //TBD
    }

    public Ir visit(LetNode let, Context ctx) {
        RefTable refTable = ctx.peek();
        refTable.addLocalRef((IdentifierNode)let.getLhs());
        return let.accept(this, ctx);
    }

    public Ir visit(SkipNode skp, Context ctx) {
        //TBD
    }

    public Ir visit(EmitNode emt, Context ctx) {
        //TBD
    }

    public Ir visit(ReturnNode ret, Context ctx) {
        //TBD
    }

    public Ir visit(ExprNode expr, Context ctx) {
        return expr.accept(this, ctx);
    }

    public List<Ir> visit(List<Node> stmts, Context ctx) {
        List<Ir> stmtsIr = new LinkedList<>();

        for (Node stmt : stmts) {
            Ir s = stmt.accept(this, ctx);
            stmtsIr.add(s);
        }

        return stmtsIr;
    }

    public Ir visit(LambdaNode lambda, Context ctx) {
        ctx.enterScope();
        RefTable refTable = ctx.peek();
        refTable.addArgRef(node.getArgs());
        Ir body = lambda.accept(this, ctx);
        refTable.index();
        ctx.exitScope();

        return new StrmFunction(body, refTable);
    }


    public Ir visit(IdentifierNode id, Context ctx) {
        RefTable refTable = ctx.peek();
        Reference ref = refTable.resolveRef(id, new LinkedList<RefTable>());

        return new VarRef(id, ref);
    }

    public Ir visit(ArrayNode arr, Context ctx) {
        GenArray ar = new GenArray();
        List<Node> data = arr.getData();
        for (Node expr : data) {
            ar.add(expr.accept(this, ctx));
        }
        ar.setHeaders(arr.getHeaders());
        return ar;
    }

    public Ir visit(ArgsNode node, Context ctx) {
        //TBD
    }

    public Ir visit(PairNode pair, Context ctx) {
        //TBD
    }

    public Ir visit(SplatNode splt, Context ctx) {
        //TBD
    }

    public Ir visit(IfNode ifn, Context ctx) {
        Ir cond = ifn.getCond().accept();
        Ir thenBody = ifn.getThenBody().accept();
        Node els = ifn.getElseBody();
        if (els == null) {
            return new CondBranch(cond, thenBody);
        }
        Ir elseBody = els.accept();

        return new CondBranch(cond, thenBody, elseBody);
    }

    public Ir visit(BinaryOpNode bin, Context ctx) {
        Ir lhs = bin.getLhs().accept(this, ctx);
        Ir rhs = bin.getRhs().accept(this, ctx);

        return new BinaryOp(bin.getOperator(), lhs, rhs);
    }

    public Ir visit(UnaryOpNode una, Context ctx) {
        Ir expr = una.getExpr().accespt(this, ctx);

        return new UnaryOp(una.getOperator(), expr);
    }

    public Ir visit(CallNode call, Context ctx) {
        //TBD need modification, runtime ref resolve
        RefTable refTable = ctx.peek();
        IdentifierNode id = call.getIdentifier();
        Reference ref = refTable.resolveRef(id, new LinkedList<RefTable>());

        ArrayNode ar = call.getArgs();
        List<Node> data = ar.getData();
        List<Ir> args = new ArrayList<Ir>();
        for (Node a : data) {
            args.add(a.accept(this, ctx));
        }

        return new Call(id, ref, args, ar.getHeaders());
    }

    public Ir visit(StringLiteralNode strn, Context ctx) {
        return new StringConstant(strn.getValue());
    }

    public Ir visit(IntegerLiteralNode intn, Context ctx) {
        return new IntegerConstant(intn.getValue());
    }

    public Ir visit(DoubleLiteralNode doublen, Context ctx) {
        return new DoubleConstant(doublen.getValue());
    }

    public Ir visit(TimeLiteralNode time, Context ctx) {
        return new TimeConstant(time.getValue());
    }

    public Ir visit(NilNode nil, Context ctx) {
        return new Nil();
    }

    public Ir visit(BoolNode bool, Context ctx) {
        return new BoolConstant(bool.getValue());
    }

    public Ir visit(GenFuncNode genf, Context ctx) {
        RefTable refTable = ctx.peek();
        IdentifierNode id = genf.getIdentifier();
        Reference ref = refTable.resolveRef(id, new LinkedList<RefTable>());

        return new GenericFunc(id, ref);
    }

    public Ir visit(FunCallNode fcall, Context ctx) {
        //TBD need modification, runtime ref resolve
        RefTable refTable = ctx.peek();
        IdentifierNode id = fcall.getIdentifier();
        Reference ref = refTable.resolveRef(id, new LinkedList<RefTable>());

        ArrayNode ar = call.getArgs();
        List<Node> data = ar.getData();
        List<Ir> args = new ArrayList<Ir>();
        for (Node a : data) {
            args.add(a.accept(this, ctx));
        }

        return new FunCall(id, ref, args, ar.getHeaders());
    }

    public Ir visit(PatternLambdaNode plambda, Context ctx) {
        //TBD
    }

    public Ir visit(PatternSplatNode psplt, Context ctx) {
        //TBD
    }

    public Ir visit(PatternArrayNode parr, Context ctx) {
        //TBD
    }

    public Ir visit(PatternStructNode parr, Context ctx) {
        //TBD
    }

    public Ir visit(PatternNamespaceNode pns, Context ctx) {
        //TBD
    }

}
