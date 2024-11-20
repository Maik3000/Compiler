package compiler.ast;

public class CalloutArgumentString extends CalloutArgument {
    private String value;

    public CalloutArgumentString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getter
}