package compiler.ast;

public class CharacterTypeNode extends DataTypeNode {
    public CharacterTypeNode() {}

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}