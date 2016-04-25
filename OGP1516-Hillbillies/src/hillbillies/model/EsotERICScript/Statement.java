package hillbillies.model.EsotERICScript;


import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Statements.PartStatement;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;



public  class Statement {

    public Statement(Expression exarg1,Expression exarg2)

    public void Execute(Unit unit) throws SyntaxError{
        this.partStatement.execute(unit);
    }
    private PartStatement partStatement;

    public class WorkPartStatement extends PartStatement{

        public WorkPartStatement(PositionExpression argument) {
            this.argExpr1=argument;
        }
        public PositionExpression argExpr1;
        @Override
        public void execute(Unit unit) throws SyntaxError {
            unit.work(argExpr1.value());
            this.setExecuted(true);
    }
}
