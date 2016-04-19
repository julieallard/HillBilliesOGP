package hillbillies.model.EsotERICScript;


import hillbillies.model.Task;
import hillbillies.model.exceptions.SyntaxError;

public abstract class Expression {

    public abstract Object value() throws SyntaxError;

    public Task task;


}
