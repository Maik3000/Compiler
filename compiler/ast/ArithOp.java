package ast;

public class ArithOp extends Expr {
    public String operator;

    public ArithOp(String operator) {
        this.operator = operator;
    }
}