package compiler.ast;

public class ExpressionStatement extends StatementNode {
    private ExpressionNode expression;

    public ExpressionStatement(ExpressionNode expression, int line, int column) {
        super(line, column);
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
