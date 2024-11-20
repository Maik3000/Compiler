package compiler.ast;

public class VarDeclarationStatement extends StatementNode {
    private VarDeclaration varDeclaration;
    private ExpressionNode initExpression; // Expresión de inicialización

    public VarDeclarationStatement(VarDeclaration varDeclaration, ExpressionNode initExpression) {
        this.varDeclaration = varDeclaration;
        this.initExpression = initExpression;
    }

    public VarDeclaration getVarDeclaration() {
        return varDeclaration;
    }

    public ExpressionNode getInitExpression() {
        return initExpression;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}