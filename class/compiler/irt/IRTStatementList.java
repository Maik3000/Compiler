package compiler.irt;

import java.util.ArrayList;
import java.util.List;

public class IRTStatementList extends IRTStatement {
    private List<IRTStatement> statements;

    public IRTStatementList() {
        statements = new ArrayList<>();
    }

    public void addStatement(IRTStatement stmt) {
        statements.add(stmt);
    }

    public List<IRTStatement> getStatements() {
        return statements;
    }

    @Override
    public void accept(IRTVisitor visitor) {
        visitor.visit(this);
    }
}
