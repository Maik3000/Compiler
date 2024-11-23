package compiler.ast;

public class VariableNode extends ASTNode{
    private String name;
    private boolean isArray;

    public VariableNode(String name, boolean isArray, int line, int column) {
        super(line, column);
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
