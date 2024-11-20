package compiler.ast;

public class UnaryExpression extends ExpressionNode {
    private String operator;
    private ExpressionNode expression;

    public UnaryExpression(String operator, ExpressionNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
