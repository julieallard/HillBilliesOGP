package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.Unit;

public class Rest implements IActivity {

	public Rest(Unit unit) {
		this.unit = unit;
	}
	
	public Unit unit;
	
	@Override
	public void advanceActivityTime(double dt) {
		if (unit.getCurrentHitPoints() < unit.getMaxStaminaPoints()) {
			int HitPointsToAdd = (int) Math.ceil((dt/0.2)*(unit.getToughness()/200));
			unit.setCurrentHitPoints(unit.getCurrentHitPoints() + HitPointsToAdd);
			//per 0.2s or continuously?	
		}
		
	}

	@Override
	public boolean hasSimpleTimeLeft() {
		return false;
	}

	@Override
	public double returnSimpleTimeLeft() throws IllegalArgumentException {
		throw new IllegalArgumentException("A resting activity doesn't have simple time left.");
	}

	@Override
	public boolean canBeInterruptedBy(String activity) {
		return true;
	}

	@Override
	public void interrupt() throws IllegalArgumentException {
		if (true) { //TODO
			throw new IllegalArgumentException("Attack cannot be Interrupted");
		}
	}
}
