package hillbillies.model.EsotERICScript;

import hillbillies.model.EsotERICScript.Statements.ExecutionStatus;
import hillbillies.model.EsotERICScript.Statements.LoopStatement;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.SyntaxError;

import java.util.*;
public class ProgramExecutor {

    public ProgramExecutor(Unit executor, Task task) {
        this.executor = executor;
        this.task = task;
    }

    private final Unit executor;
    private final Task task;
    private final List<Statement> statementList = new ArrayList<>();
    private Stack<Statement> statementCallStack = new Stack<>();
    private double dt;

    public void execute() throws SyntaxError {
        Statement current;
        Statement last=null;
        if (task.getStatus().equals(ExecutionStatus.NOTYETEXECUTED)) current=task.getRootStatement();
        else current=findPausedStatement();
        while(canExecute()) {
            current.execute(this);
            if (getExecutingUnit().getActivity() instanceof NoActivity) {
                getExecutingUnit().advanceTime(this.getDeltat());
                current.setStatus(ExecutionStatus.PAUSED);
                return;
            }
            last=current;
             current= last.getNext();
        }
        if (last != null) {
            last.setStatus(ExecutionStatus.PAUSED);
        }
    }


    public double getDeltat() {
        return dt;
    }

    public void setDeltat(double dt) {
        if (dt < 0) dt = 0;
        this.dt = dt;
    }

    public Unit getExecutingUnit() {
        return executor;
    }

    public Task getTask() {
        return task;
    }

    public void addStatementToList(Statement statement) {
        statementList.add(statement);
    }

    public void removeStatementFromList(Statement statement) {
        statementList.remove(statement);
    }

    public List<Statement> getStatementList() {
        return statementList;
    }

    public Stack<Statement> getStatementStackClone() {
        Stack<Statement> stack = new Stack<>();
        stack.addAll(this.statementCallStack);
        return stack;
    }

    public static boolean checkBreakLegality(Task task) {
        Statement root = task.getRootStatement();
        Stack<Statement> statementStack = new Stack<>();
        statementStack.push(root);
        Set<Statement> Breakset = new HashSet<>();
        while (!statementStack.isEmpty()) {
            Collection<Statement> statementCollection = statementStack.pop().getPartStatement().probe();
            for (Statement statement : statementCollection) {
                statementStack.push(statement);
                if (statement.getPartStatement() instanceof Statement.BreakPartStatement) Breakset.add(statement);
            }
        }
        for (Statement statement : Breakset) {
            if (!hasEncapsulatingLoop(statement)) return false;
        }
        return true;
    }

    public void updateCallStackWith(Statement latestStat) {
        if (statementCallStack.peek().equals(latestStat.getEncapsulatingStatement())) {
            statementCallStack.push(latestStat);
            return;
        }
        Statement stat = statementCallStack.peek();
        while (!stat.equals(latestStat.getEncapsulatingStatement())) {
            statementCallStack.pop();
            statementCallStack.peek();
        }
        statementCallStack.push(latestStat);
    }

    public boolean canExecute() {
        return !(this.dt < 0.001);
    }

    public void pushUpdate(Statement statement) {
        this.addStatementToList(statement);
        this.updateCallStackWith(statement);
    }

    public static boolean hasEncapsulatingLoop(Statement statement) {
        Statement encapstat = statement.getEncapsulatingStatement();
        if (encapstat == null) return false;
        return encapstat instanceof LoopStatement || hasEncapsulatingLoop(encapstat);
    }

    public static Statement findEncapsulatingLoop(Statement statement) throws SyntaxError {
        Statement encapstat = statement.getEncapsulatingStatement();
            if (encapstat ==null) throw new SyntaxError("No while loop found");
            if (encapstat instanceof LoopStatement) return encapstat;
        return findEncapsulatingLoop(encapstat);
    }

    public Statement findPausedStatement() {
        Statement root = task.getRootStatement();
        Stack<Statement> statementStack = new Stack<>();
        statementStack.push(root);
        Statement paused = null;
        while (!statementStack.isEmpty()) {
            Collection<Statement> statementCollection = statementStack.pop().getPartStatement().probe();
            for (Statement statement : statementCollection) {
                statementStack.push(statement);
                if (statement.getStatus().equals(ExecutionStatus.PAUSED)) {
                    paused = statement;
                    break;
                }
            }
        }
        return paused;
    }

    public static void resetExecutionstyle(Task task){
        Statement root = task.getRootStatement();
        Stack<Statement> statementStack = new Stack<>();
        statementStack.push(root);
        while (!statementStack.isEmpty()) {
            Collection<Statement> statementCollection = statementStack.pop().getPartStatement().probe();
            for (Statement statement : statementCollection) {
                statementStack.push(statement);
                    statement.setStatus(ExecutionStatus.NOTYETEXECUTED);
            }
        }
    }
}
