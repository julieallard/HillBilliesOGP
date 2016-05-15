package hillbillies.model.EsotERICScript.Statements;
import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.Expressions.Expression;
import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.List;

public class Statement {

    private ExecutionStatus status;

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public Statement(Task task) {
        this.status=ExecutionStatus.NOTYETEXECUTED;
        this.task = task;
    }

    public boolean isBeingExcecuted() {
       return this.status.equals(ExecutionStatus.BEINGEXECUTED);
    }

    public Statement encapsulatingStatement;
    public Task task;
    public Unit executingUnit;
    private PartStatement partStatement;

    public boolean mustBeCompletedInOneExecution() {
        return this.partStatement.singular();
    }

    public void execute(ProgramExecutor executor) throws SyntaxError {
        this.executingUnit = executor.getExecutingUnit();
        executor.updateCallStackWith(this);
        this.partStatement.execute(executor);
    }

    public void finishExecuting() {
        this.status=ExecutionStatus.FINISHED;
        //TODO Execute next?
    }

    public void reExecutePrepare() {
        this.status=ExecutionStatus.READYTOBEREEXECUTED;
    }

    // x := e
    // TODO: name the variable by given string
    class AssignmentPartStatement extends PartStatement {


        public AssignmentPartStatement(String variableName, Expression value) {
            this.variableName = variableName;
            this.value = value;
        }

        private final String variableName;
        private final Expression value;

        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit = executor.getExecutingUnit();
            if (value instanceof BooleanExpression) {
                task.booleanGlobalMap.put(variableName,(Boolean) value.value(unit));
            } else if (value instanceof PositionExpression) {
                task.positionGlabalMap.put(variableName,(int[]) value.value(unit));
            } else if (value instanceof UnitExpression) {
                task.unitGlobalMap.put(variableName,(Unit) value.value(unit));
            }

        }

        @Override
        boolean singular() {
            return true;
        }

    }


    // if e then s [ else s ] fi
    class IfPartStatement extends PartStatement {
        public IfPartStatement(BooleanExpression condition, Statement ifPart, Statement elsePart) {
            this.condition = condition;
            this.ifPart = ifPart;
            this.elsePart = elsePart;
        }

        private final BooleanExpression condition;
        private final Statement ifPart;
        private final Statement elsePart;
        private Boolean flag;
        private Boolean conditionEvaluated = false;

        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit = executor.getExecutingUnit();
            if (!conditionEvaluated) {
                flag = condition.value(unit);
                conditionEvaluated = true;
            }
            if (flag)
                ifPart.execute(executor);
            else
                elsePart.execute(executor);
        }
        @Override
        boolean singular() {
            return true;
        }

    }

    // break
    // TODO

    // print e
    // TODO

    // {s}
    class SequencePartStatement extends PartStatement {

        public SequencePartStatement(List<Statement> statements) {
            this.statementList = statements;
        }

        private final List<Statement> statementList;

        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            if (statementList.size() == 0)
                throw new SyntaxError("The list of statements is empty.");
            for (Statement statement: statementList)
                statement.execute(executor);
        }

        @Override
        boolean singular() {
            return false;
        }

    }

    public void proceed(ProgramExecutor executor){

    }

}