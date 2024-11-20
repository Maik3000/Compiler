package compiler.ast;

public class VarDeclaration implements ClassMember {
    private DataTypeNode type;
    private String name;
    private boolean isArray;
    private ExpressionNode initExpr; // Puede ser null

    public VarDeclaration(DataTypeNode type, String name, boolean isArray, ExpressionNode initExpr) {
        this.type = type;
        this.name = name;
        this.isArray = isArray;
        this.initExpr = initExpr;
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

    public ExpressionNode getInitExpr() {
        return initExpr;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
