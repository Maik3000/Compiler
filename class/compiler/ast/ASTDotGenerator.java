package compiler.ast;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class ASTDotGenerator implements ASTVisitor {
    private final PrintWriter writer;
    private final AtomicInteger nodeCounter;
    private final Map<Object, Integer> nodeIds;

    public ASTDotGenerator(PrintWriter writer) {
        this.writer = writer;
        this.nodeCounter = new AtomicInteger(0);
        this.nodeIds = new HashMap<>();
    }

    private int getNodeId(Object node) {
        return nodeIds.computeIfAbsent(node, k -> nodeCounter.incrementAndGet());
    }

    private void createNode(Object node, String label) {
        int id = getNodeId(node);
        // Escapar caracteres especiales en el label
        label = label.replace("\"", "\\\"")
                     .replace("\n", "\\n")
                     .replace("\r", "\\r")
                     .replace("\t", "\\t");
        writer.printf("    node%d [label=\"%s\"];\n", id, label);
    }

    private void createEdge(Object from, Object to) {
        writer.printf("    node%d -> node%d;\n", getNodeId(from), getNodeId(to));
    }

    public void beginGraph() {
        writer.println("digraph AST {");
        
    }

    public void endGraph() {
        writer.println("}");
    }

    // Implementación de los métodos visit para cada clase del AST:

    @Override
    public void visit(ProgramNode node) {
        createNode(node, "ProgramNode\n" + node.getName());
        for (ClassMember member : node.getBody()) {
            createEdge(node, member);
            member.accept(this);
        }
    }

    @Override
    public void visit(ClassMember node) {
        // Clase abstracta, no se implementa directamente
    }

    @Override
    public void visit(VarDeclaration varDecl) {
        String arrayStr = varDecl.isArray() ? "[]" : "";
        createNode(varDecl, "VarDeclaration\n" + varDecl.getName() + arrayStr);
        if (varDecl.getType() != null) {
            createEdge(varDecl, varDecl.getType());
            varDecl.getType().accept(this);
        }
        if (varDecl.getInitExpr() != null) {
            createEdge(varDecl, varDecl.getInitExpr());
            varDecl.getInitExpr().accept(this);
        }
    }

    @Override
    public void visit(VarDeclarationStatement node) {
        createNode(node, "VarDeclarationStatement");
        createEdge(node, node.getVarDeclaration());
        node.getVarDeclaration().accept(this);
        if (node.getInitExpression() != null) {
            createEdge(node, node.getInitExpression());
            node.getInitExpression().accept(this);
        }
    }

    @Override
    public void visit(MethodDeclaration node) {
        createNode(node, "MethodDeclaration\n" + node.getName());
        if (node.getReturnType() != null) {
            createEdge(node, node.getReturnType());
            node.getReturnType().accept(this);
        }

        for (Parameter param : node.getParameters()) {
            createEdge(node, param);
            param.accept(this);
        }

        if (node.getBody() != null) {
            createEdge(node, node.getBody());
            node.getBody().accept(this);
        }
    }

    @Override
    public void visit(MultipleVarDeclaration node) {
        createNode(node, "MultipleVarDeclaration");
        for (VarDeclaration decl : node.getDeclarations()) {
            createEdge(node, decl);
            decl.accept(this);
        }
    }
    @Override
    public void visit(StatementNode node) {
        if (node instanceof AssignmentStatement) {
            visit((AssignmentStatement) node);
        } else if (node instanceof MethodCallStatement) {
            visit((MethodCallStatement) node);
        } else if (node instanceof IfStatement) {
            visit((IfStatement) node);
        } else if (node instanceof WhileStatement) {
            visit((WhileStatement) node);
        } else if (node instanceof ForStatement) {
            visit((ForStatement) node);
        } else if (node instanceof ReturnStatement) {
            visit((ReturnStatement) node);
        } else if (node instanceof BreakStatement) {
            visit((BreakStatement) node);
        } else if (node instanceof ContinueStatement) {
            visit((ContinueStatement) node);
        } else if (node instanceof BlockNode) {
            visit((BlockNode) node);
        } else if (node instanceof VarDeclarationStatement) {
            visit((VarDeclarationStatement) node);
        } else {
            // Manejar otros tipos de StatementNode si los hay
            createNode(node, "Unknown StatementNode");
        }
    }

    @Override
    public void visit(BlockNode node) {
        createNode(node, "BlockNode");
        for (VarDeclaration varDecl : node.getVarDeclarations()) {
            createEdge(node, varDecl);
            varDecl.accept(this);
        }
        for (StatementNode stmt : node.getStatements()) {
            createEdge(node, stmt);
            stmt.accept(this);
        }
    }

    
    @Override
    public void visit(AssignmentStatement node) {
        createNode(node, "AssignmentStatement\nOperator: " + node.getOperator());
        createEdge(node, node.getLocation());
        node.getLocation().accept(this);
        createEdge(node, node.getExpression());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(MethodCallStatement node) {
        createNode(node, "MethodCallStatement");
        createEdge(node, node.getMethodCall());
        node.getMethodCall().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        createNode(node, "ExpressionStatement");
        createEdge(node, node.getExpression());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IfStatement node) {
        createNode(node, "IfStatement");
        createEdge(node, node.getCondition());
        node.getCondition().accept(this);
        createEdge(node, node.getThenBlock());
        node.getThenBlock().accept(this);
        if (node.getElseBlock() != null) {
            createEdge(node, node.getElseBlock());
            node.getElseBlock().accept(this);
        }
    }

    @Override
    public void visit(WhileStatement node) {
        createNode(node, "WhileStatement");
        createEdge(node, node.getCondition());
        node.getCondition().accept(this);
        createEdge(node, node.getBody());
        node.getBody().accept(this);
    }

    @Override
    public void visit(ForStatement node) {
        createNode(node, "ForStatement");
        if (node.getInitExpr() != null) {
            createEdge(node, node.getInitExpr());
            node.getInitExpr().accept(this);
        }
        if (node.getCondition() != null) {
            createEdge(node, node.getCondition());
            node.getCondition().accept(this);
        }
        if (node.getUpdateStmt() != null) {
            createEdge(node, node.getUpdateStmt());
            node.getUpdateStmt().accept(this);
        }
        createEdge(node, node.getBody());
        node.getBody().accept(this);
    }

    @Override
    public void visit(ReturnStatement node) {
        createNode(node, "ReturnStatement");
        if (node.getExpression() != null) {
            createEdge(node, node.getExpression());
            node.getExpression().accept(this);
        }
    }

    @Override
    public void visit(BreakStatement node) {
        createNode(node, "BreakStatement");
    }

    @Override
    public void visit(ContinueStatement node) {
        createNode(node, "ContinueStatement");
    }

    @Override
    public void visit(ExpressionNode node) {
        if (node instanceof AssignmentExpression) {
            visit((AssignmentExpression) node);
        } else if (node instanceof BinaryExpression) {
            visit((BinaryExpression) node);
        } else if (node instanceof UnaryExpression) {
            visit((UnaryExpression) node);
        } else if (node instanceof MethodCallNode) {
            visit((MethodCallNode) node);
        } else if (node instanceof LiteralNode) {
            visit((LiteralNode) node);
        } else if (node instanceof VariableLocation) {
            visit((VariableLocation) node);
        } else if (node instanceof ArrayLocation) {
            visit((ArrayLocation) node);
        } else if (node instanceof NewArrayExpression) {
            visit((NewArrayExpression) node);
        } else {
            createNode(node, "Unknown ExpressionNode");
        }
    }
    
    @Override
    public void visit(AssignmentExpression node) {
        createNode(node, "AssignmentExpression\nOperator: " + node.getOperator());
        createEdge(node, node.getLocation());
        node.getLocation().accept(this);
        createEdge(node, node.getExpression());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(BinaryExpression node) {
        createNode(node, "BinaryExpression\nOperator: " + node.getOperator());
        createEdge(node, node.getLeft());
        node.getLeft().accept(this);
        createEdge(node, node.getRight());
        node.getRight().accept(this);
    }

    @Override
    public void visit(UnaryExpression node) {
        createNode(node, "UnaryExpression\nOperator: " + node.getOperator());
        createEdge(node, node.getExpression());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(NewArrayExpression node) {
        createNode(node, "NewArrayExpression");
        createEdge(node, node.getType());
        node.getType().accept(this);
        createEdge(node, node.getSize());
        node.getSize().accept(this);
    }

    @Override
    public void visit(VariableLocation node) {
        createNode(node, "VariableLocation\n" + node.getName());
    }

    @Override
    public void visit(LocationNode node) {
        if (node instanceof VariableLocation) {
            visit((VariableLocation) node);
        } else if (node instanceof ArrayLocation) {
            visit((ArrayLocation) node);
        } else {
            // Manejar otros tipos de LocationNode si existen
            createNode(node, "Unknown LocationNode");
        }
    }

    @Override
    public void visit(ArrayLocation node) {
        createNode(node, "ArrayLocation\n" + node.getName());
        createEdge(node, node.getIndex());
        node.getIndex().accept(this);
    }

    @Override
    public void visit(MethodCallNode node) {
        if (node instanceof NormalMethodCallNode) {
            visit((NormalMethodCallNode) node);
        } else if (node instanceof CalloutCallNode) {
            visit((CalloutCallNode) node);
        } else {
            createNode(node, "Unknown MethodCallNode");
        }
    }

    @Override
    public void visit(NormalMethodCallNode node) {
        createNode(node, "NormalMethodCall\n" + node.getMethodName());
        for (ExpressionNode arg : node.getArguments()) {
            createEdge(node, arg);
            arg.accept(this);
        }
    }

    @Override
    public void visit(CalloutCallNode node) {
        createNode(node, "CalloutCall\n" + node.getFunctionName());
        for (CalloutArgument arg : node.getArguments()) {
            createEdge(node, arg);
            arg.accept(this);
        }
    }

    @Override
    public void visit(LiteralNode node) {
        if (node instanceof IntegerLiteral) {
            visit((IntegerLiteral) node);
        } else if (node instanceof BooleanLiteral) {
            visit((BooleanLiteral) node);
        } else if (node instanceof CharacterLiteral) {
            visit((CharacterLiteral) node);
        } else if (node instanceof StringLiteral) {
            visit((StringLiteral) node);
        } else {
            createNode(node, "Unknown LiteralNode");
        }
    }
    @Override
    public void visit(IntegerLiteral node) {
        createNode(node, "IntegerLiteral\n" + node.getValue());
    }

    @Override
    public void visit(CharacterLiteral node) {
        createNode(node, "CharacterLiteral\n'" + node.getValue() + "'");
    }

    @Override
    public void visit(BooleanLiteral node) {
        createNode(node, "BooleanLiteral\n" + node.getValue());
    }

    @Override
    public void visit(StringLiteral node) {
        createNode(node, "StringLiteral\n\"" + node.getValue() + "\"");
    }

    @Override
    public void visit(Parameter node) {
        String arrayStr = node.isArray() ? "[]" : "";
        createNode(node, "Parameter\n" + node.getName() + arrayStr);
        createEdge(node, node.getType());
        node.getType().accept(this);
    }
    @Override
    public void visit(CalloutArgument node) {
        if (node instanceof CalloutArgumentExpression) {
            visit((CalloutArgumentExpression) node);
        } else if (node instanceof CalloutArgumentString) {
            visit((CalloutArgumentString) node);
        } else {
            // Handle any other CalloutArgument types if necessary
            createNode(node, "Unknown CalloutArgument");
        }
    }


    @Override
    public void visit(CalloutArgumentExpression node) {
        createNode(node, "CalloutArgumentExpression");
        createEdge(node, node.getExpression());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(CalloutArgumentString node) {
        createNode(node, "CalloutArgumentString\n\"" + node.getValue() + "\"");
    }

    @Override
    public void visit(VariableNode node) {
        createNode(node, "VariableNode: " + node.getName());
    // Si `VariableNode` tiene hijos, crea las aristas correspondientes.
    
    }

    @Override
    public void visit(DataTypeNode node) {
        if (node instanceof IntegerTypeNode) {
            visit((IntegerTypeNode) node);
        } else if (node instanceof BooleanTypeNode) {
            visit((BooleanTypeNode) node);
        } else if (node instanceof CharacterTypeNode) {
            visit((CharacterTypeNode) node);
        } else if (node instanceof VoidTypeNode) {
            visit((VoidTypeNode) node);
        } else {
            createNode(node, "Unknown DataTypeNode");
        }
    }

    
    @Override
    public void visit(IntegerTypeNode node) {
        createNode(node, "Type\nint");
    }

    @Override
    public void visit(BooleanTypeNode node) {
        createNode(node, "Type\nboolean");
    }

    @Override
    public void visit(CharacterTypeNode node) {
        createNode(node, "Type\nchar");
    }

    @Override
    public void visit(VoidTypeNode node) {
        createNode(node, "Type\nvoid");
    }
}