package hillbillies.model.EsotERICScript.Expressions;


import hillbillies.model.EsotERICScript.Expression;
import hillbillies.model.exceptions.SyntaxError;

public abstract class PartExpression {

    public abstract Object getValue()  throws SyntaxError;
    public Expression arg1;
    public Expression arg2;

}
