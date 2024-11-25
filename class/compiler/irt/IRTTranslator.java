package compiler.irt;

import java.util.ArrayList;
import java.util.List;

import compiler.ast.*;

public class IRTTranslator implements ASTVisitor {
    // Aquí almacenarás el resultado de la traducción
    private IRTStatement irtRoot;

    // Variable para almacenar expresiones intermedias
    private IRTExpression currentExpression;

    public IRTStatement getIRTRoot() {
        return irtRoot;
    }

    @Override
    public void visit(ProgramNode node) {
        // Traducir el nodo del programa
        IRTStatementList programStatements = new IRTStatementList();

        for (ClassMember member : node.getBody()) {
            if (member instanceof MethodDeclaration) {
                MethodDeclaration method = (MethodDeclaration) member;
                method.accept(this);
                programStatements.addStatement(irtRoot);
            }
            // Si tienes otros tipos de miembros de clase, puedes manejarlos aquí
        }

        irtRoot = programStatements;
    }


    @Override
    public void visit(ArrayLocation node) {
        
    }

    public void visit(AssignmentExpression node) {
        // Traducir la expresión de la derecha
        node.getExpression().accept(this);
        IRTExpression src = currentExpression;

        // Traducir la ubicación de la izquierda
        node.getLocation().accept(this);
        IRTExpression dest = currentExpression;

        // Crear la instrucción MOVE
        IRTMOVE move = new IRTMOVE(dest, src);
        irtRoot = move;

    }

    @Override
    public void visit(AssignmentStatement node) {
        // Traducir asignación
        // Suponiendo que node.getVariable() devuelve un VariableNode
        node.getLocation().accept(this);
        IRTExpression dest = currentExpression;

        node.getExpression().accept(this);
        IRTExpression src = currentExpression;

        IRTMOVE move = new IRTMOVE(dest, src);
        irtRoot = move;
    }

    @Override
    public void visit(BinaryExpression node) {
        node.getLeft().accept(this);
        IRTExpression left = currentExpression;
        node.getRight().accept(this);
        IRTExpression right = currentExpression;

        // Mapear el operador de AST a IRTBINOP.Op
        IRTBINOP.Op operator;
        switch (node.getOperator()) {
            case "+":
                operator = IRTBINOP.Op.ADD;
                break;
            case "-":
                operator = IRTBINOP.Op.SUB;
                break;
            case "*":
                operator = IRTBINOP.Op.MUL;
                break;
            case "/":
                operator = IRTBINOP.Op.DIV;
                break;
            case "&&":
                operator = IRTBINOP.Op.AND;
                break;
            case "||":
                operator = IRTBINOP.Op.OR;
                break;
            case "==":
                operator = IRTBINOP.Op.EQ;
                break;
            case "!=":
                operator = IRTBINOP.Op.NE;
                break;
            case "<":
                operator = IRTBINOP.Op.LT;
                break;
            case "<=":
                operator = IRTBINOP.Op.LE;
                break;
            case ">":
                operator = IRTBINOP.Op.GT;
                break;
            case ">=":
                operator = IRTBINOP.Op.GE;
                break;
            default:
                throw new UnsupportedOperationException("Operador no soportado: " + node.getOperator());
        }

        currentExpression = new IRTBINOP(operator, left, right);
    }

    @Override
    public void visit(BlockNode node) {
        IRTStatementList stmtList = new IRTStatementList();
        for (StatementNode stmt : node.getStatements()) {
            stmt.accept(this);
            stmtList.addStatement(irtRoot);
        }
        irtRoot = stmtList;
    }

    @Override
    public void visit(BreakStatement node) {}

    @Override
    public void visit(BooleanTypeNode node) {}

    @Override
    public void visit(BooleanLiteral node) {}

    @Override
    public void visit(ContinueStatement node) {}

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
    public void visit(CalloutArgumentExpression node) {}

    @Override
    public void visit(CalloutCallNode node) {}

    @Override
    public void visit(CharacterTypeNode node) {}

