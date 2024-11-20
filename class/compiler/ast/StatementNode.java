package compiler.ast;

public abstract class StatementNode {
    public abstract void accept(ASTVisitor v);
}