package compiler.ast;

public class CharacterLiteral extends LiteralNode {
    private char value;

    public CharacterLiteral(char value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    public char getValue() {
        return value;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getter
}