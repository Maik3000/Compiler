package compiler.ast;

public abstract class DataTypeNode extends ASTNode{
    public abstract void accept(ASTVisitor v);

    public DataTypeNode(int line, int column) {
        super(line, column);
    }
}
