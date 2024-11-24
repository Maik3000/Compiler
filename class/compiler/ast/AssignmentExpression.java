package compiler.ast;

public class AssignmentExpression extends ExpressionNode {
    private LocationNode location;
    private String operator;
    private ExpressionNode expression;

    public AssignmentExpression(LocationNode location, String operator, ExpressionNode expression, int line, int column) {
        super(line, column);
        this.location = location;
        this.operator = operator;
        this.expression = expression;
    }

    public String getOperator() {
        return operator;
    }

    public LocationNode getLocation() {
        return location;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}