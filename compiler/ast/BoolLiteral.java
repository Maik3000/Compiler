package ast;

public class BoolLiteral extends Literal{
    public boolean value;

    public BoolLiteral(boolean value) {
        super(value);
        this.value = value;
    }
}
