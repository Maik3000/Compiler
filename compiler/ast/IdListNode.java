package ast;

import java.util.ArrayList;

public class IdListNode extends ArrayList<IDNode> {

     // Constructor que acepta un IDNode y lo añade a la lista
     public IdListNode(IDNode id) {
        this.add(id); // Agrega el IDNode inicial a la lista
    }
    
    public IdListNode addID(IDNode id) {
        this.add(id);
        return this;
    }
    
}
