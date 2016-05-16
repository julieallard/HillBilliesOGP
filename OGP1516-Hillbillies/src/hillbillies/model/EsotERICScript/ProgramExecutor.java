package hillbillies.model.EsotERICScript;

import hillbillies.model.EsotERICScript.Statements.LoopStatement;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Task;
import hillbillies.model.Unit;
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

    public double getDeltat() {
        return dt;
    }

    public ProgramExecutor setDeltat(double dt) {
        if (dt<0) dt=0;
        this.dt = dt;
        return this;
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
        Stack<Statement> stack= new Stack<>();
        stack.addAll(this.statementCallStack);
        return stack;
    }
    public static boolean CheckBreakLegality(Task task){
        Statement root =task.getRootStatement();
        Stack<Statement> statementStack=new Stack<>();
        statementStack.push(root);
        Set<Statement> Breakset= new HashSet<>();
        while(!statementStack.isEmpty()){
            Collection<Statement> statementCollection=statementStack.pop().getPartStatement().probe();
            for (Statement statement : statementCollection) {
                statementStack.push(statement);
                if(statement.getPartStatement() instanceof Statement.BreakPartStatement) Breakset.add(statement);
            }
        }
        for (Statement statement : Breakset) {
            if (!hasEncapsulatingLoop(statement)) return false;
        }
        return true;
    }

    public void updateCallStackWith(Statement latestStat){
        if(statementCallStack.peek().equals(latestStat.getEncapsulatingStatement())){
            statementCallStack.push(latestStat);
            return;
        }
        Statement stat=statementCallStack.peek();
        while (!stat.equals(latestStat.getEncapsulatingStatement())){
            statementCallStack.pop();
            statementCallStack.peek();
        }
        statementCallStack.push(latestStat);
    }
    public boolean canExecute(){
        return !(this.dt<0.001);
    }
    public void execute(Statement statement) throws SyntaxError {
        if (statement.isBeingExcecuted()) {
            statement.proceed(this);
        } else {
            this.setDeltat(dt - 0.001);
        }
    }
    public void pushUpdate(Statement statement) {
        this.addStatementToList(statement);
        this.updateCallStackWith(statement);
    }
    public static boolean hasEncapsulatingLoop(Statement statement){
        Statement encapstat=statement.getEncapsulatingStatement();
        if (encapstat==null) return false;
        if (encapstat instanceof LoopStatement) return true;
        return hasEncapsulatingLoop(encapstat);
    }
}