package compiler.ast;

public class VoidTypeNode extends DataTypeNode {
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
