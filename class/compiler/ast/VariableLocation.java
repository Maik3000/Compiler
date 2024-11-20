package compiler.ast;

public class VariableLocation extends LocationNode {
    public VariableLocation(String name) {
        super(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}