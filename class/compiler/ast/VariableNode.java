package compiler.ast;

public class VariableNode {
    private String name;
    private boolean isArray;

    public VariableNode(String name, boolean isArray) {
        this.name = name;
        this.isArray = isArray;
    }

    public String getName() {
        return name;
    }

    public boolean isArray() {
        return isArray;
    }

    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}
