package compiler.ast;

public class BreakStatement extends StatementNode {
    public BreakStatement(int line, int column) {
        super(line, column);
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}