package hillbillies.model.EsotERICScript.Statements;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.exceptions.SyntaxError;

/**
 * Filler class, Null object
 */

public final class NoStatement extends Statement {

    public NoStatement(Task task){
        super();
        this.setStatus(ExecutionStatus.TRIVIALEXECUTION);
    }
    @Override
    public void execute(ProgramExecutor executor) throws SyntaxError {
    }


}
