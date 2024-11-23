package compiler.ast;

public class VariableLocation extends LocationNode {
    public VariableLocation(String name, int line, int column) {
        super(name,line,column);
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}