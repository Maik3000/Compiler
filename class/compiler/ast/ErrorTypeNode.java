package compiler.ast;

public class ErrorTypeNode extends DataTypeNode {
    public ErrorTypeNode(int line, int column) {
        super(line, column);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public String toString() {
        return "error";
    }
}