package ast;

public class UnaryExpr extends Expr {
    private String operator;
    private Expr expr;

    public UnaryExpr(String operator, Expr expr) {
        this.operator = operator;
        this.expr = expr;
    }
}

