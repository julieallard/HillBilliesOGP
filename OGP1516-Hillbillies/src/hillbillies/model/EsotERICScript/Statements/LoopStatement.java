package hillbillies.model.EsotERICScript.Statements;
import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class LoopStatement extends Statement {
    public LoopStatement(Task task) {
        super(task);
    }
    // while e do s done
    class WhilePartStatement extends PartStatement {
        public WhilePartStatement(BooleanExpression condition, Statement body) {
            this.condition = condition;
            this.body = body;
        }
        private final BooleanExpression condition;
        private final Statement body;
        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit = executor.getExecutingUnit();
            while (this.condition.value(unit) && executor.canExecute()) {
                body.execute(executor);
            }
        }
        @Override
        boolean singular() {
            return false;
        }
    }
}
