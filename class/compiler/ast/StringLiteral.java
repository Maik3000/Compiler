package compiler.ast;

public class StringLiteral extends LiteralNode {
    private String value;

    public StringLiteral(String value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}

