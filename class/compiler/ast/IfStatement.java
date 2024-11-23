package compiler.ast;

public class IfStatement extends StatementNode {
    private ExpressionNode condition;
    private BlockNode thenBlock;
    private BlockNode elseBlock; // Puede ser null

    public IfStatement(ExpressionNode condition, BlockNode thenBlock, BlockNode elseBlock, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public BlockNode getThenBlock() {
        return thenBlock;
    }

    public BlockNode getElseBlock() {
        return elseBlock;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
