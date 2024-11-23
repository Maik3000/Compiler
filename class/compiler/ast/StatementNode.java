package compiler.ast;


public abstract class StatementNode extends ASTNode{
    public abstract void accept(ASTVisitor v);
    public StatementNode(int line, int column) {
        super(line, column);
    }
}