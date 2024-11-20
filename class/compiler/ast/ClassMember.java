package compiler.ast;

public interface ClassMember {
    void accept(ASTVisitor v);
}