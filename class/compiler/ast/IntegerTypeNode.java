package compiler.ast;

public class IntegerTypeNode extends DataTypeNode {
    public IntegerTypeNode(int line, int column) {
        super(line, column);
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerTypeNode;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    @Override
    public String toString() {
        return "int";
    }
}