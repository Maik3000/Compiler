package compiler.ast;

public abstract class LocationNode extends ExpressionNode {
    protected String name;

    public LocationNode(String name, int line, int column) {
        super(line, column);
        this.name = name;
    }


    public String getName() {
        return name;
    }
    
    @Override
    public abstract void accept(ASTVisitor v);
}