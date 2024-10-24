package ast;

import java.util.ArrayList;

public class StatementList extends ArrayList<Statement> {

    // Método para agregar un Statement a la lista
    public StatementList addStatement(Statement s) {
        this.add(s); // Utiliza el método add de ArrayList
        return this; // Retorna la lista para que sea encadenable
    }
}
