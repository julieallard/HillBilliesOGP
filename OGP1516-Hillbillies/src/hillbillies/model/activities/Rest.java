package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

/**
 * A class of Rest activities involving a unit.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Rest implements IActivity {

	/**
	 * Initialize this new Rest with given unit.
     *
     * @param  unit
     *         The unit for this new Rest.
     * @post   The unit of this new Rest is equal to the given unit.
     */
	public Rest(Unit unit) {
		this.unit = unit;
		this.timeLeftTillFirstPoint = ((double) 40) / this.unit.getToughness();
		this.gotFirsthitpoint = false;
	}

	/* Variables */
	
    /**
     * Variable registering whether this rest has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering whether this rest is finished.
     */
    private boolean isFinished = false;
	
    /**
     * Variable registering the unit of this Rest.
     */
	public Unit unit;
	
    /**
     * Variable registering the time left until the first hitpoint is recovered during this Rest.
     */
	private double timeLeftTillFirstPoint;
	
    /**
     * Variable registering whether the first hitpoint has already been recovered during this Rest.
     */
	private boolean gotFirsthitpoint;
	
    /* Methods */
    
    /**
     * Return whether this rest has been dictated by a statement.
     */
    @Override
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this rest.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}
	
	// TODO
    /**
     * Update this defense according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     */
	@Override
	public void advanceActivityTime(double dt) {
		if (! gotFirsthitpoint) {
			if (Util.fuzzyGreaterThanOrEqualTo(dt, timeLeftTillFirstPoint)) {
				this.gotFirsthitpoint = true;
				this.timeLeftTillFirstPoint = 0;
			} else
				this.timeLeftTillFirstPoint=this.timeLeftTillFirstPoint-dt;
		}
		if (unit.getCurrentHitPoints() < unit.getMaxPoints()) {
			int HPGaining = (int) Math.ceil((dt / 0.2) * (unit.getToughness() / 200));
			int HPNeeded = unit.getMaxPoints() - unit.getCurrentHitPoints();
			double timeForGainingHPNeeded = HPNeeded * 0.2 / (unit.getToughness() / 200);
			if (timeForGainingHPNeeded < dt) {
				double timeForGainingSP = dt - timeForGainingHPNeeded;
				unit.setCurrentHitPoints(unit.getCurrentHitPoints() + HPNeeded);
				advanceActivityTime(timeForGainingSP);
			} else
				unit.setCurrentHitPoints(unit.getCurrentHitPoints() + HPGaining);
				unit.activityFinished();
		} else if (unit.getCurrentStaminaPoints() < unit.getMaxPoints()) {
			// TODO: 8/04/16 check for max+let open an option for activity finished
			int SPGaining = (int) Math.ceil((dt / 0.2) * (unit.getToughness() / 100));
			int SPNeeded = unit.getMaxPoints() - unit.getCurrentStaminaPoints();
			double timeForGainingSPNeeded = SPNeeded * 0.2 / (unit.getToughness() / 100);
			if (timeForGainingSPNeeded < dt) {
				unit.setCurrentHitPoints(unit.getCurrentStaminaPoints() + SPNeeded);
				unit.activityFinished();
			} else
				unit.setCurrentHitPoints(unit.getCurrentStaminaPoints() + SPGaining);
				unit.activityFinished();
			}
	}

    /**
     * Check whether this rest can be interrupted by the given activity.
     * 
     * @param	activity
     *			The activity to check.
     * @return	True if and only if the given activity is a defense or a fall or if this rest's unit already gained its first hitpoint.
     */
	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return activity instanceof Defend || activity instanceof Fall || gotFirsthitpoint;
	}

    /**
     * Return the ID of this rest.
     */
	@Override
	public int getId() {
		return 5;
	}

    /**
     * Return whether this rest is finished.
     */
	@Override
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * Finish this rest.
	 */
	@Override
	public void finishActivity() {
		this.isFinished = true;
		unit.activityFinished();
	}

}
