    package ast;

    public class AssignOp extends Statement {
        private String op;
        private Location location;
        private Expr expression;

    

        // Constructor existente
        public AssignOp(Location location, String op, Expr expression) {
            this.location = location;
            this.op = op;
            this.expression = expression;
        }
        public String getOperator() {
            return op;
        }

        @Override
        public void execute() {
            // Implementa la lógica de ejecución para una asignación
        }
    }

