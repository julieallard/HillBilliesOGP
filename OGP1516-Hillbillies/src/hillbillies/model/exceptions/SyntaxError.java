package hillbillies.model.exceptions;


public class SyntaxError extends Exception {

    public SyntaxError(String message,Exception cause){
        super(message,cause);
        System.out.println("Syntax error has occurred with the following message:"+message);
    }
}
