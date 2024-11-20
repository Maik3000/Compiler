package compiler.ast;
import java.util.List;

public class MultipleVarDeclaration implements ClassMember {
    private List<VarDeclaration> declarations;

    public MultipleVarDeclaration(List<VarDeclaration> declarations) {
        this.declarations = declarations;
    }

    public List<VarDeclaration> getDeclarations() {
        return declarations;
    }
    
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
