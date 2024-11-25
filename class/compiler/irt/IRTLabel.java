package compiler.irt;

public class IRTLabel extends IRTStatement {
    private String label;

    public IRTLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}