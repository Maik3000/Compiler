package compiler.ast;

import java.util.List;

public abstract class MethodCallNode extends ExpressionNode {
    private String methodName;
    private List<ExpressionNode> arguments;

    public MethodCallNode(String methodName, List<ExpressionNode> arguments, int line, int column) {
        super(line, column);
        this.methodName = methodName;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}
