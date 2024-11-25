package compiler.irt;

public class IRTMOVE extends IRTStatement {
    private IRTExpression dest;
    private IRTExpression src;

    public IRTMOVE(IRTExpression dest, IRTExpression src) {
        this.dest = dest;
        this.src = src;
    }

    public IRTExpression getDest() { return dest; }
    public IRTExpression getSrc() { return src; }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}