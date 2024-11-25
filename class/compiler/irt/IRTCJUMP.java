package compiler.irt;

public class IRTCJUMP extends IRTStatement {
    private IRTExpression condition;
    private String trueLabel;
    private String falseLabel;

    public IRTCJUMP(IRTExpression condition, String trueLabel, String falseLabel) {
        this.condition = condition;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    public IRTExpression getCondition() {
        return condition;
    }

    public String getTrueLabel() {
        return trueLabel;
    }

    public String getFalseLabel() {
        return falseLabel;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
