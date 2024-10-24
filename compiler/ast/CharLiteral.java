package ast;

public class CharLiteral extends Literal{
    public char value;

    public CharLiteral(char value) {
        super(value);
        this.value = value;
    }
}
