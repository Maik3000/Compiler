package compiler.semantic;

import compiler.ast.DataTypeNode;
import compiler.ast.*;

public class Symbol {
    private String name;
    private DataTypeNode type;
    private boolean isArray;
    private SymbolKind kind; // VARIABLE, METHOD, etc.

    public Symbol(String name, DataTypeNode type, boolean isArray, SymbolKind kind) {
        this.name = name;
        this.type = type;
        this.isArray = isArray;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public DataTypeNode getType() {
        return type;
    }

    public boolean isArray() {
        return isArray;
    }

    public SymbolKind getKind() {
        return kind;
    }


    public enum SymbolKind {
        VARIABLE,
        METHOD
        // Puedes agregar otros tipos si es necesario
    }
}