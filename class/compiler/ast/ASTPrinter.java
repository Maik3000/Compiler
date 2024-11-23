package compiler.ast;

import java.io.PrintWriter;


public class ASTPrinter implements ASTVisitor {
    private PrintWriter writer;
    private int indent = 0;

    public ASTPrinter(PrintWriter writer) {
        this.writer = writer;
    }

    private void println(String s) {
        for (int i = 0; i < indent; i++) {
            writer.print("  ");
        }
        writer.println(s);
    }

    private void indent() {
        indent++;
    }

    private void unindent() {
        if (indent > 0) indent--;
    }

    @Override
    public void visit(ProgramNode program) {
        println("Program: " + program.getName());
        indent();
        for (ClassMember member : program.getBody()) {
            member.accept(this);
        }
        unindent();
    }

    @Override
    public void visit(VarDeclaration varDecl) {
        String arrayStr = varDecl.isArray() ? "[]" : "";
        println("VarDeclaration: " + varDecl.getName() + " Type: " + varDecl.getType().getClass().getSimpleName() + arrayStr);
        if (varDecl.getInitExpr() != null) {
            indent();
            println("InitExpr:");
            indent();
            varDecl.getInitExpr().accept(this);
            unindent();
            unindent();
        }
    }

    @Override
    public void visit(MethodDeclaration methodDecl) {
        println("MethodDeclaration: " + methodDecl.getName() + " ReturnType: " + methodDecl.getReturnType().getClass().getSimpleName());
        indent();
        if (!methodDecl.getParameters().isEmpty()) {
            println("Parameters:");
            indent();
            for (Parameter param : methodDecl.getParameters()) {
                println("Parameter: " + param.getName() + " Type: " + param.getType().getClass().getSimpleName() + (param.isArray() ? "[]" : ""));
            }
            unindent();
        }
        println("Body:");
        methodDecl.getBody().accept(this);
        unindent();
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
        } else {
            println("Unknown StatementNode");
        }
    }

    @Override
    public void visit(BlockNode block) {
        println("BlockNode:");
        indent();
        if (!block.getVarDeclarations().isEmpty()) {
            println("VarDeclarations:");
            indent();
            for (VarDeclaration varDecl : block.getVarDeclarations()) {
                varDecl.accept(this);
            }
            unindent();
        }
        if (!block.getStatements().isEmpty()) {
            println("Statements:");
            indent();
            for (StatementNode stmt : block.getStatements()) {
                stmt.accept(this);
            }
            unindent();
        }
        unindent();
    }

    @Override
    public void visit(AssignmentStatement assignStmt) {
        println("AssignmentStatement:");
        indent();
        println("Location:");
        indent();
        assignStmt.getLocation().accept(this);
        unindent();
        println("Operator: " + assignStmt.getOperator());
        println("Expression:");
        indent();
        assignStmt.getExpression().accept(this);
        unindent();
        unindent();
    }

    @Override
    public void visit(MethodCallStatement methodCallStmt) {
        println("MethodCallStatement:");
        indent();
        methodCallStmt.getMethodCall().accept(this);
        unindent();
    }

    @Override
    public void visit(IfStatement ifStmt) {
        println("IfStatement:");
        indent();
        println("Condition:");
        indent();
        ifStmt.getCondition().accept(this);
        unindent();
        println("Then Block:");
        indent();
        if (ifStmt.getThenBlock() != null) {
            ifStmt.getThenBlock().accept(this);
        } else {
            println("None");
        }
        unindent();
        if (ifStmt.getElseBlock() != null) {
            println("Else Block:");
            indent();
            ifStmt.getElseBlock().accept(this);
            unindent();
        }
        unindent();
    }

    @Override
    public void visit(WhileStatement whileStmt) {
        println("WhileStatement:");
        indent();
        println("Condition:");
        indent();
        whileStmt.getCondition().accept(this);
        unindent();
        println("Body:");
        indent();
        whileStmt.getBody().accept(this);
        unindent();
        unindent();
    }

    @Override
    public void visit(ForStatement forStmt) {
        println("ForStatement:");
        indent();
        if (forStmt.getInitExpr() != null) {
            println("Initialization:");
            indent();
            forStmt.getInitExpr().accept(this);
            unindent();
        }
        if (forStmt.getCondition() != null) {
            println("Condition:");
            indent();
            forStmt.getCondition().accept(this);
            unindent();
        }
        if (forStmt.getUpdateStmt() != null) {
            println("Update:");
            indent();
            forStmt.getUpdateStmt().accept(this);
            unindent();
        }
        println("Body:");
        indent();
        forStmt.getBody().accept(this);
        unindent();
        unindent();
    }

    @Override
    public void visit(ReturnStatement returnStmt) {
        println("ReturnStatement:");
        indent();
        if (returnStmt.getExpression() != null) {
            returnStmt.getExpression().accept(this);
        } else {
            println("void");
        }
        unindent();
    }

    @Override
    public void visit(BreakStatement breakStmt) {
        println("BreakStatement");
    }

    @Override
    public void visit(ContinueStatement continueStmt) {
        println("ContinueStatement");
    }

    @Override
    public void visit(ExpressionStatement exprStmt) {
        println("ExpressionStatement:");
        indent();
        exprStmt.getExpression().accept(this);
        unindent();
    }

    @Override
    public void visit(ClassMember node) {
        if (node instanceof VarDeclaration) {
            visit((VarDeclaration) node);
        } else if (node instanceof MethodDeclaration) {
            visit((MethodDeclaration) node);
        } else {
            println("Unknown ClassMember");
        }
    }

    @Override
    public void visit(VarDeclarationStatement varDeclStmt) {
        println("VarDeclarationStatement:");
        indent();
        varDeclStmt.getVarDeclaration().accept(this);
        if (varDeclStmt.getInitExpression() != null) {
            println("InitExpression:");
            indent();
            varDeclStmt.getInitExpression().accept(this);
            unindent();
        }
        unindent();
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
            println("Unknown ExpressionNode");
        }
    }

    @Override
    public void visit(AssignmentExpression assignExpr) {
        println("AssignmentExpression:");
        indent();
        println("Location:");
        indent();
        assignExpr.getLocation().accept(this);
        unindent();
        println("Operator: " + assignExpr.getOperator());
        println("Expression:");
        indent();
        assignExpr.getExpression().accept(this);
        unindent();
        unindent();
    }

    @Override
    public void visit(CalloutCallNode calloutCall) {
        println("CalloutCall: " + calloutCall.getFunctionName());
        indent();
        if (!calloutCall.getArguments().isEmpty()) {
            println("Arguments:");
            indent();
            for (CalloutArgument arg : calloutCall.getCalloutArguments()) {
                arg.accept(this);
            }
            unindent();
        }
        unindent();
    }

    @Override
    public void visit(NewArrayExpression newArrayExpr) {
        println("NewArrayExpression:");
        indent();
        println("Type:");
        indent();
        newArrayExpr.getType().accept(this);
        unindent();
        println("Size:");
        indent();
        newArrayExpr.getSize().accept(this);
        unindent();
        unindent();
    }

    @Override
    public void visit(BinaryExpression binaryExpr) {
        println("BinaryExpression: " + binaryExpr.getOperator());
        indent();
        println("Left:");
        indent();
        binaryExpr.getLeft().accept(this);
        unindent();
        println("Right:");
        indent();
        binaryExpr.getRight().accept(this);
        unindent();
        unindent();
    }

    @Override
    public void visit(UnaryExpression unaryExpr) {
        println("UnaryExpression: " + unaryExpr.getOperator());
        indent();
        unaryExpr.getExpression().accept(this);
        unindent();
    }

    @Override
    public void visit(MethodCallNode node) {
        if (node instanceof NormalMethodCallNode) {
            visit((NormalMethodCallNode) node);
        } else if (node instanceof CalloutCallNode) {
            visit((CalloutCallNode) node);
        } else {
            println("Unknown MethodCallNode");
        }
    }

    @Override
    public void visit(LocationNode node) {
        if (node instanceof VariableLocation) {
            visit((VariableLocation) node);
        } else if (node instanceof ArrayLocation) {
            visit((ArrayLocation) node);
        } else {
            println("Unknown LocationNode");
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
            println("Unknown LiteralNode");
        }
    }

    @Override
    public void visit(IntegerLiteral intLiteral) {
        println("IntegerLiteral: " + intLiteral.getValue());
    }

    @Override
    public void visit(BooleanLiteral boolLiteral) {
        println("BooleanLiteral: " + boolLiteral.getValue());
    }

    @Override
    public void visit(CharacterLiteral charLiteral) {
        println("CharacterLiteral: '" + charLiteral.getValue() + "'");
    }

    @Override
    public void visit(StringLiteral stringLiteral) {
        println("StringLiteral: \"" + stringLiteral.getValue() + "\"");
    }

    @Override
    public void visit(VariableLocation varLocation) {
        println("VariableLocation: " + varLocation.getName());
    }

    @Override
    public void visit(ArrayLocation arrayLocation) {
        println("ArrayLocation: " + arrayLocation.getName());
        indent();
        println("Index:");
        indent();
        arrayLocation.getIndex().accept(this);
        unindent();
        unindent();
    }

    
    @Override
    public void visit(Parameter param) {
        String arrayStr = param.isArray() ? "[]" : "";
        println("Parameter: " + param.getName() + " Type: " + param.getType().getClass().getSimpleName() + arrayStr);
    }

    @Override
    public void visit(CalloutArgument arg) {
        if (arg instanceof CalloutArgumentExpression) {
            visit((CalloutArgumentExpression) arg);
        } else if (arg instanceof CalloutArgumentString) {
            visit((CalloutArgumentString) arg);
        } else {
            println("Unknown CalloutArgument type");
        }
    }

    @Override
    public void visit(CalloutArgumentExpression exprArg) {
        println("CalloutArgumentExpression:");
        indent();
        exprArg.getExpression().accept(this);
        unindent();
    }

    @Override
    public void visit(CalloutArgumentString stringArg) {
        println("CalloutArgumentString: \"" + stringArg.getValue() + "\"");
    }

    @Override
    public void visit(VariableNode node) {
        println("VariableNode: " + node.getName());
    }

    @Override
    public void visit(MultipleVarDeclaration multiVarDecl) {
        for (VarDeclaration decl : multiVarDecl.getDeclarations()) {
            decl.accept(this);
        }
    }
    @Override
    public void visit(NormalMethodCallNode node) {
        println("NormalMethodCall: " + node.getMethodName());
        indent();
        if (!node.getArguments().isEmpty()) {
            println("Arguments:");
            indent();
            for (ExpressionNode arg : node.getArguments()) {
                arg.accept(this);
            }
            unindent();
        }
        unindent();
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
            println("Unknown DataTypeNode");
        }
    }

    @Override
    public void visit(IntegerTypeNode intType) {
        println("Type: int");
    }

    @Override
    public void visit(BooleanTypeNode booleanType) {
        println("Type: boolean");
    }

    @Override
    public void visit(CharacterTypeNode charType) {
        println("Type: char");
    }

    @Override
    public void visit(VoidTypeNode voidTypeNode) {
        println("Type: void");
    }
    @Override
    public void visit(ASTNode node) {
        // Implementación vacía o lógica necesaria
    }
}