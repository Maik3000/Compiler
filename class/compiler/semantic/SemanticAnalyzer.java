package compiler.semantic;
import compiler.ast.*;
import compiler.semantic.Symbol.SymbolKind;

import java.util.*;

public class SemanticAnalyzer implements ASTVisitor {
    // Tabla de símbolos
    private SymbolTable symbolTable;
    private List<String> errorMessages;
    private DataTypeNode currentMethodReturnType;
    private boolean insideLoop;

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
        this.errorMessages = new ArrayList<>();
        this.currentMethodReturnType = null;
        this.insideLoop = false;
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
        // Visitar la ubicación y la expresión
        node.getLocation().accept(this);
        node.getExpression().accept(this);

        // Obtener tipos
        DataTypeNode locationType = node.getLocation().getType();
        DataTypeNode exprType = node.getExpression().getType();

        // Verificar compatibilidad de tipos
        if (!isTypeCompatible(locationType, exprType)) {
            reportError("Asignación incompatible. Se esperaba tipo '" + locationType + "', pero se encontró '" + exprType + "'.", node);
        }
    }


    @Override
    public void visit(BinaryExpression node) {
        // Visitar las expresiones izquierda y derecha
        node.getLeft().accept(this);
        node.getRight().accept(this);

        DataTypeNode leftType = node.getLeft().getType();
        DataTypeNode rightType = node.getRight().getType();

        // Verificar compatibilidad de tipos según el operador
        String operator = node.getOperator();
        switch (operator) {
            case "+": case "-": case "*": case "/": case "%":
                // Operaciones aritméticas: ambos operandos deben ser enteros
                if (!(leftType instanceof IntegerTypeNode) || !(rightType instanceof IntegerTypeNode)) {
                    reportError("Los operadores '" + operator + "' requieren operandos de tipo 'int'.", node);
                }
                node.setType(new IntegerTypeNode(node.getLine(), node.getColumn()));
                break;
            case "&&": case "||":
                // Operaciones lógicas: ambos operandos deben ser booleanos
                if (!(leftType instanceof BooleanTypeNode) || !(rightType instanceof BooleanTypeNode)) {
                    reportError("Los operadores '" + operator + "' requieren operandos de tipo 'boolean'.", node);
                }
                node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
                break;
            case "==": case "!=":
                // Operadores de igualdad: los tipos deben ser compatibles
                if (!isTypeCompatible(leftType, rightType)) {
                    reportError("Los operadores '" + operator + "' requieren operandos del mismo tipo.", node);
                }
                node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
                break;
            case "<": case "<=": case ">": case ">=":
                // Operadores relacionales: ambos operandos deben ser enteros
                if (!(leftType instanceof IntegerTypeNode) || !(rightType instanceof IntegerTypeNode)) {
                    reportError("Los operadores '" + operator + "' requieren operandos de tipo 'int'.", node);
                }
                node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
                break;
            default:
                reportError("Operador desconocido '" + operator + "'.", node);
                break;
        }
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
        if (!insideLoop) {
            reportError("La sentencia 'break' debe estar dentro de un bucle.", node);
        }
    }

    @Override
    public void visit(BooleanTypeNode node) {
    }

    @Override
    public void visit(BooleanLiteral node) {
        node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
    }

    @Override
    public void visit(ContinueStatement node) {
        if (!insideLoop) {
            reportError("La sentencia 'continue' debe estar dentro de un bucle.", node);
        }
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
        node.getExpression().accept(this);
    }

    @Override
    public void visit(CalloutCallNode node) {
        // Establecer el tipo de retorno como 'void' o según sea necesario
        node.setType(new VoidTypeNode(node.getLine(), node.getColumn()));

        // Visitar los argumentos
        for (ExpressionNode arg : node.getArguments()) {
        arg.accept(this);
        }
    }

    @Override
    public void visit(CharacterTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes

    }

    @Override
    public void visit(CharacterLiteral node) {
        node.setType(new CharacterTypeNode(node.getLine(), node.getColumn()));
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
        // Entrar en un nuevo ámbito
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
         // Visitar la condición
         node.getCondition().accept(this);

         // Verificar que la condición sea booleana
         DataTypeNode condType = node.getCondition().getType();
         if (!(condType instanceof BooleanTypeNode)) {
             reportError("La condición del 'if' debe ser de tipo boolean.", node.getCondition());
         }
 
         // Visitar el bloque 'then'
         node.getThenBlock().accept(this);
 
         // Visitar el bloque 'else' si existe
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
        node.setType(new IntegerTypeNode(node.getLine(), node.getColumn()));
    }

    @Override
    public void visit(LiteralNode node) {
        // Establecer el tipo según el tipo de literal
        if (node instanceof IntegerLiteral) {
            node.setType(new IntegerTypeNode(node.getLine(), node.getColumn()));
        } else if (node instanceof BooleanLiteral) {
            node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
        } else if (node instanceof CharacterLiteral) {
            node.setType(new CharacterTypeNode(node.getLine(), node.getColumn()));
        } else {
            reportError("Tipo de literal desconocido.", node);
        }
    }

    @Override
    public void visit(LocationNode node) {
        // Verificar si la variable ha sido declarada
        String varName = node.getName();
        DataTypeNode varType = symbolTable.lookupVariable(varName);
        if (varType == null) {
            reportError("La variable '" + varName + "' no ha sido declarada.", node);
        } else {
            node.setType(varType);

            // Si es un arreglo, verificar el índice
            if (node instanceof ArrayLocation) {
                ArrayLocation arrayNode = (ArrayLocation) node;
                arrayNode.getIndex().accept(this);
                DataTypeNode indexType = arrayNode.getIndex().getType();

                if (!(indexType instanceof IntegerTypeNode)) {
                    reportError("El índice del arreglo debe ser de tipo 'int'.", arrayNode.getIndex());
                }

                // El tipo de la ubicación es el tipo base del arreglo
                node.setType(varType); // Asumiendo que varType ya es el tipo base
            }
        }
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
        String methodName = node.getMethodName();
        Symbol methodSymbol = symbolTable.lookupMethod(methodName);

        if (methodSymbol == null) {
            reportError("El método '" + methodName + "' no está declarado.", node);
            
        } else {
            node.setType(methodSymbol.getType());
        }
    }
    
    @Override
    public void visit(MethodDeclaration node) {
       // Registrar el método en la tabla de símbolos
        String methodName = node.getName();
        DataTypeNode returnType = node.getReturnType();
        Symbol methodSymbol = new Symbol(methodName, node.getReturnType(), false, Symbol.SymbolKind.METHOD);

        if (!symbolTable.addMethod(methodName, methodSymbol)) {
            reportError("Method '" + methodName + "' is already declared.", node);
        }

        // Iniciar un nuevo ámbito para los parámetros y variables locales
        symbolTable.enterScope();

        // Registrar los parámetros del método
        for (Parameter parameter : node.getParameters()) {
            String paramName = parameter.getName();
            if (!symbolTable.addVariable(paramName, parameter.getType())) {
                reportError("El parámetro '" + paramName + "' ya ha sido declarado en este ámbito.", parameter);
            }
        }

        // Guardar el tipo de retorno actual
        currentMethodReturnType = node.getReturnType();

        // Visitar el cuerpo del método
        node.getBody().accept(this);

        // Salir del ámbito
        symbolTable.exitScope();

        // Restaurar el tipo de retorno
        currentMethodReturnType = null;
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
        // Verificar que el método ha sido declarado
        
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
        if (currentMethodReturnType instanceof VoidTypeNode) {
            if (node.getExpression() != null) {
                reportError("Un método 'void' no debe retornar un valor.", node);
            }
        } else {
            if (node.getExpression() == null) {
                reportError("El método debe retornar un valor de tipo '" + currentMethodReturnType + "'.", node);
            } else {
                node.getExpression().accept(this);
                DataTypeNode exprType = node.getExpression().getType();
                if (!isTypeCompatible(currentMethodReturnType, exprType)) {
                    reportError("El tipo de retorno '" + exprType + "' no coincide con el tipo esperado '" + currentMethodReturnType + "'.", node);
                }
            }
        }
    }

    @Override
    public void visit(StatementNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(StringLiteral node) {
        
    }
    @Override
    public void visit(UnaryExpression node) {
        // Visitar la expresión
        node.getExpression().accept(this);

        DataTypeNode exprType = node.getExpression().getType();
        String operator = node.getOperator();

        switch (operator) {
            case "-":
                // Operador unario menos: el operando debe ser entero
                if (!(exprType instanceof IntegerTypeNode)) {
                    reportError("El operador '-' requiere un operando de tipo 'int'.", node);
                }
                node.setType(new IntegerTypeNode(node.getLine(), node.getColumn()));
                break;
            case "!":
                // Operador de negación: el operando debe ser booleano
                if (!(exprType instanceof BooleanTypeNode)) {
                    reportError("El operador '!' requiere un operando de tipo 'boolean'.", node);
                }
                node.setType(new BooleanTypeNode(node.getLine(), node.getColumn()));
                break;
            default:
                reportError("Operador unario desconocido '" + operator + "'.", node);
                break;
        }
    }
    @Override
    public void visit(VariableNode node) {
        // Implementación del visitante para VariableNode
    }

    @Override
    public void visit(VarDeclaration node) {
        String varName = node.getName();
        if (!symbolTable.addVariable(varName, node.getType())) {
            reportError("La variable '" + varName + "' ya ha sido declarada en este ámbito.", node);
        }

        // Si hay una expresión de inicialización, verificar tipos
        if (node.getInitExpr() != null) {
            node.getInitExpr().accept(this);

            DataTypeNode exprType = node.getInitExpr().getType();
            if (!isTypeCompatible(node.getType(), exprType)) {
                reportError("Asignación incompatible. Se esperaba tipo '" + node.getType() + "', pero se encontró '" + exprType + "'.", node);
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

        // Verificar que la condición sea booleana
        DataTypeNode condType = node.getCondition().getType();
        if (!(condType instanceof BooleanTypeNode)) {
            reportError("La condición del 'while' debe ser de tipo boolean.", node.getCondition());
        }

        // Indicar que estamos dentro de un bucle
        boolean prevInsideLoop = insideLoop;
        insideLoop = true;

        // Visitar el cuerpo del bucle
        node.getBody().accept(this);

        // Restaurar el estado anterior
        insideLoop = prevInsideLoop;
    }

    @Override
    public void visit(ASTNode node) {
        // Implementación vacía o lógica necesaria
    }
   
}