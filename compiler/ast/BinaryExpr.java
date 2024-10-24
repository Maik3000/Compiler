package ast;

public class BinaryExpr extends Expr {
    private Expr left;
    private Expr right;
    private BinOp op;

    public BinaryExpr(Expr left, BinOp op, Expr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}
