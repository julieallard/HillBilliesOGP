package hillbillies.model.exceptions;

public class IllegalTimeException extends RuntimeException {

    public IllegalTimeException(String message){
        super(message);
    }
    public IllegalTimeException(){
        super();
    }
}