package hillbillies.model;

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
			throw RuntimeException
		} else {
			UnitSet.add(unit);
		}
	}
	
	public void removeUnit(Unit unit) {
		UnitSet.remove(unit);	
	}
	
	public int getNumberOfUnits() {
		UnitSet.size();
	}
	
	//in another class:
	public Faction getSmallestFaction() {
		smallestFaction = FactionSet<0>;
		for (i=1; i<5; i++) {
			if (FactionSet<i>.size() < smallestFaction.size()) {
				smallestFaction = FactionSet<i>;
			};
		return smallestFaction;
		}
	}
	
	//implement in World class:
	/**
	 * Set collecting references to Factions belonging to this world.
	 * 
	 * @invar The set of Factions is effective.
	 * 		| FactionSet != null
	 * @invar Each Faction in the FactionSet references this world as the world to
	 * 		  which it is attached.
	 * 		| for each Faction in FactionSet:
	 * 		|	(FactionSet.getWorld() == this) || 
	 */
	private Set<Faction> FactionSet;
	
	//implement in World class:
	public int getNumberOfFactions() {
		return FactionSet.size();
	}
	
	//implement in Unit constructor:
	this.addToFaction;
	
	//implement in Unit class:
	private int faction;
	
	//implement in Unit class:
	public void setFaction() throws RuntimeException {
		if (getNumberOfFactions() < 5) {
			Faction faction = new Faction(this);
			this.faction = faction;
		} else if (!canHaveAsFaction(getSmallestFaction())) {
			throw new RuntimeException;
		} else {
			this.faction = getSmallestFaction();
			getSmallestFaction().add(this);
		}
	}
	
	//implement in Unit class:
	public Faction getFaction() {
		return this.faction();
	}
	
	//implement in Unit class:
	public boolean canHaveAsFaction(Faction faction) {
		if (faction.UnitSet.size() < 50) {
			return true;
		} else {
			return false;
		}
	}
	
}
