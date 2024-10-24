package ast;

public class ReturnNode extends Statement {
    private Expr expression;

    public ReturnNode(Expr expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        // Implementa la lógica de retorno de una expresión
    }
}
