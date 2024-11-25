package compiler.irt;

public abstract class IRTStatement {
    public abstract void accept(IRTVisitor visitor);
}