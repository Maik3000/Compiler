package compiler.irt;
import java.io.PrintWriter;


public class IRTPrinter implements IRTVisitor {
    private int indentLevel = 0;
    private PrintWriter writer;

    public IRTPrinter(PrintWriter writer) {
        this.writer = writer;
    }

    private void indent() {
        for(int i = 0; i < indentLevel; i++) {
            writer.print("  ");
        }
    }


    @Override
    public void visit(IRTConst node) {
        indent();
        writer.println("CONST " + node.getValue());
    }

    @Override
    public void visit(IRTMOVE node) {
        indent();
        writer.println("MOVE");
        indentLevel++;
        node.getDest().accept(this);
        node.getSrc().accept(this);
        indentLevel--;
    }

    @Override
    public void visit(IRTTEMP node) {
        indent();
        writer.println("TEMP " + node.getName());
    }

    @Override
    public void visit(IRTCJUMP node) {
        indent();
        writer.println("CJUMP");
        indentLevel++;
        node.getCondition().accept(this);
        indent();
        writer.println("TRUE LABEL: " + node.getTrueLabel());
        indent();
        writer.println("FALSE LABEL: " + node.getFalseLabel());
        indentLevel--;
    }

    @Override
    public void visit(IRTLabel node) {
        indent();
        writer.println("LABEL " + node.getLabel());
    }

    @Override
    public void visit(IRTJUMP node) {
        indent();
        writer.println("JUMP " + node.getTargetLabel());
    }

    @Override
    public void visit(IRTCALL node) {
        indent();
        writer.println("CALL " + node.getMethodName());
        indentLevel++;
        for (IRTExpression arg : node.getArguments()) {
            arg.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(IRTRETURN node) {
        indent();
        writer.println("RETURN");
        indentLevel++;
        node.getExpression().accept(this);
        indentLevel--;
    }

    @Override
    public void visit(IRTStatementList node) {
        for (IRTStatement stmt : node.getStatements()) {
            stmt.accept(this);
        }
    }

    @Override
    public void visit(IRTMethod node) {
        indent();
        writer.println("METHOD " + node.getName());
        indentLevel++;
        node.getBody().accept(this);
        indentLevel--;
    }

    @Override
    public void visit(IREXP node) {
        indent();
        writer.println("EXP");
        indentLevel++;
        node.getExpression().accept(this);
        indentLevel--;
    }
    @Override
    public void visit(IRTBINOP node) {
        indent();
        writer.println("BINOP " + node.getOperator());
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
    }

}
