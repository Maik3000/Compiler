package compiler.ast;
import java.util.List;

public class MethodDeclaration implements ClassMember {
    private DataTypeNode returnType;
    private String name;
    private List<Parameter> parameters;
    private BlockNode body;

    public MethodDeclaration(DataTypeNode returnType, String name, List<Parameter> parameters, BlockNode body) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public DataTypeNode getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public BlockNode getBody() {
        return body;
    }


    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    // Getters y setters
}
