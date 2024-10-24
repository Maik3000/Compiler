package ast;

import java.util.ArrayList;
import java.util.List;

public class MethodCall extends Expr{
    public MethodName methodName;
    public List<Expr> args;
    public List<CalloutArg> calloutArgs;  // List de CalloutArg para los callouts

    // Constructor para llamada sin argumentos
    public MethodCall(MethodName methodName) {
        this.methodName = methodName;
        this.args = null;
        this.calloutArgs = null;
    }

    // Constructor para llamada con un argumento
    public MethodCall(MethodName methodName, Expr arg) {
        this.methodName = methodName;
        this.args = List.of(arg);
        this.calloutArgs = null;
    }

    // Constructor para llamada con dos argumentos
    public MethodCall(MethodName methodName, Expr arg1, Expr arg2) {
        this.methodName = methodName;
        this.args = new ArrayList<>();
        this.args.add(arg1);
        this.args.add(arg2);
        this.calloutArgs = null;
    }

    // **Constructor para llamada con CalloutArgs**
    public MethodCall(MethodName methodName, List<CalloutArg> calloutArgs) {
        this.methodName = methodName;
        this.args = null;  // No se usa para Callout
        this.calloutArgs = calloutArgs;
    }
}
