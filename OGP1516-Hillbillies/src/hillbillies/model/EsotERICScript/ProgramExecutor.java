package hillbillies.model.EsotERICScript;




import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        return (Stack<Statement>) statementCallStack.clone();
    }

    public static boolean TaskBreakValidityCheck(Task task){
    	return false;
    }

    public void updateCallStackWith(Statement latestStat){
        if(statementCallStack.peek().equals(latestStat.encapsulatingStatement)){
            statementCallStack.push(latestStat);
            return;
        }
        Statement stat=statementCallStack.peek();
        while (!stat.equals(latestStat.encapsulatingStatement)){
            statementCallStack.pop();
            statementCallStack.peek();
        }
        statementCallStack.push(latestStat);
    }
    public boolean canExecute(){
        return !(this.dt<0.001);
    }
    public void execute(Statement statement) throws SyntaxError{
        if (statement.isBeingExcecuted()){
            statement.proceed(this);
        }
        else{
            this.setDeltat(dt-0.001);
        }
    }
 
    public void pushUpdate(Statement statement) {
        this.addStatementToList(statement);
        this.updateCallStackWith(statement);
    }
}