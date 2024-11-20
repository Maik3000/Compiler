package compiler.ast;

public abstract class DataTypeNode {
    public abstract void accept(ASTVisitor v);
}
