package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.parser.*;
import pw.koj.jetstreem.compiler.ir.*;
import java.util.*;

public interface Visitor {
    IrNode visit(NamespaceNode node) throws CompileError;
    IrNode visit(ImportNode node) throws CompileError;
    IrNode visit(LetNode node) throws CompileError;
    IrNode visit(SkipNode node) throws CompileError;
    IrNode visit(EmitNode node) throws CompileError;
    IrNode visit(ReturnNode node) throws CompileError;
    IrNode visit(LambdaNode node) throws CompileError;
    IrNode visit(IdentifierNode node) throws CompileError;
    IrNode visit(ArrayNode node) throws CompileError;
    IrNode visit(PairNode node) throws CompileError;
    IrNode visit(SplatNode node) throws CompileError;
    IrNode visit(IfNode node) throws CompileError;
    IrNode visit(BinaryOpNode node) throws CompileError;
    IrNode visit(UnaryOpNode node) throws CompileError;
    IrNode visit(CallNode node) throws CompileError;
    IrNode visit(StringLiteralNode node) throws CompileError;
    IrNode visit(IntegerLiteralNode node) throws CompileError;
    IrNode visit(DoubleLiteralNode node) throws CompileError;
    IrNode visit(TimeLiteralNode node) throws CompileError;
    IrNode visit(NilNode node) throws CompileError;
    IrNode visit(BoolNode node) throws CompileError;
    IrNode visit(GenFuncNode node) throws CompileError;
    IrNode visit(FunCallNode node) throws CompileError;
    IrNode visit(PatternLambdaNode node) throws CompileError;
    IrNode visit(PatternSplatNode node) throws CompileError;
    IrNode visit(PatternArrayNode node) throws CompileError;
    IrNode visit(PatternStructNode node) throws CompileError;
    IrNode visit(PatternNamespaceNode node) throws CompileError;
    IrNode visit(PatternVarNode node) throws CompileError;
    IrNode visit(PatternNumberNode node) throws CompileError;
    IrNode visit(PatternStringNode node) throws CompileError;
    IrNode visit(PatternNilNode node) throws CompileError;
    IrNode visit(PatternBoolNode node) throws CompileError;
    IrNode visit(ArgsNode node) throws CompileError;
    IrNode visit(TypeNode node) throws CompileError;
}

