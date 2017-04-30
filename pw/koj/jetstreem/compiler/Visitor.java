package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.ast.*;
import pw.koj.jetstreem.compiler.ir.*;
import java.util.*;

public interface Visitor {
    Object visit(NamespaceNode node) throws CompileError;
    Object visit(ImportNode node) throws CompileError;
    Object visit(LetNode node) throws CompileError;
    Object visit(SkipNode node) throws CompileError;
    Object visit(EmitNode node) throws CompileError;
    Object visit(ReturnNode node) throws CompileError;
    Object visit(LambdaNode node) throws CompileError;
    Object visit(IdentifierNode node) throws CompileError;
    Object visit(ArrayNode node) throws CompileError;
    Object visit(PairNode node) throws CompileError;
    Object visit(SplatNode node) throws CompileError;
    Object visit(IfNode node) throws CompileError;
    Object visit(BinaryOpNode node) throws CompileError;
    Object visit(UnaryOpNode node) throws CompileError;
    Object visit(CallNode node) throws CompileError;
    Object visit(StringLiteralNode node) throws CompileError;
    Object visit(IntegerLiteralNode node) throws CompileError;
    Object visit(DoubleLiteralNode node) throws CompileError;
    Object visit(TimeLiteralNode node) throws CompileError;
    Object visit(NilNode node) throws CompileError;
    Object visit(BoolNode node) throws CompileError;
    Object visit(GenFuncNode node) throws CompileError;
    Object visit(FunCallNode node) throws CompileError;
    Object visit(PatternLambdaNode node) throws CompileError;
    Object visit(PatternSplatNode node) throws CompileError;
    Object visit(PatternArrayNode node) throws CompileError;
    Object visit(PatternStructNode node) throws CompileError;
    Object visit(PatternNamespaceNode node) throws CompileError;
    Object visit(PatternVarNode node) throws CompileError;
    Object visit(PatternNumberNode node) throws CompileError;
    Object visit(PatternStringNode node) throws CompileError;
    Object visit(PatternNilNode node) throws CompileError;
    Object visit(PatternBoolNode node) throws CompileError;
    Object visit(ArgsNode node) throws CompileError;
    Object visit(TypeNode node) throws CompileError;
}

