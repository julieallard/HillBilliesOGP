package hillbillies.model;


import hillbillies.model.Unit;

public interface IActivity {

    void createNewActivity(Unit unit);

    void advanceActivityTime();

    boolean hasSimpleTimeLeft();

    double returnSimpleTimeLeft();

    boolean canBeInterruptedBy(Object activity);

    void interrupt();




}
