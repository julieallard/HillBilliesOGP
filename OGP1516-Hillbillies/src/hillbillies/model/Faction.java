package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Faction {
	
	public Faction(Unit unit) {
		World.getFactionSet.addFaction(this);
		this.addUnit(unit);
	}
	
	/**
	 * Set collecting references to Units belonging to this faction.
	 * 
	 * @invar The set of Units is effective.
	 * 		| UnitSet != null
	 * @invar Each Unit in the UnitSet references this faction as the faction to
	 * 		  which it is attached.
	 * 		| for each Unit in UnitSet:
	 * 		|	(UnitSet.getFaction() == this) || 
	 */
	private Set<Unit> UnitSet;
	
	public void addUnit(Unit unit) throws IllegalArgumentException {
		if (!unit.canHaveAsFaction(this)) {
			throw new IllegalArgumentException("Faction already contains its max no of Units.");
		} else {
			UnitSet.add(unit);
		}
	}
	
	public void removeUnit(Unit unit) {
		UnitSet.remove(unit);	
	}
	
	public int getNumberOfUnits() {
		return UnitSet.size();
	}
	
	public Set<Unit> getUnitSet() {
		return this.UnitSet;
	}
	
	public boolean canHaveAsWorld(World world) {
		if (world.getFactionSet.size() < 5) {
			return true;
		} else {
			return false;
		}
	}

}
