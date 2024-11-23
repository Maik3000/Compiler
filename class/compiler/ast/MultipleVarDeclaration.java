package compiler.ast;
import java.util.List;

public class MultipleVarDeclaration extends ClassMember {
    private List<VarDeclaration> declarations;

    public MultipleVarDeclaration(List<VarDeclaration> declarations, int line, int column) {
        super(line, column);
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
