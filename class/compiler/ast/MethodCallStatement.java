package compiler.ast;

public class MethodCallStatement extends StatementNode {
    private MethodCallNode methodCall;

    public MethodCallStatement(MethodCallNode methodCall, int line, int column) {
        super(line, column);
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