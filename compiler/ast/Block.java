package ast;

import java.util.ArrayList;

public class Block extends Statement {
    private ArrayList<Statement> statements;
    private VarDeclList varDecls; // Lista de declaraciones de variables

    // Nuevo constructor que acepta VarDeclList y StatementList
    public Block(VarDeclList varDecls, StatementList statements) {
        super();
        this.varDecls = varDecls;
        this.statements = new ArrayList<>(statements); // Convertir StatementList en ArrayList
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    @Override
    public void execute() {
        // Ejecutar las declaraciones de variables primero (si es necesario)
        for (Statement statement : statements) {
            statement.execute();
        }
    }
}
