package ast;

public class BinOp {
    public BinOp op;
    public Expr left, right;

    public BinOp(BinOp op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
}