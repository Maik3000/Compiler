package ast;

public class EqOp extends Expr {
    public String operator;

    public EqOp(String operator) {
        this.operator = operator;
    }
}
