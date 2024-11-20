package compiler.semantic;

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

    // Getters y setters
    // ...
}