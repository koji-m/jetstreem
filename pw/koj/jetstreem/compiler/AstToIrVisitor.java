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
        Namespace currentNs = ctx.peekNsStack();
        if (currentNs.hasChild(nsName)) {
            throw CompileError("duplicate namespace definition");
        }
        Namespace ns = new Namespace(nsName, stmts, refTable, currentNs);
        currentNs.addChild(ns);

        ctx.enterNsTo(ns);
        for (Node stmt : nsNode.getStmts()) {
            stmts.add(stmt.accept(this, ctx));
        }
        ctx.exitNs();

        return ns;
   }

    public Ir visit(ImportNode imp, Context ctx) {
        String id = imp.getIdentifier();
        Namespace ns = ctx.peekNsStack();

        Namespace n = ns.lookupNs(id);
        if (n == null) {
            throw new CompileError("namespace not found");
        }

        return new Import(n);
    }

    public Ir visit(LetNode let, Context ctx) {
        Ir rhs = let.getRhs().accept(this, ctx);

        RefTable refTable = ctx.peekRefTableStack();
        String name = ((IdentifierNode)let.getLhs()).getName();
        if (refTable.hasLocal(name)) {
            throw new CompileError("duplicate assignment");
        }
        refTable.addLocal(name);

        return new Let(name, rhs);
   }

    public Ir visit(SkipNode skp, Context ctx) {
        return new Skip();
    }

    public Ir visit(EmitNode emt, Context ctx) {
        ArrayList<Node> arr = emt.getArgs().getData();
        ArrayList<Ir> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this, ctx));
        }

        return new Emit(args);
    }

    public Ir visit(ReturnNode ret, Context ctx) {
        ArrayList<Node> arr = ret.getArgs().getData();
        ArrayList<Ir> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this, ctx));
        }

        return new Return(args);
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
        FuncRefTable refTable = new FuncRefTable();
        List<IdentifierNode> args = lambda.getArgList();

        for (IdentifierNode id : args) {
            refTable.addArg(id.getName());
        }

        ctx.enterScopeTo(refTable);
        List<Ir> body = new LinkedList<>();
        for (Node stmt : lambda.getBody()) {
            body.add(stmt.accept(this, ctx));
        }
        ctx.exitScope();

        return new Function(body, refTable);
    }


    public Ir visit(IdentifierNode id, Context ctx) {
        RefTable current = ctx.peekRefTableStack();
        String name = id.getName();
        RefTable ref = current.resolveRef(name);
        if (ref == null) {
            throw CompileError("variable not defined");
        }

        return new VarRef(name, ref);
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
