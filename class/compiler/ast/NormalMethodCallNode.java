package compiler.ast;
import java.util.List;

public class NormalMethodCallNode extends MethodCallNode {
    private String methodName;
    private List<ExpressionNode> arguments;

    public NormalMethodCallNode(String methodName, List<ExpressionNode> arguments) {
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

    // Getters
}