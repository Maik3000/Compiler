package compiler.ast;

public abstract class ExpressionNode {
    public abstract void accept(ASTVisitor v);
    protected DataTypeNode type;

    public DataTypeNode getType() {
        return type;
    }

    public void setType(DataTypeNode type) {
        this.type = type;
    }
}