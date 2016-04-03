package hillbillies.model;


/**
 * the Id's of the activities are the following:
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
 * 7: noActivity
 */

public interface IActivity {


    void advanceActivityTime(double dt);

    double returnSimpleTimeLeft() throws IllegalArgumentException;

    boolean canBeInterruptedBy(IActivity activity);

    int getId();




}

