package compiler.ast;

public class CalloutArgumentExpression extends CalloutArgument {
    private ExpressionNode expression;

    public CalloutArgumentExpression(ExpressionNode expression) {
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