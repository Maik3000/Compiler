package compiler.ast;
import java.util.List;

public class NormalMethodCallNode extends MethodCallNode {
    public NormalMethodCallNode(String methodName, List<ExpressionNode> arguments, int line, int column) {
        super(methodName, arguments, line, column);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    // Getters
}