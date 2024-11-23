package compiler.ast;

public class CharacterTypeNode extends DataTypeNode {
    public CharacterTypeNode(int line, int column) {
        super(line, column);
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}