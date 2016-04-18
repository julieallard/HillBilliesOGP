package hillbillies.model.exceptions;


import java.io.SyncFailedException;

public class SyntaxError extends Exception {

    public SyntaxError(String message,Exception cause){
        super(message,cause);
        System.out.println("Syntax error has occurred with the following message:"+message);
    }
    public SyntaxError(String message){
        super(message);
        System.out.println("Syntax error has occurred with the following message:"+message);
    }

}
