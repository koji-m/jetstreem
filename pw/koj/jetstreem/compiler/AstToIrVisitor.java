package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.compiler.ir.*;
import java.util.*;


public class AstToIrVisitor implements Visitor {

    private Context ctx;
    private ArrayList<String> pvs;
    
    //
    // Entry point
    //
    
    public List<Object> transform(Object ast) {
        ctx = new Context("StrmTop");
        return visit((List<Node>)ast);
    }


    public Object visit(NamespaceNode nsNode) {
        String nsName = nsNode.getName();
        List<Object> stmts = new LinkedList<>();
        NsRefTable refTable = new NsRefTable(nsName);
        Namespace currentNs = ctx.peekNsStack();
        if (currentNs.hasChild(nsName)) {
            throw CompileError("duplicate namespace definition");
        }
        Namespace ns = new Namespace(nsName, stmts, refTable, currentNs);
        currentNs.addChild(ns);

        ctx.enterNsTo(ns);
        for (Node stmt : nsNode.getStmts()) {
            stmts.add(stmt.accept(this));
        }
        ctx.exitNs();

        return ns;
   }

    public Object visit(ImportNode imp) {
        String id = imp.getIdentifier();
        Namespace ns = ctx.peekNsStack();

        Namespace n = ns.lookupNs(id);
        if (n == null) {
            throw new CompileError("namespace not found: " + id);
        }

        return new Import(n);
    }

    public Object visit(LetNode let) {
        Object rhs = let.getRhs().accept(this);

        RefTable refTable = ctx.peekRefTableStack();
        String name = ((IdentifierNode)let.getLhs()).getName();
        if (refTable.hasLocal(name)) {
            throw new CompileError("duplicate assignment: " + name);
        }
        refTable.addLocal(name);

        return new Let(name, rhs);
   }

    public Object visit(SkipNode skp) {
        return new Skip();
    }

