package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.Set;

public class Faction {
	
	public Faction(Unit unit, World world) {
		this.world = world;
		world.addFaction(this);
		this.addUnit(unit);
	}
	
	private final World world;
	
	/**
	 * Set collecting references to Units belonging to this faction.
	 * 
	 * @invar The set of Units is effective.
	 * @invar Each Unit in the UnitSet references this faction as the faction to
	 * 		  which it is attached.
	 */
	private Set<Unit> UnitSet;

    /**
     * Return the world of this faction.
     */
    @Basic
    @Raw
    @Immutable
	public World getWorld() {
		return this.world;
	}
	
    /**
     * Check whether this faction can have the given world as its world.
     *
     * @param  world
     *         The world to check.
     * @return True if and only if the given world contains less
     * 		   than 5 factions.
     */
    @Raw
	public boolean canHaveAsWorld(World world) {
		return (world.getNumberOfFactions() < 5);
	}
	
	public Set<Unit> getUnitSet() {
		return this.UnitSet;
	}	

	/**
	 * Add the given unit to this faction.
	 * 
	 * @param  unit
	 * 		   The unit to add.
	 * @post   The given unit is added to this faction.
	 */
	public void addUnit(Unit unit) throws IllegalArgumentException {
		if (! unit.canHaveAsFaction(this))
			throw new IllegalArgumentException("This faction has already reached its max amount of Units.");
		UnitSet.add(unit);
	}
	
	/**
	 * Remove the given unit from this faction.
	 * 
	 * @param  unit
	 * 		   The unit to remove.
	 * @post   The given unit is removed from this faction.
	 */
	public void removeUnit(Unit unit) {
		UnitSet.remove(unit);
		if (getNumberOfUnits() == 0) {
			getWorld().removeFaction(unit.getFaction());
		}
	}

	/**
	 * Return the number of units of this faction.
	 */
	public int getNumberOfUnits() {
		return UnitSet.size();
	}

}
