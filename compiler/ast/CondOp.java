package ast;

public class CondOp extends Expr {
    public String operator;

    public CondOp(String operator) {
        this.operator = operator;
    }
}