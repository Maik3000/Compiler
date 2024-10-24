package ast;

public class MethodCallStatementNode extends Statement {
    private MethodCall methodCall;

    public MethodCallStatementNode(MethodCall methodCall) {
        this.methodCall = methodCall;
    }

    @Override
    public void execute() {
        // Implementa la lógica de ejecución para una llamada a método
    }
}
