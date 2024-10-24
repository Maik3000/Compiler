package ast;

import java.util.ArrayList;
import java.util.List;

public class CalloutArgList {
    private ArrayList<CalloutArg> args;

    // Constructor por defecto
    public CalloutArgList() {
        this.args = new ArrayList<>();
    }

    // Constructor que acepta un argumento inicial
    public CalloutArgList(CalloutArg arg) {
        this();
        this.args.add(arg);
    }

    // Método para agregar un argumento
    public CalloutArgList add(CalloutArg arg) {
        this.args.add(arg);
        return this;
    }

    // Método para obtener los argumentos
    public List<CalloutArg> getArgs() {
        return this.args;
    }
    
}
