package ast;

import java.util.List;

public class CalloutArg {
    public String str;
    public Expr expr;
    public List<CalloutArg> args; // Lista para manejar múltiples CalloutArgs

    // Constructor para un callout con un string
    public CalloutArg(String str) {
        this.str = str;
        this.args = null; // No tiene más argumentos
    }

    // Constructor para un callout con una expresión
    public CalloutArg(Expr expr) {
        this.expr = expr;
        this.args = null; // No tiene más argumentos
    }

    // Constructor para un callout con un string y una lista de CalloutArgs
    public CalloutArg(String str, CalloutArgList argList) {
        this.str = str;
        this.args = argList.getArgs(); // Se inicializa con la lista de argumentos
    }

    // Constructor para un callout con una expresión y una lista de CalloutArgs
    public CalloutArg(Expr expr, CalloutArgList argList) {
        this.expr = expr;
        this.args = argList.getArgs(); // Se inicializa con la lista de argumentos
    }
}
