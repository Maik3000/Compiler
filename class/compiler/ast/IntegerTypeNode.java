package compiler.ast;

public class IntegerTypeNode extends DataTypeNode {
    public IntegerTypeNode() {}

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}