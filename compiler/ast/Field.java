package ast;

// Asegúrate de que 'Type' esté importado o definido en tu proyecto
public class Field {
    private IDNode idNode; // Identificador del campo
    private Type type; // Tipo del campo

    // Constructor que acepta el id y el type del campo
    public Field(IDNode idNode, Type type) {
        this.idNode = idNode;
        this.type = type;
    }

    // Getters y Setters
    public IDNode getId() {
        return idNode;
    }

    public void setId(IDNode id) {
        this.idNode = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    

    // Otros métodos adicionales según sea necesario
}
