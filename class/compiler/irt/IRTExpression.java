package compiler.irt;

public abstract class IRTExpression {
    public abstract void accept(IRTVisitor visitor);
}