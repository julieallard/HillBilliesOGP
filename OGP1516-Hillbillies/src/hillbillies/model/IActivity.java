package hillbillies.model;

public interface IActivity {


    void advanceActivityTime(double dt);

    boolean hasSimpleTimeLeft();

    double returnSimpleTimeLeft() throws IllegalArgumentException;

    boolean canBeInterruptedBy(IActivity activity);

    void interrupt() throws IllegalArgumentException;




}

