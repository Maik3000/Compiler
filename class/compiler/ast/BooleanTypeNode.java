package compiler.ast;


public class BooleanTypeNode extends DataTypeNode {
    public BooleanTypeNode() {}

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}