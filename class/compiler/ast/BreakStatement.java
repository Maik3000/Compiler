package compiler.ast;

public class BreakStatement extends StatementNode {
    public BreakStatement() {}

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}