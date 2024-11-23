package compiler.semantic;

import compiler.semantic.Symbol;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
    private Deque<Map<String, Symbol>> scopes;

    public SymbolTable() {
        scopes = new ArrayDeque<>();
        enterScope(); // Iniciar con el ámbito global
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
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

    public Symbol lookupInCurrentScope(String name) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(name)) {
            return currentScope.get(name);
        }
        return null; // No encontrado en el ámbito actual
    }
}