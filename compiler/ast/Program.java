package ast;

public class Program {
    private IDNode id;
    private FieldDeclList fieldDecls;
    private MethodDeclList methodDecls;

    public Program(IDNode id, FieldDeclList fieldDecls, MethodDeclList methodDecls) {
        this.id = id;
        this.fieldDecls = fieldDecls;
        this.methodDecls = methodDecls;
    }
    
    // Métodos getters y setters
}
