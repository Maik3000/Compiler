package compiler.ast;


public class BooleanTypeNode extends DataTypeNode {
    public BooleanTypeNode(int line, int column) {
        super(line, column);
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
    @Override
    public String toString() {
        return "boolean";
    }
}