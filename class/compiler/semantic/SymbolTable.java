package compiler.semantic;

import compiler.semantic.Symbol;
import compiler.ast.DataTypeNode;
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

    public boolean addVariable(String varName, DataTypeNode type) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(varName)) {
            return false; // La variable ya está declarada en este ámbito
        } else {
            // Crear un nuevo símbolo para la variable
            Symbol variableSymbol = new Symbol(varName, type, false, Symbol.SymbolKind.VARIABLE);
            currentScope.put(varName, variableSymbol);
            return true;
        }
    }

    public boolean addMethod(String methodName, Symbol methodSymbol) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(methodName)) {
            return false; // El método ya está declarado en este ámbito
        } else {
            currentScope.put(methodName, methodSymbol);
            return true;
        }
    }
    public Symbol lookupMethod(String methodName) {
        Symbol symbol = lookup(methodName);
        if (symbol != null && symbol.getKind() == Symbol.SymbolKind.METHOD) {
            return symbol;
        }
        return null; // Method not found or not a method symbol
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
    public DataTypeNode lookupVariable(String varName) {
        Symbol symbol = lookup(varName);
        if (symbol != null && symbol.getKind() == Symbol.SymbolKind.VARIABLE) {
            return symbol.getType();
        }
        return null; // Variable not found or not a variable
    }
    public Symbol lookupInCurrentScope(String name) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(name)) {
            return currentScope.get(name);
        }
        return null; // No encontrado en el ámbito actual
    }
}