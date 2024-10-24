package ast;

public class FieldDecl {
    private Type type;  // Variable para almacenar el tipo
    private FieldList fieldList; // Variable para almacenar la lista de campos

    // Constructor que acepta Type y FieldList
    public FieldDecl(Type type, FieldList fieldList) {
        this.type = type;
        this.fieldList = fieldList;
    }

    // Getters y Setters si son necesarios
    public Type getType() {
        return type;
    }

    public FieldList getFieldList() {
        return fieldList;
    }

    // Otros métodos adicionales según sea necesario
}
