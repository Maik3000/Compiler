package compiler.irt;

import java.util.List;

public class IRTCALL extends IRTExpression {
    private String methodName;
    private List<IRTExpression> arguments;

    public IRTCALL(String methodName, List<IRTExpression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<IRTExpression> getArguments() {
        return arguments;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
