package compiler.ast;

public class ContinueStatement extends StatementNode {
    public ContinueStatement(int line, int column) {
        super(line, column);
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}