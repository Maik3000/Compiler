package compiler.ast;

public class ReturnStatement extends StatementNode {
    private ExpressionNode expression; // Puede ser null

    public ReturnStatement(ExpressionNode expression, int line, int column) {
        super(line, column);
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getter
}
