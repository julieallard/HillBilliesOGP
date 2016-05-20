package hillbillies.model.EsotERICScript.Statements;
import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.Expressions.Expression;
import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.*;

public class Statement {

    private ExecutionStatus status;

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public Statement() {
        this.status = ExecutionStatus.NOTYETEXECUTED;
    }

    public Statement getNext() throws SyntaxError {
        return this.getPartStatement().getNext();
    }

    public boolean isBeingExcecuted() {
        return this.status.equals(ExecutionStatus.BEINGEXECUTED);
    }

    private Statement encapsulatingStatement;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Unit getExecutingUnit() {
        return executingUnit;
    }

    public void setExecutingUnit(Unit executingUnit) {
        this.executingUnit = executingUnit;
    }

    public Task task;

    public Unit executingUnit;

    private PartStatement partStatement;

    public boolean mustBeCompletedInOneExecution() {
        return this.getPartStatement().singular();
    }

    public void execute(ProgramExecutor executor) throws SyntaxError {
        this.executingUnit = executor.getExecutingUnit();
        if (!executor.canExecute()) return;
        executor.pushUpdate(this);
        this.setStatus(ExecutionStatus.BEINGEXECUTED);
        this.getPartStatement().execute(executor);
    }

    public void finishExecuting() {
        this.status = ExecutionStatus.FINISHED;
    }

    public void reExecutePrepare() {
        this.getPartStatement().refresh();
        this.status = ExecutionStatus.READYTOBEREEXECUTED;
    }

    public Statement getEncapsulatingStatement() {
        return encapsulatingStatement;
    }

    private void setEncapsulatingStatement(Statement encapsulatingStatement) {
        this.encapsulatingStatement = encapsulatingStatement;

    }

    public PartStatement getPartStatement() {
        return partStatement;
    }

    public void setPartStatement(PartStatement partStatement) {
        this.partStatement = partStatement;
        for (Statement statement :partStatement.probe()){
            statement.setEncapsulatingStatement(this);
        }
    }

    // x := e
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
                task.booleanGlobalMap.put(variableName, (Boolean) value.value(unit));
            } else if (value instanceof PositionExpression) {
                task.positionGlobalMap.put(variableName, (int[]) value.value(unit));
            } else if (value instanceof UnitExpression) {
                task.unitGlobalMap.put(variableName, (Unit) value.value(unit));
            }
            Statement.this.setStatus(ExecutionStatus.COMPLETED);
        }

        @Override
        boolean singular() {
            return true;
        }

        @Override
        public Collection<Statement> probe() {
            return Collections.EMPTY_SET;
        }

        @Override
        public Statement getNext() {
            Statement.this.setStatus(ExecutionStatus.FINISHED);
            return Statement.this.getEncapsulatingStatement();
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
            Statement.this.setStatus(ExecutionStatus.COMPLETED);
        }

        @Override
        boolean singular() {
            return true;
        }

        @Override
        public Collection<Statement> probe() {
            Set<Statement> probeset = new HashSet<>();
            probeset.add(ifPart);
            probeset.add(elsePart);
            return probeset;
        }

        @Override
        public Statement getNext() {
            if (Statement.this.getStatus()==ExecutionStatus.COMPLETED){

                return Statement.this.getEncapsulatingStatement();
            }
            setStatus(ExecutionStatus.COMPLETED);
            if (flag){ return ifPart;}
            else {return elsePart;}
        }

        @Override
        public void refresh() {
            super.refresh();
            this.conditionEvaluated = false;
        }
    }

    // break
    public class BreakPartStatement extends PartStatement {
        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Statement.this.setStatus(ExecutionStatus.COMPLETED);
        }

        @Override
        boolean singular() {
            return true;
        }

        @Override
        public Collection<Statement> probe() {
            return Collections.EMPTY_SET;
        }

        @Override
        public Statement getNext() throws SyntaxError {
            return ProgramExecutor.findEncapsulatingLoop(Statement.this);
        }

    }
        class SequencePartStatement extends PartStatement {

            public SequencePartStatement(List<Statement> statements) {
                this.statementList = statements;
            }

            private final List<Statement> statementList;

            private Statement currentStatement;

            @Override
            public void execute(ProgramExecutor executor) throws SyntaxError {
                if (statementList.size() == 0){
                    setStatus(ExecutionStatus.COMPLETED);
                    return;}
                currentStatement=statementList.get(0);
                statementList.remove(0);
            }
            @Override
            boolean singular() {
                return false;
            }

            @Override
            public Collection<Statement> probe() {
                return statementList;
            }

            @Override
            public Statement getNext() throws SyntaxError {
                if (getStatus().equals(ExecutionStatus.COMPLETED)){
                    setStatus(ExecutionStatus.FINISHED);
                    return getEncapsulatingStatement();
                }
                Statement interm=currentStatement;
                currentStatement=null;
                return interm;
            }
        }

    public static Statement newAssignstatement(String key,Expression value){
        Statement statement=new Statement();
        statement.setPartStatement(statement.new AssignmentPartStatement(key,value));
        return statement;
    }

    public static Statement newIfStatement(BooleanExpression condition,Statement ifpart,Statement elsePart){
        Statement statement=new Statement();
        statement.setPartStatement(statement.new IfPartStatement(condition,ifpart,elsePart));
        return statement;
    }

    public static Statement newBreakStatement(){
        Statement statement=new Statement();
        statement.setPartStatement(statement.new BreakPartStatement());
        return statement;
    }

    public static Statement newSequenceStatement(List<Statement> statementList){
        Statement statement=new Statement();
        statement.setPartStatement(statement.new SequencePartStatement(statementList));
        return statement;
    }

    public static Statement newWorkStatement(PositionExpression location) {
        ActionStatement statement=new ActionStatement();
        statement.setPartStatement(statement.new WorkPartStatement(location));
        return statement;
    }
    public static Statement newAttackStatement(UnitExpression defender){
        ActionStatement statement=new ActionStatement();
        statement.setPartStatement(statement.new AttackPartStatement(defender));
        return statement;
    }

    public static Statement newMovetoStatement(PositionExpression location) {
        ActionStatement statement=new ActionStatement();
        statement.setPartStatement(statement.new MoveToPartStatement(location));
        return statement;
    }
    public static Statement newFollowStatement(UnitExpression leader){
        ActionStatement statement=new ActionStatement();
        statement.setPartStatement(statement.new FollowPartStatement(leader));
        return statement;
    }
    public static Statement newWhileStatement(BooleanExpression condition, Statement body){
        LoopStatement statement=new LoopStatement();
        statement.setPartStatement(statement.new WhilePartStatement(condition,body));
        return statement;
    }

}
