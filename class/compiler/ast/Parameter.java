package compiler.ast;

public class Parameter {
    private DataTypeNode type;
    private String name;
    private boolean isArray;

    public Parameter(DataTypeNode type, String name, boolean isArray) {
        this.type = type;
        this.name = name;
        this.isArray = isArray;
    }   

    public DataTypeNode getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isArray() {
        return isArray;
    }

    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters
}