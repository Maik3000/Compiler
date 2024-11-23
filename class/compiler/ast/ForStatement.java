package compiler.ast;

public class ForStatement extends StatementNode {
    private String varName;
    private StatementNode initExpr; // Puede ser null
    private ExpressionNode condition; // Puede ser null
    private StatementNode updateStmt; // Puede ser null
    private BlockNode body;

    public ForStatement(String varName, StatementNode initExpr, ExpressionNode condition, StatementNode updateStmt, BlockNode body, int line, int column) {
        super(line, column);
        this.varName = varName;
        this.initExpr = initExpr;
        this.condition = condition;
        this.updateStmt = updateStmt;
        this.body = body;
    }

    public String getVarName() {
        return varName;
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
