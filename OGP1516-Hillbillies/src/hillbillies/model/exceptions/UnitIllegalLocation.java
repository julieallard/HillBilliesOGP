package hillbillies.model.exceptions;

public class UnitIllegalLocation extends RuntimeException {
    public UnitIllegalLocation(String message){
        super(message);
    }
    public UnitIllegalLocation(){
        super();
    }
}
}

