package compiler.ast;
import java.util.ArrayList;
import java.util.List;


public class CalloutCallNode extends MethodCallNode {
    private String functionName;
    private List<CalloutArgument> calloutArguments;

    public CalloutCallNode(String functionName, List<CalloutArgument> arguments, int line, int column) {
        super(functionName, new ArrayList<ExpressionNode>(), line, column);
        this.functionName = functionName; 
        this.calloutArguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<CalloutArgument> getCalloutArguments() {
        return calloutArguments;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters
}