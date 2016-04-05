package hillbillies.model;

/**
 * The Id's of the activities are the following:
 * 0: noActivity
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
 */

public interface IActivity {

    void advanceActivityTime(double dt);

    double returnSimpleTimeLeft() throws IllegalArgumentException;

    boolean canBeInterruptedBy(IActivity activity);

    int getId();

}

