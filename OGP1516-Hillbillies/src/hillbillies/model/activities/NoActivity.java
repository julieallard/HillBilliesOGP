package hillbillies.model.activities;

import hillbillies.model.IActivity;

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

public class NoActivity implements IActivity {

	/**
	 * No documentation required.
	 */
    @Override
    public void advanceActivityTime(double dt) {

    }

    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        return 0;
    }

    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return true;
    }

    @Override
    public int getId() {
        return 0;
    }
}
