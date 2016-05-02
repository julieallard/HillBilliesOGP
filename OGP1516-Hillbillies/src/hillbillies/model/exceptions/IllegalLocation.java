package hillbillies.model.exceptions;

public class IllegalLocation extends RuntimeException {
    public IllegalLocation(String message){
        super(message);
    }
    public IllegalLocation(){
        super();
    }
}


