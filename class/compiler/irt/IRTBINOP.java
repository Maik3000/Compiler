package compiler.irt;

public class IRTBINOP extends IRTExpression {
    public enum Op {
        ADD, SUB, MUL, DIV, AND, OR, LT, LE, GT, GE, EQ, NE
    }

    private Op operator;
    private IRTExpression left;
    private IRTExpression right;

    public IRTBINOP(Op operator, IRTExpression left, IRTExpression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public Op getOperator() { return operator; }
    public IRTExpression getLeft() { return left; }
    public IRTExpression getRight() { return right; }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
