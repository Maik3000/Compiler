package ast;

public class IntLiteral extends Literal{
    public int value;

    public IntLiteral(int value) {
        super(value);
        this.value = value;
    }
}
