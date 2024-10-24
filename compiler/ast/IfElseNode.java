package ast;

public class IfElseNode extends Statement {
    private Expr condition;
    private Block thenBlock;
    private Block elseBlock;

    public IfElseNode(Expr condition, Block thenBlock, Block elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public void execute() {
        // Implementa la lógica de ejecución para el condicional if-else
    }
}
