package compiler.semantic;

import compiler.ast.*;

public class SymbolTable {
    private Deque<Map<String, Symbol>> scopes;

    public SymbolTable() {
        scopes = new ArrayDeque<>();
        enterScope(); // Inicia en el ámbito global
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public boolean declare(Symbol symbol) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(symbol.getName())) {
            return false; // La declaración ya existe en el ámbito actual
        } else {
            currentScope.put(symbol.getName(), symbol);
            return true;
        }
    }

    public Symbol lookup(String name) {
        for (Map<String, Symbol> scope : scopes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null; // No encontrado
    }
}