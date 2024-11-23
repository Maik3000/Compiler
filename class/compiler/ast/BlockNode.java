package compiler.ast;
import java.util.List;

public class BlockNode extends StatementNode{
    private List<VarDeclaration> varDeclarations;
    private List<StatementNode> statements;

    public BlockNode(List<VarDeclaration> varDeclarations, List<StatementNode> statements , int line, int column) {
        super(line, column);
        this.varDeclarations = varDeclarations;
        this.statements = statements;
    }

    public List<VarDeclaration> getVarDeclarations() {
        return varDeclarations;
    }

    public List<StatementNode> getStatements() {
        return statements;
    }
    
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
