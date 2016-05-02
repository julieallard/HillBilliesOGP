package hillbillies.model;

import hillbillies.model.EsotERICScript.Statements.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ProgramExecutor {

    private final Unit Excecutor;
    private final Task task;
    private final List<Statement> statementList;

    private Stack<Statement> statementStack;

    public ProgramExecutor(Unit excecutor, Task task, List<Statement> statementList) {
        this.Excecutor = excecutor;
        this.task = task;
        this.statementList=new ArrayList<>();
        this.statementStack=new Stack<>();

    }

    public Unit getExcecutor() {
        return Excecutor;
    }

    public Task getTask() {
        return task;
    }

    public List<Statement> getStatementList() {
        return statementList;
    }

    public Stack<Statement> getStatementStackClone() {
        return (Stack<Statement>) statementStack.clone();
    }




    public static boolean TaskBreakValidityCheck(Task task){

        task.taks
    }
}