    public Object visit(EmitNode emt) {
        ArrayList<Node> arr = emt.getArgs().getData();
        ArrayList<Object> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this));
        }

        return new Emit(args);
    }

    public Object visit(ReturnNode ret) {
        ArrayList<Node> arr = ret.getArgs().getData();
        ArrayList<Object> args = new ArrayList<>();
        for (Node node : arr) {
            args.add(node.accept(this));
        }

        return new Return(args);
    }

    public Object visit(ExprNode expr) {
        //necessity unknown
        return expr.accept(this);
    }

    public List<Object> visit(List<Node> stmts) {
        List<Object> stmtsIr = new LinkedList<>();

        for (Node stmt : stmts) {
            Object s = stmt.accept(this);
            stmtsIr.add(s);
        }

        return stmtsIr;
    }

    public Object visit(LambdaNode lambda) {
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


    public Object visit(IdentifierNode id) {
        RefTable current = ctx.peekRefTableStack();
        String name = id.getName();
        RefTable ref = current.resolveRef(name);
        if (ref == null) {
            throw new CompileError("variable not defined");
        }

        return new VarRef(name, ref);
   }

    public Object visit(ArrayNode arr) {
        GenArray ar = new GenArray();
        ArrayList<Node> data = arr.getData();
        for (Node expr : data) {
            ar.add(expr.accept(this));
        }
        ar.setHeaders(arr.getHeaders());
        ar.setNs(arr.getNs());
        return ar;
    }

    public Object visit(ArgsNode node) {
        //TBD
    }

    public Object visit(PairNode pair) {
        //TBD
    }

    public Object visit(SplatNode splt) {
        //TBD
    }

    public Object visit(IfNode ifn) {
        Object cond = ifn.getCond().accept(this);
        Object thenBody = ifn.getThenBody().accept(this);
        Node els = ifn.getElseBody();
        if (els == null) {
            return new CondBranch(cond, thenBody);
        }
        Object elseBody = els.accept(this);

        return new CondBranch(cond, thenBody, elseBody);
    }

    public Object visit(BinaryOpNode bin) {
        Object lhs = bin.getLhs().accept(this);
        Object rhs = bin.getRhs().accept(this);

        return new BinaryOp(bin.getOperator(), lhs, rhs);
    }

    public Object visit(UnaryOpNode una) {
        Object expr = una.getExpr().accept(this);

        return new UnaryOp(una.getOperator(), expr);
    }

    public Object visit(CallNode call) {
        //TBD need modification, runtime ref resolve
        RefTable current = ctx.peek();
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

    public Object visit(StringLiteralNode strn) {
        return new StringConstant(strn.getValue());
    }

    public Object visit(IntegerLiteralNode intn) {
        return new IntegerConstant(intn.getValue());
    }

    public Object visit(DoubleLiteralNode doublen) {
        return new DoubleConstant(doublen.getValue());
    }

    public Object visit(TimeLiteralNode time) {
        return new TimeConstant(time.getValue());
    }

    public Object visit(NilNode nil) {
        return new Nil();
    }

    public Object visit(BoolNode bool) {
        return new BoolConstant(bool.getValue());
    }

    public Object visit(GenFuncNode genf) {
        RefTable current = ctx.peek();
        String name = genf.getIdentifier().getName();
        Reference ref = current.resolveRef(name);

        return new GenericFunc(name, ref);
    }

    public Object visit(FunCallNode fcall) {
        RefTable current = ctx.peek();
        String name = fcall.getId().getName();
        Reference ref = current.resolveRef(name);

        ArrayNode ar = call.getArgs();
        List<Node> data = ar.getData();
        ArrayList<Object> args = new ArrayList<>();
        for (Node a : data) {
            args.add(a.accept(this));
        }

        return new FunCall(name, ref, args, ar.getHeaders());
    }

    public Object visit(PatternLambdaNode plambda) {
        PatternFunc pf = new PatternFunc();

        for (PatternLambdaNode pl = plambda; pl != null; pl = pl.getNext()) {
            pvs = new ArrayList<>();
            FuncArm arm = new FuncArm(pl.getPattern().accept(this));

            ctx.enterScopeTo(new PatternRefTable(pvs));
            arm.setCondition(pl.getCondition().accept(this));
            ctx.exitScope();

            FuncRefTable refTable = new FuncRefTable();
            for (String arg : pvs) {
                refTable.addArg(arg);
            }

            ctx.enterScopeTo(refTable);
            List<Object> body = new LinkedList<>();
            for (Node stmt : plambda.getBody()) {
                body.add(stmt.accept(this));
            }

            ctx.exitScope();

            arm.setBody(body);

            pf.add(arm);
        }

        return pf;
    }

    public Object visit(PatternSplatNode psplt) {
        Node headNode = psplt.getHead();
        PatternVarNode midNode = psplt.getMid();
        Node tailNode = psplt.getTail();

        if (headNode instanceof PatternStructNode) {
            Object head = headNode.accept(this);
            Object vvar = midNode.accept(this);

            return new PatternStruct(head, vvar);
        }
        else if (headNode instanceof PatternArrayNode) {
            Object head = headNode.accept(this);
            Object vvar = midNode.accpet(this);
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
                return new PatternArray(null, midNode.accept(this), true);
            }
        }

        throw new CompileError("illegal splat pattern");

    }

    public Object visit(PatternArrayNode parr) {
        PatternArray pattern = new PatternArray();
        List<Node> pnodes = parr.getData();
        for (Node p : pnodes) {
            pattern.add(p.accept(this));
        }

        return pattern;
    }

    public Object visit(PatternStructNode pstruct) {
        PatternStruct pattern = new PatternStruct();
        List<Node> pnodes = pstruct.getData();
        for (Node p : pnodes) {
            PairNode pair = (PairNode)p;
            pattern.add(pair.getKey(), pair.getValue().accept(this));
        }

        return pattern;
    }

    public Object visit(PatternNamespaceNode pns) {
        PatternNamespace pattern = new PatternNamespace();
        pattern.setName(pns.getName());
        pattern.setPattern(pns.getPattern().accept(this));

        return pattern;
    }

    public Object visit(PatternVarNode pvar) {
        Object pattern;
        String name = pvar.getName();
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

    public Object visit(PatternNumberNode pnum) {
        NumberLiteralNode num = pnum.getNumber();

        if (num instanceof IntegerLiteralNode) {
            return new PatternInteger(((IntegerLiteralNode)num).getValue());
        }
        else if (num instanceof DoubleLiteralNode) {
            return new PatternDouble(((DoubleLiteralNode)num).getValue());
        }

        throw new CompileError("not a number pattern");
    }

    public Object visit(PatternStringNode pstr) {
        return new PatternString(pstr.getStr());
    }

    public Object visit(PatternNilNode pnil) {
        return new PatternNil();
    }

    public Object visit(PatternBoolNode pbool) {
        return new PatternBool(pbool.getBool());
    }


}
