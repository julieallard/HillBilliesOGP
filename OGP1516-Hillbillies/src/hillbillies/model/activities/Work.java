package hillbillies.model.activities;

import hillbillies.model.CubeObjects.Air;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.IActivity;
import hillbillies.model.MovableWorldObject;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;
import ogp.framework.util.Util;

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

//TODO Units shall gain 10 experience points for every completed work order. No
//experience points shall be awarded for interrupted activities.
public class Work implements IActivity {
	
    public Work(Unit unit, VLocation targetLocation){
        this.timeLeft = ((double) 500)/unit.getStrength();
        this.unit = unit;
        this.targetLocation = targetLocation;
    }
    
    private Unit unit;
    private double timeLeft;
	private final VLocation targetLocation;

	@Override
	public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.returnSimpleTimeLeft())) {
            this.timeLeft = 0;
            conductWork();
        } else {
            double time = returnSimpleTimeLeft() - dt;
            this.setTimeLeft(time);
        }
	}

    /**
     * Return the time left for this Work.
     */
    @Basic
    @Raw
    @Override
	public double returnSimpleTimeLeft() throws IllegalArgumentException {
		return this.timeLeft;
	}

	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return true;
	}

	@Override
	public int getId() {
		return 4;
	}

    /**
     * Check whether the given time left is a valid time left for
     * any Work.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return Always true.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
        return true;
    }	
	
    /**
     * Set the time left for this Work to the given time left.
     *
     * @param  timeLeft
     *         The new time left for this Work.
     * @post   The time left fot this new Work is equal to
     *         the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any
     *         Work.
     */
    @Raw
    public void setTimeLeft(double timeLeft) throws IllegalTimeException {
        if (! isValidTimeLeft(timeLeft))
            throw new IllegalTimeException();
        this.timeLeft = timeLeft;
    }    
    
    private void conductWork() {
    	
    }
        
}