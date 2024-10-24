package ast;

public class StringLiteral extends Literal{
    public String value;

    public StringLiteral(String value) {
        super(value);
        this.value = value;
    }
}
