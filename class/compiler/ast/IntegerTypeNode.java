package compiler.ast;

public class IntegerTypeNode extends DataTypeNode {
    public IntegerTypeNode(int line, int column) {
        super(line, column);
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}