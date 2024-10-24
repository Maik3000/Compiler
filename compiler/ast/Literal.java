package ast;

public class Literal extends Expr {
    public Object value;

    public Literal(Object value) {
        this.value = value;
    }
}