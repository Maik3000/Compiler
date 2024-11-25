package compiler.irt;

public class IRTConst extends IRTExpression {
    private int value;

    public IRTConst(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}