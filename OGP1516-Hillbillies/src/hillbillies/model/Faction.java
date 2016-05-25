package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.HashSet;
import java.util.Set;

/**
 * A class of factions involving a unit and a world.
 * 
 * @version	2.9.05 technical beta
 * @author	Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Faction {
	
	/**
	 * Initialize this new faction with given unit and given world.
     *
     * @param	unit
     *			The unit for this new faction.
     * @param	world
     *			The world for this new faction.
     * @post	The world of this new faction is equal to the given world.
     * @effect	This faction is added to its world.
	 * @effect	The given unit is added to this new faction.
     */
	public Faction(Unit unit, World world) throws IllegalArgumentException {
		this.world = world;
		this.getWorld().addFaction(this);
		this.addUnit(unit);
	}
	
	/* Variables */
	
	/**
	 * Variable registering the world of this faction.
	 */
	private final World world;
	
	/**
	 * Set collecting references to units belonging to this faction.
 	 * 
 	 * @invar	The set of units is effective.
 	 * @invar	Each element in the set of units references a unit that is an acceptable unit for this faction.
 	 * @invar	Each unit in the UnitSet references this faction as the faction to which it is attached.
	 */
	private Set<Unit> UnitSet = new HashSet<>();
	
	/**
	 * Variable registering the scheduler of this Faction.
	 */
	private final Scheduler scheduler = new Scheduler(this);

	/**
	 * Constant reflecting the maximum amount of units in a faction.
	 * 
	 * @return	The maximum amount of units of all factions is 50.
	 *		  |	result == 50
	 */
	public static final int MAX_UNITS = 50;
	
	/* Methods */
	
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
     * @return True if and only if the given world contains less than the maximum amount of factions in the given world.
     */
    @Raw
	public boolean canHaveAsWorld(World world) {
		return (world.getNbFactions() < world.getMaxNbFactions());
	}
	
	/**
	 * Return the maximum amount of units in this faction.
	 */
	public int getMaxNbUnits() {
		return Faction.MAX_UNITS;
	}

	/**
	 * Return the number of units in this faction.
	 */
	public int getNbUnits() {
		return UnitSet.size();
	}
	
    /**
     * Return a set collecting all the units in this faction.
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
	 * @param	unit
	 *			The unit to remove.
	 * @post	The given unit is removed from this faction.
	 * @effect	If this faction does not longer contain units, this faction is removed from its world.
	 */
	public void removeUnit(Unit unit) {
		UnitSet.remove(unit);
		if (getNbUnits() == 0) {
			getWorld().removeFaction(unit.getFaction());
		}
	}

	/**
	 * Return the scheduler of this faction.
	 */
	public Scheduler getScheduler() {
		return this.scheduler;
	}
}
