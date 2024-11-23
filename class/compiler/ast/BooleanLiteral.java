package compiler.ast;

public class BooleanLiteral extends LiteralNode {
    private boolean value;

    public BooleanLiteral(boolean value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getter
}
