package compiler.ast;

public class ForStatement extends StatementNode {
    private StatementNode initExpr; // Puede ser null
    private ExpressionNode condition; // Puede ser null
    private StatementNode updateStmt; // Puede ser null
    private BlockNode body;

    public ForStatement(StatementNode initExpr, ExpressionNode condition, StatementNode updateStmt, BlockNode body) {
        this.initExpr = initExpr;
        this.condition = condition;
        this.updateStmt = updateStmt;
        this.body = body;
    }

    public StatementNode getInitExpr() {
        return initExpr;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public StatementNode getUpdateStmt() {
        return updateStmt;
    }

    public BlockNode getBody() {
        return body;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
