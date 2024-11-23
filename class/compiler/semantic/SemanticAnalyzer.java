package compiler.semantic;
import compiler.ast.*;
import compiler.semantic.Symbol.SymbolKind;

import java.util.*;

public class SemanticAnalyzer implements ASTVisitor {
    // Tabla de símbolos
    private SymbolTable symbolTable;
    private List<String> errorMessages;

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
        this.errorMessages = new ArrayList<>();
    }
    
    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }
    
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    private boolean isTypeCompatible(DataTypeNode type1, DataTypeNode type2) {
        // Implementa la lógica para determinar si los tipos son compatibles
        // Por ejemplo, podrías verificar si son del mismo tipo o si hay conversiones permitidas
        return type1.equals(type2);
    }
    
    
    private void reportError(String message, ASTNode node) {
        String error = "Error semántico en línea " + node.getLine() + ", columna " + node.getColumn() + ": " + message;
        errorMessages.add(error);
        System.err.println(error);
    }
    

    // Implementaciones de los métodos visit
    @Override
    public void visit(ProgramNode node) {
        for (ClassMember member : node.getBody()) {
            member.accept(this);
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
    public void visit(AssignmentStatement node) {
        node.getExpression().accept(this);
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
                node.setType(new IntegerTypeNode(node.getLine(), node.getColumn()));
            } else {
                reportError("Operador '" + operator + "' no soportado para tipos " + leftType + " y " + rightType, node);
            }
        } else if (operator.equals("&&") || operator.equals("||")) {
            if (leftType instanceof BooleanTypeNode && rightType instanceof BooleanTypeNode) {
                node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
            } else {
                reportError("Operador '" + operator + "' no soportado para tipos " + leftType + " y " + rightType, node);
            }
        }
        // Agrega más casos para otros operadores
    }

    @Override
    public void visit(BlockNode node) {
        // Entrar en un nuevo ámbito para las variables locales
        symbolTable.enterScope();

        // Visitar las declaraciones de variables
        for (VarDeclaration varDecl : node.getVarDeclarations()) {
            varDecl.accept(this);
        }

        // Visitar las sentencias
        for (StatementNode stmt : node.getStatements()) {
            stmt.accept(this);
        }

        // Salir del ámbito
        symbolTable.exitScope();
    }   

    @Override
    public void visit(BreakStatement node) {
        // Opcional: Verificar que esté dentro de un bucle
    }

    @Override
    public void visit(BooleanTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(BooleanLiteral node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(ContinueStatement node) {
        // Opcional: Verificar que esté dentro de un bucle
    }

    @Override
    public void visit(CalloutArgument node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }
    @Override
    public void visit(CalloutArgumentString node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(CalloutArgumentExpression node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(CalloutCallNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(CharacterTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes

    }

    @Override
    public void visit(CharacterLiteral node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(ClassMember node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(DataTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }
    @Override
    public void visit(ExpressionStatement node) {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(ExpressionNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(ForStatement node) {
        // Entrar en un nuevo ámbito para las variables del 'for'
        symbolTable.enterScope();

        // Declarar la variable del 'for'
        Symbol symbol = new Symbol(
            node.getVarName(),
            new IntegerTypeNode(node.getLine(), node.getColumn()),
            false,
            SymbolKind.VARIABLE);
    }
    @Override
    public void visit(IfStatement node) {
        node.getCondition().accept(this);

        if (!(node.getCondition().getType() instanceof BooleanTypeNode)) {
            reportError("La condición del 'if' debe ser de tipo boolean.", node.getCondition());
        }

        node.getThenBlock().accept(this);

        if (node.getElseBlock() != null) {
            node.getElseBlock().accept(this);
        }
    }

    @Override
    public void visit(IntegerTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(IntegerLiteral node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(LiteralNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(LocationNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(MultipleVarDeclaration node) {
        for (VarDeclaration varDecl : node.getDeclarations()) {
            varDecl.accept(this);
        }
    }

    @Override
    public void visit(MethodCallStatement node) {
        node.getMethodCall().accept(this);
    }

    @Override
    public void visit(MethodCallNode node) {
        // Verificar si el método existe
        Symbol methodSymbol = symbolTable.lookup(node.getMethodName());

        if (methodSymbol == null || methodSymbol.getKind() != SymbolKind.METHOD) {
            reportError("El método '" + node.getMethodName() + "' no está declarado.", node);
            node.setType(new ErrorTypeNode(node.getLine(), node.getColumn()));
        } else {
            // Verificar los parámetros
            // Aquí podrías comparar el tipo y número de parámetros esperados con los proporcionados
            // Por simplicidad, asumiremos que los métodos no tienen parámetros
            node.setType(methodSymbol.getType());
        }

        // Visitar los argumentos
        for (ExpressionNode arg : node.getArguments()) {
            arg.accept(this);
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
    public void visit(NewArrayExpression node) {
        node.getSize().accept(this);

        if (!(node.getSize().getType() instanceof IntegerTypeNode)) {
            reportError("El tamaño de un arreglo debe ser un valor entero.", node.getSize());
        }

        node.setType(node.getType());
    }

    @Override
    public void visit(NormalMethodCallNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(Parameter node) {
        Symbol symbol = new Symbol(
            node.getName(),
            node.getType(),
            node.isArray(),
            SymbolKind.VARIABLE
        );

        if (!symbolTable.declare(symbol)) {
            reportError("El parámetro '" + node.getName() + "' ya está declarado en este ámbito.", node);
        }
    }

    @Override
    public void visit(ReturnStatement node) {
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
            // Aquí podrías verificar que el tipo de retorno coincide con el del método
            // Necesitas mantener información del tipo de retorno del método actual
        }
    }

    @Override
    public void visit(StatementNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(StringLiteral node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }
    @Override
    public void visit(UnaryExpression node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }
    @Override
    public void visit(VariableNode node) {
        // Implementación del visitante para VariableNode
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
    public void visit(VarDeclarationStatement node) {
        node.getVarDeclaration().accept(this);
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
    public void visit(VoidTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(WhileStatement node) {
        node.getCondition().accept(this);

        if (!(node.getCondition().getType() instanceof BooleanTypeNode)) {
            reportError("La condición del 'while' debe ser de tipo boolean.", node.getCondition());
        }

        node.getBody().accept(this);
    }

    @Override
    public void visit(ASTNode node) {
        // Implementación vacía o lógica necesaria
    }
   
}