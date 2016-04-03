package hillbillies.model.exceptions;

public class UnitIllegalLocation extends RuntimeException {
    public UnitIllegalLocation () {
        str ="no comment";
    }
    public UnitIllegalLocation(String comment){
        this.str=comment;
    }
    public final String str;

    public String getComment(){
        return this.str;
    }

}

