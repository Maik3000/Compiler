package compiler.ast;
import java.util.List;


public class CalloutCallNode extends MethodCallNode {
    private String functionName;
    private List<CalloutArgument> arguments;

    public CalloutCallNode(String functionName, List<CalloutArgument> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<CalloutArgument> getArguments() {
        return arguments;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters
}