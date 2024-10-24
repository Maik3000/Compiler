package ast;

public class ParamNode {
    public Type type;
    public IDNode id;

    public ParamNode(Type type, IDNode id) {
        this.type = type;
        this.id = id;
    }
}
