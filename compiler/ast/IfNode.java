
package ast;

public class IfNode extends Statement {
    private Expr condition;
    private Block thenBlock;

    public IfNode(Expr condition, Block thenBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
    }

    @Override
    public void execute() {
        // Implementa la lógica de ejecución para el condicional if
    }
}



