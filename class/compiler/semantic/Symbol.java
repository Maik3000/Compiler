package compiler.semantic;

import java.util.List;

import compiler.ast.*;

public class Symbol {
    private String name;
    private DataTypeNode type;
    private boolean isArray;
    private SymbolKind kind; // VARIABLE, METHOD, etc.
    private List<Parameter> parameters; // Agrega este campo

    // Constructor para variables
    public Symbol(String name, DataTypeNode type, boolean isArray, SymbolKind kind) {
        this(name, type, isArray, kind, null);
    }

    // Constructor para métodos
    public Symbol(String name, DataTypeNode type, boolean isArray, SymbolKind kind, List<Parameter> parameters) {
        this.name = name;
        this.type = type;
        this.isArray = isArray;
        this.kind = kind;
        this.parameters = parameters;
    }

    // Agrega un getter para los parámetros
    public List<Parameter> getParameters() {
        return parameters;
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