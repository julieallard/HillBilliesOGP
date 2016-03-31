package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Faction {
	
	//Een nieuwe faction wordt altijd aangemaakt bij creatie van een Unit.
	public Faction(Unit unit) {
		//FactionSet is een set die in World gecreëerd moet worden,
		//met max. 5 factions als elementen.
		FactionSet.add(this);
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
	
	public void addUnit(Unit unit) throws RuntimeException {
		if (!canHaveAsFaction(this)) {
			throw new RuntimeException
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
	
}
