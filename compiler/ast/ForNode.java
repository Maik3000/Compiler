package ast;

public class ForNode extends Statement {
    private IDNode id;
    private Expr from;
    private Expr to;
    private Block block;

    public ForNode(IDNode id, Expr from, Expr to, Block block) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.block = block;
    }

    @Override
    public void execute() {
        // Implementa la lógica de ejecución para un bucle for
    }
}
