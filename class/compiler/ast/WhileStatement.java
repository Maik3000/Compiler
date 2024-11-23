package compiler.ast;

public class WhileStatement extends StatementNode {
    private ExpressionNode condition;
    private BlockNode body;

    public WhileStatement(ExpressionNode condition, BlockNode body, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.body = body;
    }

    public ExpressionNode getCondition() {
        return condition;
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
