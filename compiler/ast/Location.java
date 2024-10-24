package ast;

public class Location extends Expr{
    public IDNode id;
    public Expr indexExpr;

    // Constructor para Location con solo un IDNode
    public Location(IDNode id) {
        this.id = id;
        this.indexExpr = null;
    }

    // Constructor para Location con un IDNode y una expresión de índice
    public Location(IDNode id, Expr indexExpr) {
        this.id = id;
        this.indexExpr = indexExpr;
    }

    // Constructor adicional para aceptar String y convertirlo a IDNode
    public Location(String name) {
        this.id = new IDNode(name);
        this.indexExpr = null;
    }

    // Constructor adicional para aceptar String y una expresión
    public Location(String name, Expr indexExpr) {
        this.id = new IDNode(name);
        this.indexExpr = indexExpr;
    }
}


