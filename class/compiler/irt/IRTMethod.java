package compiler.irt;

public class IRTMethod extends IRTStatement {
    private String name;
    private IRTStatement body;

    public IRTMethod(String name, IRTStatement body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public IRTStatement getBody() {
        return body;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
