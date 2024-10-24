package ast;

import java.util.ArrayList;

public class VarDeclList extends ArrayList<VarDecl> {
    public VarDeclList addVarDecl(VarDecl varDecl) {
        this.add(varDecl);
        return this;
    }
}