    @Override
    public void visit(CharacterLiteral node) {}

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
        // Las expresiones en una sentencia pueden producir efectos secundarios, pero en IRT necesitamos una instrucción
        // Si la expresión es una llamada a método o similar, podrías necesitar manejarla aquí
        // Si no produce efectos secundarios, podrías no necesitar hacer nada
        irtRoot = new IREXP(currentExpression);

    }

    @Override
    public void visit(ExpressionNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }

    @Override
    public void visit(ForStatement node) {}

    @Override
    public void visit(IntegerLiteral node) {
        currentExpression = new IRTConst(node.getValue());
    }

    @Override
    public void visit(IntegerTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
    }    

    @Override
    public void visit(IfStatement node) {
        // Traducir declaración if
        // Generar etiquetas para las ramas
        String trueLabel = LabelGenerator.newLabel();
        String falseLabel = LabelGenerator.newLabel();
        String endLabel = LabelGenerator.newLabel();

        // Traducir la condición
        node.getCondition().accept(this);
        IRTExpression condition = currentExpression;

        // Crear la instrucción de salto condicional
        IRTCJUMP cjump = new IRTCJUMP(condition, trueLabel, falseLabel);

        // Traducir el bloque verdadero
        node.getThenBlock().accept(this);
        IRTStatement thenStmt = irtRoot;

        // Traducir el bloque falso si existe
        IRTStatement elseStmt = null;
        if (node.getElseBlock() != null) {
            node.getElseBlock().accept(this);
            elseStmt = irtRoot;
        }

        // Combinar las instrucciones en una secuencia
        IRTStatementList stmtList = new IRTStatementList();
        stmtList.addStatement(cjump);
        stmtList.addStatement(new IRTLabel(trueLabel));
        stmtList.addStatement(thenStmt);
        stmtList.addStatement(new IRTJUMP(endLabel));
        stmtList.addStatement(new IRTLabel(falseLabel));
        if (elseStmt != null) {
            stmtList.addStatement(elseStmt);
        }
        stmtList.addStatement(new IRTLabel(endLabel));

        irtRoot = stmtList;
    }

    @Override
    public void visit(LiteralNode node) {}

    @Override
    public void visit(LocationNode node) {}

    @Override
    public void visit(MethodCallNode node) {
        // Traducir llamada a método
        // Traducir los argumentos
        List<IRTExpression> args = new ArrayList<>();
        for (ExpressionNode arg : node.getArguments()) {
            arg.accept(this);
            args.add(currentExpression);
        }

        // Crear instrucción de llamada
        IRTCALL call = new IRTCALL(node.getMethodName(), args);
        currentExpression = call;
    }

    @Override
    public void visit(MethodCallStatement node) {
    }

    @Override
    public void visit(MethodDeclaration node) {
        // Traducir el cuerpo del método
        node.getBody().accept(this);
        IRTStatement body = irtRoot;

        // Crear el nodo IRTMethod
        IRTMethod method = new IRTMethod(node.getName(), body);
        irtRoot = method;
    }


    @Override
    public void visit(MultipleVarDeclaration node) {}

    @Override
    public void visit(NewArrayExpression node) {}

    @Override
    public void visit(NormalMethodCallNode node) {
        // Verificar que el método ha sido declarado
    }

    @Override
    public void visit(Parameter node) {}

    @Override
    public void visit(ReturnStatement node) {
        // Traducir declaración return
        node.getExpression().accept(this);
        IRTExpression returnExpr = currentExpression;

        // Crear instrucción de retorno
        IRTRETURN irtReturn = new IRTRETURN(returnExpr);
        irtRoot = irtReturn;
    }

    @Override
    public void visit(VariableNode node) {
        // Implementación de la traducción para VariableNode
        String varName = node.getName();
        // Crear una expresión IRT para la variable
        currentExpression = new IRTTEMP(varName);
    }

    @Override
    public void visit(VarDeclaration node) {
        // Traducir declaración de variable
        // Puede que no necesites hacer nada si las variables se manejan en el contexto
    }

    @Override
    public void visit(VarDeclarationStatement node) {
    }

    @Override
    public void visit(VariableLocation node) {
        String varName = node.getName();
        currentExpression = new IRTTEMP(varName);

    }

     @Override
    public void visit(VoidTypeNode node) {
        // If no specific action is needed, you can leave this method empty
        // or implement any necessary logic for CalloutArgumentString nodes
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
    public void visit(UnaryExpression node) {}

    @Override
    public void visit(WhileStatement node) {
        // Traducir declaración while
        String startLabel = LabelGenerator.newLabel();
        String bodyLabel = LabelGenerator.newLabel();
        String endLabel = LabelGenerator.newLabel();

        // Inicio del bucle
        IRTLabel start = new IRTLabel(startLabel);

        // Traducir la condición
        node.getCondition().accept(this);
        IRTExpression condition = currentExpression;

        // Salto condicional
        IRTCJUMP cjump = new IRTCJUMP(condition, bodyLabel, endLabel);

        // Traducir el cuerpo del bucle
        node.getBody().accept(this);
        IRTStatement bodyStmt = irtRoot;

        // Crear la secuencia de instrucciones
        IRTStatementList stmtList = new IRTStatementList();
        stmtList.addStatement(start);
        stmtList.addStatement(cjump);
        stmtList.addStatement(new IRTLabel(bodyLabel));
        stmtList.addStatement(bodyStmt);
        stmtList.addStatement(new IRTJUMP(startLabel));
        stmtList.addStatement(new IRTLabel(endLabel));

        irtRoot = stmtList;
    }

    @Override
    public void visit(ASTNode node) {
        throw new UnsupportedOperationException("visit(ASTNode) no está implementado en IRTTranslator");
    }

    // Implementar otros métodos de visita según tus nodos AST
    // Por ejemplo, visitas para declaraciones de variables, llamadas a métodos, etc.

    

    

    // Asegúrate de implementar todos los métodos de ASTVisitor
}
