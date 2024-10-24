package ast;

public class VarDecl {
    public Type type;
    public IdListNode ids;

    public VarDecl(Type type, IdListNode ids) {
        this.type = type;
        this.ids = ids;
    }
}
