package compiler.ast;

public abstract class LiteralNode extends ExpressionNode {
    public LiteralNode(int line, int column) {
        super(line, column);
    }
}
