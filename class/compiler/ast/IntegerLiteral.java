package compiler.ast;

public class IntegerLiteral extends LiteralNode {
    private int value;

    public IntegerLiteral(int value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getter
}