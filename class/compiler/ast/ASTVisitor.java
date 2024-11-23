package compiler.ast;

public interface ASTVisitor {
    void visit(ProgramNode node);
    void visit(ClassMember node);
    void visit(VarDeclaration node);
    void visit(MethodDeclaration node);
    void visit(MultipleVarDeclaration node);
    void visit(BlockNode node);

    void visit(ExpressionNode node);
    void visit(ExpressionStatement node);
    void visit(AssignmentExpression node);
    void visit(BinaryExpression node);
    void visit(UnaryExpression node);
    void visit(NewArrayExpression node);

    void visit(StatementNode node);
    void visit(AssignmentStatement node);
    void visit(MethodCallStatement node);
    void visit(IfStatement node);
    void visit(WhileStatement node);
    void visit(ForStatement node);
    void visit(ReturnStatement node);
    void visit(BreakStatement node);
    void visit(ContinueStatement node);

    void visit(LocationNode node);
    void visit(VariableLocation node);
    void visit(ArrayLocation node);

    void visit(MethodCallNode node);
    void visit(NormalMethodCallNode node);
    void visit(CalloutCallNode node);
    
    void visit(LiteralNode node);
    void visit(StringLiteral node);
    void visit(IntegerLiteral node);
    void visit(CharacterLiteral node);
    void visit(BooleanLiteral node);

    void visit(DataTypeNode node);
    void visit(IntegerTypeNode node);
    void visit(BooleanTypeNode node);
    void visit(CharacterTypeNode node);
    void visit(VoidTypeNode node);

    void visit(Parameter node);
    void visit(CalloutArgument node);
    void visit(CalloutArgumentExpression node);
    void visit(CalloutArgumentString node);
    void visit(VarDeclarationStatement node);
    void visit(VariableNode node);

    void visit(ASTNode node);

}