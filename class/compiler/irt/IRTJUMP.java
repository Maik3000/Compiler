package compiler.irt;

public class IRTJUMP extends IRTStatement {
    private String targetLabel;

    public IRTJUMP(String targetLabel) {
        this.targetLabel = targetLabel;
    }

    public String getTargetLabel() {
        return targetLabel;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}