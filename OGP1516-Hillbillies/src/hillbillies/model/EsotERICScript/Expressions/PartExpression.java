package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.exceptions.SyntaxError;

public abstract class PartExpression {

    public abstract Object getValue() throws SyntaxError;

	Expression arg1;
    Expression arg2;
    
}
