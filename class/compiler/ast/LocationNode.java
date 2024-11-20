package compiler.ast;

public abstract class LocationNode extends ExpressionNode {
    protected String name;

    public LocationNode(String name) {
        this.name = name;
    }

    // Getter
}