package compiler.irt;

public interface IRTVisitor {
    void visit(IRTBINOP node);
    void visit(IRTConst node);
    void visit(IRTMOVE node);
    void visit(IRTTEMP node);
    void visit(IRTCJUMP node);
    void visit(IRTLabel node);
    void visit(IRTJUMP node);
    void visit(IRTCALL node);
    void visit(IRTRETURN node);
    void visit(IRTStatementList node);
    void visit(IRTMethod node);
    void visit(IREXP node);
    // Asegúrate de incluir todos los métodos visit para tus clases IRT
}
