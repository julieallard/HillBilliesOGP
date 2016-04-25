package hillbillies.model.EsotERICScript.Statements;


import hillbillies.model.EsotERICScript.Expression;
import hillbillies.model.EsotERICScript.Statement;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public abstract class PartStatement {

    public abstract void execute(Unit unit) throws SyntaxError;

    public void setExecuted(boolean state) {
        executed = state;
    }

    public boolean executed;

    public boolean isExecuted() {
        return executed;
    }

    public Expression argExpr1;

    Statement argStat1;

    Statement argStat2;

}
