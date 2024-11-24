package compiler.ast;

public abstract class DataTypeNode extends ASTNode{
    public abstract void accept(ASTVisitor v);

    public DataTypeNode(int line, int column) {
        super(line, column);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        // Puedes agregar más lógica si tus tipos tienen atributos adicionales
        return true;
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
