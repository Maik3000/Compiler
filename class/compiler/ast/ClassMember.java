package compiler.ast;

public abstract class ClassMember extends ASTNode {
    public ClassMember(int line, int column) {
        super(line, column);
    }
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}