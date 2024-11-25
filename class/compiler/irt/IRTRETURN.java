package compiler.irt;

public class IRTRETURN extends IRTStatement {
    private IRTExpression expression;

    public IRTRETURN(IRTExpression expression) {
        this.expression = expression;
    }

    public IRTExpression getExpression() {
        return expression;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
