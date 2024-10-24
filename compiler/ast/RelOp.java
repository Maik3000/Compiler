package ast;

public class RelOp extends Expr {
    public String operator;

    public RelOp(String operator) {
        this.operator = operator;
    }
}