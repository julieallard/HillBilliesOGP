package hillbillies.model.EsotERICScript.Statements;
import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.Expressions.Expression;
import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.List;

public class Statement {
    public Statement(Task task) {
        this.beingExcecuted = false;
        this.executed = false;
        this.task = task;
    }
    public Statement encapsulatingStatement;
    protected boolean beingExcecuted;
    protected boolean executed;
    public Task task;
    public Unit executor;
    private PartStatement partStatement;

    public boolean mustBecompletedInOneExecution(){
        return this.partStatement.singular();
    }


    public void execute(ProgramExecutor executor) throws SyntaxError {
        this.executor = executor.getExecutor();
        executor.updateCallStackWith(this);
        this.partStatement.execute(executor);
    }

    public void finishExecuting() {
        this.beingExcecuted = false;
        this.executed = true;
        //TODO Execute next?
    }

    public void reExecutePrepare() {
        this.beingExcecuted = false;
        this.executed = false;
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
            Unit unit=executor.getExecutor();
            if (value instanceof BooleanExpression) {
                boolean booleanVar = ((BooleanExpression) value).value(unit);
            } else if (value instanceof PositionExpression) {
                int[] intVar = ((PositionExpression) value).value(unit);
            } else if (value instanceof UnitExpression) {
                Unit unitVar = ((UnitExpression) value).value(unit);
            }

        }

        @Override
        boolean singular() {
            return true;
        }

    }

    // while e do s done
    class WhilePartStatement extends PartStatement {

        public WhilePartStatement(BooleanExpression condition, Statement body) {
            this.condition = condition;
            this.body = body;
        }

        private final BooleanExpression condition;
        private final Statement body;
        private Boolean flag;
        private Boolean conditionEvaluated = false;

        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit=executor.getExecutor();
            if (!conditionEvaluated) {
                flag = condition.value(unit);
                conditionEvaluated = true;
            }
            while (flag)
                body.execute(executor);
        }
        @Override
        boolean singular() {
            return false;
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
            Unit unit=executor.getExecutor();
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
            for (Statement statement : statementList)
                statement.execute(executor);
        }

        @Override
        boolean singular() {
            return false;
        }

    }

}