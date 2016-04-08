package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.Unit;
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

public class Rest implements IActivity {

	public Rest(Unit unit) {
		this.unit = unit;
		this.timeLeftTillFirstPoint=((double)40)/this.unit.getToughness();
		this.gotFirsthitpoint=false;
	}

	public Unit unit;
	private double timeLeftTillFirstPoint;
	private boolean gotFirsthitpoint;
	/**
	 * No documentation required.
	 */
	@Override
	public void advanceActivityTime(double dt) {
		if(!gotFirsthitpoint){
			if(Util.fuzzyGreaterThanOrEqualTo(dt,timeLeftTillFirstPoint)){
				this.gotFirsthitpoint=true;
				this.timeLeftTillFirstPoint=0;
			}
			else { this.timeLeftTillFirstPoint=this.timeLeftTillFirstPoint-dt;}
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
		}
		if (unit.getCurrentStaminaPoints() < unit.getMaxPoints()) {
			// TODO: 8/04/16 check for max+let open an option for activity finished
			int SPGaining = (int) Math.ceil((dt / 0.2) * (unit.getToughness() / 100));
			unit.setCurrentStaminaPoints(unit.getCurrentStaminaPoints() + SPGaining);
		}
	}

	@Override
	public double returnSimpleTimeLeft() throws IllegalArgumentException {
		throw new IllegalArgumentException("A resting activity does not have a SimpleTimeLeft attribute.");
	}

	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		if (activity instanceof Defend||activity instanceof Fall) return true;
		else return gotFirsthitpoint;
	}

	@Override
	public int getId() {
		return 5;
	}
	
}
