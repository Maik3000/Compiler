package compiler.ast;

public class MethodCallStatement extends StatementNode {
    private MethodCallNode methodCall;

    public MethodCallStatement(MethodCallNode methodCall) {
        this.methodCall = methodCall;
    }

    public MethodCallNode getMethodCall() {
        return methodCall;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}