package compiler.ast;
import java.util.List;

public class ProgramNode extends ASTNode{
    private String name;
    private List<ClassMember> members;

    public ProgramNode(String name, List<ClassMember> members, int line, int column) {
        super(line, column);
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public List<ClassMember> getBody() {
        return members;
    }

    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}