package compiler.ast;

public class ContinueStatement extends StatementNode {
    public ContinueStatement() {}

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}