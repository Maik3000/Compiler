package ast;

import java.util.ArrayList;

public class FieldDeclList extends ArrayList<FieldDecl> {
    public FieldDeclList addField(FieldDecl f) {
        // Puedes agregar validaciones aquí si es necesario
        this.add(f);
        return this; 
}
}
