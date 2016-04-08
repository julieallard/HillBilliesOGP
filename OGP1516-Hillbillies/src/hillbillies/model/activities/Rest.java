package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.Unit;

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
	}

	public Unit unit;

	/**
	 * No documentation required.
	 */
	@Override
	public void advanceActivityTime(double dt) {
		//per 0.2s or continuously?
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
		return true;
	}

	@Override
	public int getId() {
		return 5;
	}
	
}
