package ast;

public class MethodDecl {
    public Type type;
    public IDNode idNode;
    public ParamListNode params;
    public Block block;

    public MethodDecl(Type type, IDNode idNode, ParamListNode params, Block block) {
        this.type = type;
        this.idNode = idNode;
        this.params = params;
        this.block = block;
    }
}
