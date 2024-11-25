package compiler.irt;

public class IRTTEMP extends IRTExpression {
    private String name;

    public IRTTEMP(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
