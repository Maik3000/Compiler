package ast;

import java.util.ArrayList;

public class MethodDeclList extends ArrayList<MethodDecl> {
    public MethodDeclList addMethod(MethodDecl method) {
        this.add(method);
        return this;  // Llama al método add de ArrayList
    }
}
