package compiler.ast;

public class VoidTypeNode extends DataTypeNode {

    public VoidTypeNode(int line, int column){
        super(line, column);
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
