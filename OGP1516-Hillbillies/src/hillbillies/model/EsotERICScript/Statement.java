package hillbillies.model.EsotERICScript;


import hillbillies.model.exceptions.SyntaxError;

public abstract class Statement {


    public abstract void Execute() throws SyntaxError;

}
