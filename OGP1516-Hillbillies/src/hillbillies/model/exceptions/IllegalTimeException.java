package hillbillies.model.exceptions;

public class IllegalTimeException extends RuntimeException {

    public IllegalTimeException(String message){
        super(message);
        System.out.println("exception with message"+this.getMessage());
    }
    public IllegalTimeException(){
        super();
    }
}