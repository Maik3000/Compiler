package compiler.semantic;
import compiler.ast.*;
import java.util.*;

import ast.ASTVisitor;
import ast.ArrayLocation;
import ast.AssignmentExpression;
import ast.BinaryExpression;
import ast.BooleanTypeNode;
import ast.ClassMember;
import ast.DataTypeNode;
import ast.IntegerTypeNode;
import ast.MethodDeclaration;
import ast.ProgramNode;
import ast.VarDeclaration;
import ast.VariableLocation;


public class SemanticAnalyzer implements ASTVisitor {
    // Tabla de símbolos
    private SymbolTable symbolTable;

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
    }

    // Implementaciones de los métodos visit
    @Override
    public void visit(ProgramNode node) {
        // Iniciar el análisis semántico
        // Visitar cada miembro de la clase
        for (ClassMember member : node.getClassBody()) {
            member.accept(this);
        }
    }

    @Override
    public void visit(MethodDeclaration node) {
        // Declarar el método en la tabla de símbolos
        Symbol methodSymbol = new Symbol(
            node.getName(),
            node.getReturnType(),
            false,
            SymbolKind.METHOD
        );

        if (!symbolTable.declare(methodSymbol)) {
            // Error: Método ya declarado
            reportError("El método '" + node.getName() + "' ya está declarado.", node);
        }

        // Entrar en un nuevo ámbito para los parámetros y variables locales
        symbolTable.enterScope();

        // Declarar parámetros
        for (Parameter param : node.getParameters()) {
            param.accept(this);
        }

        // Visitar el cuerpo del método
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }

        // Salir del ámbito del método
        symbolTable.exitScope();
    }

    @Override
    public void visit(VarDeclaration node) {
        Symbol varSymbol = new Symbol(
            node.getName(),
            node.getType(),
            node.isArray(),
            SymbolKind.VARIABLE
        );

        if (!symbolTable.declare(varSymbol)) {
            // Error: Variable ya declarada en este ámbito
            reportError("La variable '" + node.getName() + "' ya está declarada en este ámbito.", node);
        }

        // Si hay una expresión de inicialización, verificarla
        if (node.getInitExpr() != null) {
            node.getInitExpr().accept(this);

            // Comprobar compatibilidad de tipos
            if (!isTypeCompatible(node.getType(), node.getInitExpr().getType())) {
                reportError("Tipos incompatibles en la inicialización de la variable '" + node.getName() + "'.", node);
            }
        }
    }

    @Override
    public void visit(AssignmentExpression node) {
        // Visitar la ubicación y la expresión
        node.getLocation().accept(this);
        node.getExpression().accept(this);

        // Comprobar que la variable está declarada
        Symbol varSymbol = symbolTable.lookup(node.getLocation().getName());
        if (varSymbol == null) {
            reportError("La variable '" + node.getLocation().getName() + "' no está declarada.", node.getLocation());
        }

        // Comprobar compatibilidad de tipos
        if (!isTypeCompatible(node.getLocation().getType(), node.getExpression().getType())) {
            reportError("Tipos incompatibles en la asignación a '" + node.getLocation().getName() + "'.", node);
        }

        // Establecer el tipo de la expresión
        node.setType(node.getLocation().getType());
    }

    @Override
    public void visit(BinaryExpression node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);

        // Comprobar si los tipos son compatibles con el operador
        String operator = node.getOperator();
        DataTypeNode leftType = node.getLeft().getType();
        DataTypeNode rightType = node.getRight().getType();

        // Aquí puedes implementar reglas específicas para cada operador
        if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("%")) {
            if (leftType instanceof IntegerTypeNode && rightType instanceof IntegerTypeNode) {
                node.setType(new IntegerTypeNode());
            } else {
                reportError("Operador '" + operator + "' no soportado para tipos " + leftType + " y " + rightType, node);
            }
        } else if (operator.equals("&&") || operator.equals("||")) {
            if (leftType instanceof BooleanTypeNode && rightType instanceof BooleanTypeNode) {
                node.setType(new BooleanTypeNode());
            } else {
                reportError("Operador '" + operator + "' no soportado para tipos " + leftType + " y " + rightType, node);
            }
        }
        // Agrega más casos para otros operadores
    }
    @Override
    public void visit(VariableLocation node) {
        Symbol varSymbol = symbolTable.lookup(node.getName());
        if (varSymbol == null) {
            reportError("La variable '" + node.getName() + "' no está declarada.", node);
        } else {
            node.setType(varSymbol.getType());
        }
    }

    @Override
    public void visit(ArrayLocation node) {
        Symbol varSymbol = symbolTable.lookup(node.getName());
        if (varSymbol == null) {
            reportError("El arreglo '" + node.getName() + "' no está declarado.", node);
        } else if (!varSymbol.isArray()) {
            reportError("'" + node.getName() + "' no es un arreglo.", node);
        } else {
            node.setType(varSymbol.getType());
        }

        // Visitar la expresión índice
        node.getIndex().accept(this);

        // Comprobar que el índice es de tipo entero
        if (!(node.getIndex().getType() instanceof IntegerTypeNode)) {
            reportError("El índice del arreglo debe ser de tipo entero.", node.getIndex());
        }
    }
}