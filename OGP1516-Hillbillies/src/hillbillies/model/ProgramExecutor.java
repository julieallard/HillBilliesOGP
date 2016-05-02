package hillbillies.model;

import hillbillies.model.EsotERICScript.Statements.Statement;

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

    public Unit getExecutor() {
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
    	//TODO
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

}