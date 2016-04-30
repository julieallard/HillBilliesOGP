package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.EsotERICScript.Expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public abstract class PartStatement {

    public abstract void execute(Unit unit, double dt) throws SyntaxError;

    public Expression argExpr1;

    Statement argStat1;

    Statement argStat2;

}