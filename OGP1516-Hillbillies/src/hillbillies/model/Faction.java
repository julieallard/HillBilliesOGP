package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.HashSet;
import java.util.Set;

/**
 * A class of Factions involving a unit and a world.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Faction {
	
	/**
	 * Initialize this new Faction with given unit and given world.
     *
     * @param  unit
     *         The unit for this new Faction.
     * @param  world
     * 		   The world for this new Log.
	 * @effect The given Unit is added to this new Faction.
     * @post   The world of this new Faction is equal to the given world.
     */
	public Faction(Unit unit, World world) throws IllegalArgumentException {
		this.world = world;
		world.addFaction(this);
		this.addUnit(unit);
        this.UnitSet = new HashSet<>();
	}
	
	/**
	 * Variable registering the world of this Faction.
	 */
	private final World world;
	
	/**
	 * Set collecting references to Units belonging to this faction.
	 * 
	 * @invar The set of Units is effective.
	 * @invar Each element in the set of Units references a unit that is an acceptable unit for this Faction.
	 * @invar Each Unit in the UnitSet references this faction as the faction to which it is attached.
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
     * @return True if and only if the given world contains less than 5 factions.
     */
    @Raw
	public boolean canHaveAsWorld(World world) {
		return (world.getNumberOfFactions() < 5);
	}
	
    /**
     * Return a set collecting all the units in this Faction.
     */
	public Set<Unit> getUnitSet() {
		return this.UnitSet;
	}	

	/**
	 * Add the given unit to this faction.
	 * 
	 * @param  unit
	 * 		   The unit to add.
	 * @post   The given unit is added to this faction.
	 * @throws IllegalArgumentException
	 * 		   The given unit cannot have this faction as a faction.
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
	 * @effect The given unit is removed from this faction. If this faction does not longer contain units,
	 * 		   this Faction is removed from its world.
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
