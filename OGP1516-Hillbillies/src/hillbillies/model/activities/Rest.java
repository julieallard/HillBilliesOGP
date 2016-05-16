package hillbillies.model.activities;

import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

/**
 * A class of Rest activities involving a unit.
 * 
 * The Id's of the activities are the following:
 * 0: noActivity
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
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
	
	/**
	 * No documentation required.
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
     * Return the time left until finishing this Rest.
     */
	@Override
	public double returnSimpleTimeLeft() throws IllegalArgumentException {
		throw new IllegalArgumentException("A resting activity does not have a SimpleTimeLeft attribute.");
	}

    /**
     * Check whether this Rest can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		if (activity instanceof Defend||activity instanceof Fall) return true;
		else return gotFirsthitpoint;
	}

    /**
     * Return the ID of this Rest.
     */
	@Override
	public int getId() {
		return 5;
	}

	private Boolean isFinished;

	@Override
	public boolean isFinished() {
		return isFinished;
	}
	@Override
	public boolean isDictatedByStatement() {
		return false;
	}

	@Override
	public Statement getControllingStatement() {
		throw new IllegalArgumentException("Rest can't be directly dictated by statement");
	}
}
