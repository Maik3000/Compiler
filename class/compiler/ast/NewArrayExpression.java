package compiler.ast;

public class NewArrayExpression extends ExpressionNode {
    private DataTypeNode type;
    private ExpressionNode size;

    public NewArrayExpression(DataTypeNode type, ExpressionNode size) {
        this.type = type;
        this.size = size;
    }

    public DataTypeNode getType() {
        return type;
    }

    public ExpressionNode getSize() {
        return size;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}