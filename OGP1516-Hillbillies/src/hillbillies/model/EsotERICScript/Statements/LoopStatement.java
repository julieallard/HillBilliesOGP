package hillbillies.model.EsotERICScript.Statements;
import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LoopStatement extends Statement {
    public LoopStatement(Task task) {
        super();
    }
    // while e do s done
    class WhilePartStatement extends PartStatement {
        public WhilePartStatement(BooleanExpression condition, Statement body) {
            this.condition = condition;
            this.body = body;
        }
        private final BooleanExpression condition;
        private final Statement body;

        private boolean flag;
        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit = executor.getExecutingUnit();
            body.reExecutePrepare();
            flag=condition.value(unit);
            if (!flag) setStatus(ExecutionStatus.COMPLETED);
        }
        @Override
        boolean singular() {
            return false;
        }
        @Override
        public Collection<Statement> probe() {
            Set<Statement> probeset =new HashSet<>();
            probeset.add(body);
            return probeset;
        }

        @Override
        public Statement getNext() throws SyntaxError {
            if (flag) return this.body;
            setStatus(ExecutionStatus.FINISHED);
            return getEncapsulatingStatement();
        }
    }
}
