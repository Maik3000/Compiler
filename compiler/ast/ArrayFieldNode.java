package ast;

public class ArrayFieldNode extends Field{
    private IntLiteral size;

    public ArrayFieldNode(IDNode idNode, IntLiteral size, Type type) {
        super(idNode, type);
        this.size = size;
    }

    public IntLiteral getSize() {
        return size;
    }

}
