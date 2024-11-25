package compiler.irt;

public class IREXP extends IRTStatement {
    private IRTExpression expression;

    public IREXP(IRTExpression expression) {
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

