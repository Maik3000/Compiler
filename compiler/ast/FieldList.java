package ast;

import java.util.ArrayList;

public class FieldList extends ArrayList<Field> { // Asegúrate de que 'Field' es el tipo correcto
    // Constructor sin argumentos
    public FieldList() {
        super();
    }

    // Constructor que acepta un Field
    public FieldList(Field f) {
        super();
        this.add(f); // Agrega el campo inicial
    }
    // Método para agregar un campo a la lista
    public FieldList addField(Field f) {
        this.add(f);
        return this; // Llama al método add de ArrayList para agregar el campo
    }
    
    // Otras funcionalidades de FieldList...
}
