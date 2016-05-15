package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.exceptions.SyntaxError;

import java.util.Collection;

public abstract class PartStatement {

    public abstract void execute(ProgramExecutor executor) throws SyntaxError;
    
    abstract boolean singular();



    public void refresh(){
        this.probe().forEach(Statement::reExecutePrepare);
    }
    public abstract Collection<Statement> probe();
    
}