package compiler.ast;

public class ArrayLocation extends LocationNode {
    private ExpressionNode index;

    public ArrayLocation(String name, ExpressionNode index, int line, int column) {
        super(name, line, column);
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public ExpressionNode getIndex() {
        return index;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getter
}