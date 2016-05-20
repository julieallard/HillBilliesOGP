package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.activities.*;
import hillbillies.model.exceptions.IllegalLocation;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.exceptions.SyntaxError;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;

import java.util.Random;

/**
 * A class of hillbilly units involving a name, an x, y and z coordinate, a weight, agility, strength, toughness, a facility to enable the default behaviour and a world.
 * 
 * @invar	The name of each unit must be a valid name for any unit.
 *		  | isValidName(getName())
 * @invar	The weight of each unit must be a valid weight for any unit.
 *		  |	isValidWeight(getWeight())
 * @invar	The strength of each unit must be a valid strength for any unit.
 *		  |	isValidProperty(getStrength())
 * @invar	The agility of each unit must be a valid agility for any unit.
 *		  |	isValidProperty(getAgility()) 
 * @invar	The toughness of each unit must be a valid toughness for any unit.
 *		  |	isValidProperty(getToughness()) 
 * @invar	The number of hitpoints of each unit must be a valid number of hitpoints for any unit.
 *		  |	isValidPoints(getCurrentHitPoints())
 * @invar	The number of stamina points of each unit must be a valid number of stamina points for any unit.
 *		  |	isValidPoints(getCurrentStaminaPoints())
 * @invar	The orientation of each unit must be a valid orientation for any unit.
 *		  |	isValidOrientation(getOrientation())
 * @invar	Each unit can have its faction as its faction.
 * 		  |	canHaveAsFaction(getFaction)
 * @invar	The number of experience points of each unit must be a valid number of experience points for any unit.
 *		  |	isValidXP(getXP())
 * @invar	The number of used experience points of each unit must be a valid number of used experience points for any unit.
 *		  |	isValidXPUsed(getXPUsed())
 * @invar	The time since the last rest of each unit must be a valid time since the last rest for any unit.
 * 		  |	isValidTimeSinceLastRest(getTimeSinceLastRest())
 * @invar	The task of each unit must be a valid task for any unit.
 * 		  |	isValidTask(getTask())
 * @invar	The activity of each unit must be a valid activity for any unit.
 * 		  |	isValidActivity(getActivity())
 *        
 * @version 2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Unit extends MovableWorldObject {

    /**
     * Initialize this new unit with given name, given initial x, y and z coordinate, given weight, given agility, given strength, given toughness,
     * given default behaviour state and given world.
     *
     * @param	name
     * 			The name for this unit.
     * @param	x
     * 			The x coordinate for this unit.
     * @param	y
     * 			The y coordinate for this unit.
     * @param	z
     * 			The z coordinate for this unit.
     * @param	weight
     * 			The weight for this unit.
     * @param	strength
     * 			The strength for this unit.
     * @param	agility
     * 			The agility for this unit.
     * @param	toughness
     * 			The toughness for this unit.
     * @param	enableDefaultBehavior
     * 			The state of behaviour for this unit.
     * @effect	The name of this new unit is set to the given name.
     *		  |	this.setName(name)
     * @effect	The agility of this unit is set to the given agility.
     * 		  |	this.setAgility(agility)
     * @effect	The strength of this unit is set to the given strength.
     * 		  |	this.setStrength(strength)
     * @effect	The toughness of this unit is set to the given toughness.
     * 		  |	this.setToughness(toughness)
     * @effect	The weight of this unit is set to the given weight.
     * 		  |	this.setWeight(weight)
     * @effect	The initial state of behavior of this new unit is set according to the given flag.
     *		  |	this.setDefaultBehavior(enableDefaultBehavior)
     * @effect	The world of this new unit is set to the given world.
     *		  |	this.setWorld(world)
     * @effect	A faction is assigned to this new unit.
     * 		  | this.setFaction()
     * @effect	The location if this unit is set to the given x, y and z coordinate.
     *		  |	this.setLocation(x, y, z)
     * @effect	The activity of this new unit is set to none.
     * 		  |	this.setActivity(new NoActivity)
     */
    public Unit(String name, double x, double y, double z, int weight, int strength, int agility, int toughness, boolean enableDefaultBehavior, World world)
    		throws IllegalLocation, IllegalArgumentException {
        this.setName(name);
        this.setAgility(agility);
        this.setStrength(strength);
        this.setToughness(toughness);
        this.setWeight(weight);
        propAssignedUponCreation = true;
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setFaction();
        this.setLocation(x, y, z);
        this.setActivity(new NoActivity());
    }

    /**
     * Initialize this new hillbilly unit with given name, given initial x, y and z coordinate, given weight, given agility, given strength, given toughness
     * and given default behaviour state.
     *
     * @param	name
     * 			The name for this unit.
     * @param	x
     * 			The x coordinate for this unit.
     * @param	y
     * 			The y coordinate for this unit.
     * @param	z
     * 			The z coordinate for this unit.
     * @param	weight
				The weight for this unit.
     * @param	strength
				The strength for this unit.
     * @param	agility
				The agility for this unit.
     * @param	toughness
				The toughness for this unit.
     * @param	enableDefaultBehavior
     * 			The state of behaviour for this unit.
     * @effect	The name of this new unit is set to the given name.
     *		  |	this.setName(name)
     * @effect	The agility of this unit is set to the given agility.
     * 		  |	this.setAgility(agility)
     * @effect	The strength of this unit is set to the given strength.
     * 		  |	this.setStrength(strength)
     * @effect	The toughness of this unit is set to the given toughness.
     * 		  |	this.setToughness(toughness)
     * @effect	The weight of this unit is set to the given weight.
     * 		  |	this.setWeight(weight)
     * @effect	The initial state of behavior of this new unit is set according to the given flag.
     *		  |	this.setDefaultBehavior(enableDefaultBehavior)
     * @effect	The world of this new unit is set to a cubical world with side size max(x, y, z).
     *		  |	this.setWorld(world)
     * @effect	A faction is assigned to this new unit.
     * 		  | this.setFaction()
     * @effect	The location of this unit is set to the given x, y and z coordinate.
     *		  |	this.setLocation(x, y, z)
     * @effect	The activity of this new unit is set to none.
     * 		  |	this.setActivity(new NoActivity)
     */
    public Unit(String name, double x, double y, double z, int weight, int strength, int agility, int toughness,
                boolean enableDefaultBehavior) throws IllegalLocation, IllegalArgumentException {
        this.setName(name);
        this.setAgility(agility);
        this.setStrength(strength);
        this.setToughness(toughness);
        this.setWeight(weight);
        propAssignedUponCreation = true;
        this.setDefaultBehavior(enableDefaultBehavior);
        int longestSideSize = (int) Math.ceil(x);
        if (y > longestSideSize)
            longestSideSize = (int) Math.ceil(y);
        if (z > longestSideSize)
            longestSideSize = (int) Math.ceil(z);
        int[][][] randomCubeWorld = new int[longestSideSize][longestSideSize][longestSideSize];
        TerrainChangeListener randomTerrainChangeListener = new DefaultTerrainChangeListener();
        World world = new World(randomCubeWorld, randomTerrainChangeListener);
        this.setWorld(world);
        this.setFaction();
        this.setLocation(x, y, z);
        this.setActivity(new NoActivity());
    }

    /**
     * Initialize this new hillbilly unit with given world and given default behaviour state.
     *
     * @param	world
     * 			The world for this unit.
     * @param	enableDefaultBehavior
     * 			The state of behaviour for this unit.
     * @effect	The name of this new unit is set to a random name.
     *		  |	this.setName(name)
     * @effect	The agility of this unit is set to a random agility.
     * 		  |	this.setAgility(agility)
     * @effect	The strength of this unit is set to a random strength.
     * 		  |	this.setStrength(strength)
     * @effect	The toughness of this unit is set to a random toughness.
     * 		  |	this.setToughness(toughness)
     * @effect	The weight of this unit is set to a random weight.
     * 		  |	this.setWeight(weight)
     * @effect	The initial state of behavior of this new unit is set according to the given flag.
     *		  |	this.setDefaultBehavior(enableDefaultBehavior)
     * @effect	The world of this new unit is set to the given world.
     *		  |	this.setWorld(world)
     * @effect	A faction is assigned to this new unit.
     * 		  | this.setFaction()
     * @effect	The location if this unit is set to a random x, y and z coordinate.
     *		  |	this.setLocation(x, y, z)
     * @effect	The activity of this new unit is set to none.
     * 		  |	this.setActivity(new NoActivity)
     */
    public Unit(World world, boolean enableDefaultBehavior) {
        String name = getRandomName();
        this.setName(name);
        int randomWeight = random.nextInt(76) + 25;
        int randomStrength = random.nextInt(76) + 25;
        int randomAgility = random.nextInt(76) + 25;
        int randomToughness = random.nextInt(76) + 25;
        this.setAgility(randomAgility);
        this.setStrength(randomStrength);
        this.setToughness(randomToughness);
        this.setWeight(randomWeight);
        propAssignedUponCreation = true;
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setFaction();
        int randomLocX;
        int randomLocY;
        int randomLocZ;
        while (true) {
            randomLocX = random.nextInt(world.getxSideSize());
            randomLocY = random.nextInt(world.getySideSize());
            randomLocZ = random.nextInt(world.getzSideSize());
            int[] randomLoc = new int[]{randomLocX, randomLocY, randomLocZ};
            if (world.canHaveAsCubeLocation(randomLoc, this))
                break;
        }
        this.setLocation(randomLocX, randomLocY, randomLocZ);
        this.setActivity(new NoActivity());
    }

    /* Variables */
    /**
     * Variable registering the name of this unit.
     */
    private String name;

    /**
     * Variable registering the weight of this unit.
     */
    public int weight;
    /**
     * Variable registering the agility of this unit.
     */
    private int agility;

    /**
     * Variable registering the strength of this unit.
     */
    private int strength;

    /**
     * Variable registering the toughness of this unit.
     */
    private int toughness;
    
    /**
     * Variable registering whether the weight, strength, agility and toughness properties have already been assigned upon creation of this unit.
     */
    private boolean propAssignedUponCreation = false;

    /**
     * Variable registering the state of default behavior of this unit.
     */
    private boolean defaultBehaviorEnabled;

    /**
     * Variable registering the hitpoints of this unit.
     */
    private int hitpoints = getMaxPoints();

    /**
     * Variable registering the stamina points of this unit.
     */
    private int staminapoints = getMaxPoints();

    /**
     * Variable registering the orientation of this unit.
     */
    private float orientation = (float) (0.5 * Math.PI);

    /**
     * Variable registering whether this unit is carrying an object.
     */
    private boolean isCarrying = false;

    /**
     * Variable registering the object this unit is carrying.
     */
    private InanimateMovableWorldObject carriedObject;

    /**
     * Variable registering whether this unit is sprinting.
     */
    private boolean isSprinting = false;

    /**
     * Variable registering the time since this unit rested for the last time.
     */
    public double timeSinceLastRest;

    /**
     * Variable registering the activity of this unit which is paused but will be continued after this unit finishes its current activity.
     */
    private IActivity pausedActivity;

    /**
     * Variable registering whether this unit has a paused activity.
     */
    private boolean hasPausedActivity;
    
    /**
     * Variable registering the faction this unit belongs to.
     */
    private Faction faction;
    
    /**
     * Object holding the random generator used during the random cration of the unit.
     */
    private Random random = new Random();
    
    /**
     * Variable registering the experience points of this unit.
     */
    private int xp = 0;

    /**
     * Variable registering the number of experience points of this unit that have already been used to increase its strength, agility or toughness.
     */
    private int xpused = 0;

    /**
     * Variable registering whether this unit is terminated.
     */
    private boolean isTerminated = false;
    
    /**
     * Variable registering the task assigned to this unit.
     */
    private Task task;
    
    /**
     * Variable registering whether a task is assigned to this unit.
     */
    private boolean hasTask = false;
    
	/**
	 * Constant reflecting the lowest possible value for the weight, strength, agility and toughness properties of a unit.
	 * 
	 * @return	The lowest possible value for the weight, strength, agility and toughness properties of all units is 1.
	 *		  |	result == 1
	 */
	public static final int MIN_PROPERTY = 1;
	
	/**
	 * Constant reflecting the highest possible value for the weight, strength, agility and toughness properties of a unit.
	 * 
	 * @return	The highest possible value for the weight, strength, agility and toughness properties of all units is 200.
	 *        |	result == 200
	 */
	public static final int MAX_PROPERTY = 200;
	
	/**
	 * Constant reflecting the lowest possible value for the weight, strength, agility and toughness properties of a unit upon creation of the unit.
	 * 
	 * @return	The lowest possible value for the weight, strength, agility and toughness properties of all units is 25 upon creation of the units.
	 *		  |	result == 25
	 */
	public static final int MIN_PROPERTY_UPON_CREATION = 25;
	
	/**
	 * Constant reflecting the highest possible value for the weight, strength, agility and toughness properties of a unit upon creation of the unit.
	 * 
	 * @return	The highest possible value for the weight, strength, agility and toughness properties of all units is 100 upon creation of the units.
	 *		  |	result == 100
	 */
	public static final int MAX_PROPERTY_UPON_CREATION = 100;
    
	/* Methods */
	
    /**
     * Return the amount of time since this unit rested for the last time.
     */
    @Basic
    @Raw
    private double getTimeSinceLastRest() {
    	return this.timeSinceLastRest;
    }
    
    /**
     * Check whether the given time is a valid amount of time since the last rest for any unit.
     *  
     * @param	dt
     *			The time to check.
     * @return	True if and only if the given time is positive.
     *		  |	result == dt >= 0
     */
    private boolean isValidTimeSinceLastRest(double dt) {
    	return (dt >= 0);
    }
    
    /**
     * Set the amount of time since this unit's last rest to the given time.
     * 
     * @param  dt
     *         The new time since the last rest for this unit.
     * @post   The amount of time since this unit's last rest is equal to the given time.
     *       | new.getTimeSinceLastRest() == dt
     * @throws IllegalTimeException
     *         The given time is not a valid amount of time since the last rest for any unit.
     *       | ! isValidTimeSinceLastRest(dt)
     */
    @Raw
    private void setTimeSinceLastRest(double dt) throws IllegalTimeException {
    	if (!isValidTimeSinceLastRest(dt))
    		throw new IllegalTimeException();
    	this.timeSinceLastRest = dt;
    }
    
    /**
     * Update this unit's position and activity status according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     */
    @Override
    public void advanceTime(double dt) throws SyntaxError {
        setTimeSinceLastRest(getTimeSinceLastRest() + dt);
        if (getTimeSinceLastRest() >= 300) {
            boolean flag = this.interrupt(new Rest(this));
            if (flag) setTimeSinceLastRest(0);
        }
        if(this.getActivity().getId()!=0) {
            this.getActivity().advanceActivityTime(dt);
            return;
        }

        if (this.hasTask()) {
            task.advanceTime(dt);
            return;
        }
        if ((! this.hasTask())&& isDefaultBehaviorEnabled()) {
            Task task = this.getFaction().getScheduler().offerTask();
            if (task==null) return;
            task.advanceTime(dt);
        }
    }

	
    /**
     * Return the name of this unit.
     */
    @Basic
    @Raw
    public String getName() {
        return name;
    }

    /**
     * Check whether the given name is a valid name for any unit.
     *
     * @param	name
     * 			The name to check.
     * @return	True if and only if the given name contains at least two characters, if the first character is an uppercase letter and if all characters
     * 			are uppercase or lowercase letters, single or double quotes or spaces.
     *		  |	result == (name.length() >= 2)
     *		  |		&& Character.isUpperCase(name.charAt(0))
     *		  |		&& "[A-Z | a-z | \" | \' | \\s]*".matches(name)
     */
    private static boolean isValidName(String name) {
        return (name.length() >= 2) && Character.isUpperCase(name.charAt(0)) && "[A-Z | a-z | \" | \' | \\s]*".matches(name);
    }

    /**
     * Set the name of this unit to the given name.
     *
     * @param	name
     * 			The new name for this unit.
     * @post	The name of this new unit is equal to the given name.
     * 		  |	new.getName() == name
     * @throws	IllegalArgumentException()
     * 			The given initial name is not a valid name for any unit.
     *        | ! isValidName(getName)
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException {
        if (!isValidName(name))
            throw new IllegalArgumentException("Invalid name");
        this.name = name;
    }

    /**
     * Check whether the given property is a valid property for any unit.
     *
     * @param	property
     * 			The property to check.
     * @param	min
     * 			The given minimum value of the property. 
     * @param	max
     * 			The given maximum value of the property.
     * @return	True if and only if the property number is equal or higher than the given minimum value and if it is equal or lower than the given maximum value,
     * 			inclusive.
     *		  | result ==
     *		  |		property >= min
     *		  |		&& property <= max
     */
    private boolean isValidProperty(int property, int min, int max) {
        return (property >= min && property <= max);
    }

    /**
     * Return the weight of this unit.
     * 
     * @return	This unit's weight, augmented by its carried object's weight, if this unit is carrying an object.
     * 			Otherwise, only its own weight.
     * 		  | if (isCarrying())
     * 		  |		then result == this.weight + this.carriedObject.getWeight()
     * 		  |		else result == this.weight
     */
    @Basic
    @Raw
    @Override
    public int getWeight() {
        if (isCarrying())
            return this.weight + this.carriedObject.getWeight();
        return this.weight;
    }
    
    /**
     * Return the minimum weight of this unit.
     * 
     * @return	An integer calculated as half of the sum of this unit's strength and agility.
     */
    public int getMinWeight() {
        return (int) (0.5 * (this.getStrength() + this.getAgility()));
    }

    /**
     * Set the weight of this unit to the given weight.
     *
     * @param	weight
     * 			The new weight for this unit.
     * @post	If the given weight is a valid weight for this unit, the weight of this new unit is equal to the given weight.
     * 			Otherwise, if the given weight is below the minimum weight of this unit, its new weight is equal to the minimum weight of this unit.
     * 			If the given weight is above the highest possible value for the weight of a unit, its new weight is equal to the highest possible value for
     * 			the weight of a unit.
     *		  | if (isValidProperty(weight, min, max))
     * 		  |		then new.getWeight() == weight
     * 		  |		else if (weight < min)
     * 		  |		then new.getWeight() == min
     * 		  |	 	else if (weight > max)
     * 		  |	 	then new.getWeight() == max
     */
    @Raw
    public void setWeight(int weight) {
    	int min = getMinWeight();
    	int max = MAX_PROPERTY;
    	if (!propAssignedUponCreation)
    		max = MAX_PROPERTY_UPON_CREATION;    
        if (isValidProperty(weight, min, max))
            this.weight = weight;
        else if (weight < min)
            this.weight = min;
        else if (weight > max)
            this.weight = max;
    }

    /**
     * Return the strength of this unit.
     */
    @Basic
    @Raw
    public int getStrength() {
        return this.strength;
    }

    /**
     * Set the strength of this unit to the given strength.
     *
     * @param	strength
     * 			The new strength for this unit.
     * @post	If the given strength is a valid strength for this unit, the strength of this new unit is equal to the given strength.
     * 			Otherwise, if the given strength is below the lowest possible value for the strength of a unit, its new strength is equal to the
     * 			lowest possible value for the strength of a unit.
     * 			If the given strength is above the highest possible value for the strength of a unit, its new strength is equal to the highest possible value for the
     * 			strength of a unit.
     *		  | if (isValidStrength(strength, min, max))
     * 		  |		then new.getStrength() == strength
     * 		  |		else if (strength < min)
     * 		  |		then new.getStrength() == min
     * 		  |	 	else if (strength > max)
     * 		  |	 	then new.getStrength() == max
     */
    @Raw
    public void setStrength(int strength) {
    	int min = MIN_PROPERTY;
    	int max = MAX_PROPERTY;
    	if (!propAssignedUponCreation) {
    		min = MIN_PROPERTY_UPON_CREATION;
    		max = MAX_PROPERTY_UPON_CREATION;
    	}
        if (isValidProperty(strength, min, max))
            this.strength = strength;
        else if (strength < min)
            this.strength = min;
        else if (strength > max)
            this.strength = max;
    }

    /**
     * Return the agility of this unit.
     */
    @Basic
    @Raw
    public int getAgility() {
        return this.agility;
    }
    
    /**
     * Set the agility of this unit to the given agility.
     *
     * @param	agility
     * 			The new agility for this unit.
     * @post	If the given agility is a valid agility for this unit, the agility of this new unit is equal to the given agility.
     * 			Otherwise, if the given agility is below the lowest possible value for the agility of a unit, its new agility is equal to the
     * 			lowest possible value for the agility of a unit.
     * 			If the given agility is above the highest possible value for the agility of a unit, its new agility is equal to the highest possible value for the
     * 			agility of a unit.
     *		  | if (isValidStrength(agility, min, max))
     * 		  |		then new.getAgility() == agility
     * 		  |		else if (agility < min)
     * 		  |		then new.getAgility() == min
     * 		  |	 	else if (agility > max)
     * 		  |	 	then new.getAgility() == max
     */
    @Raw
    public void setAgility(int agility) {
    	int min = MIN_PROPERTY;
    	int max = MAX_PROPERTY;
    	if (!propAssignedUponCreation) {
    		min = MIN_PROPERTY_UPON_CREATION;
    		max = MAX_PROPERTY_UPON_CREATION;
    	}    	
        if (isValidProperty(agility, min, max))
            this.agility = agility;
        else if (agility < min)
            this.agility = min;
        else if (agility > max)
            this.agility = max;
    }

    /**
     * Return the toughness of this unit.
     */
    @Basic
    @Raw
    public int getToughness() {
        return this.toughness;
    }

    /**
     * Set the toughness of this unit to the given toughness.
     *
     * @param	toughness
     * 			The new toughness for this unit.
     * @post	If the given toughness is a valid toughness for this unit, the toughness of this new unit is equal to the given toughness.
     * 			Otherwise, if the given toughness is below the lowest possible value for the toughness of a unit, its new toughness is equal to the
     * 			lowest possible value for the toughness of a unit.
     * 			If the given toughness is above the highest possible value for the toughness of a unit, its new toughness is equal to the highest possible value for the
     * 			toughness of a unit.
     *		  | if (isValidStrength(toughness, min, max))
     * 		  |		then new.getToughness() == toughness
     * 		  |		else if (toughness < min)
     * 		  |		then new.getToughness() == min
     * 		  |	 	else if (toughness > max)
     * 		  |	 	then new.getToughness() == max
     */
    @Raw
    public void setToughness(int toughness) {
    	int min = MIN_PROPERTY;
    	int max = MAX_PROPERTY;
    	if (!propAssignedUponCreation) {
    		min = MIN_PROPERTY_UPON_CREATION;
    		max = MAX_PROPERTY_UPON_CREATION;
    	}    
        if (isValidProperty(toughness, min, max))
            this.toughness = toughness;
        else if (toughness < min)
            this.toughness = min;
        else if (toughness > max)
            this.toughness = max;
    }

    /**
     * Return whether the default behavior of this unit is enabled.
     */
    @Basic
    @Raw
    public boolean isDefaultBehaviorEnabled() {
        return this.defaultBehaviorEnabled;
    }

    /**
     * Set the state of default behavior of this unit according to the given flag.
     *
     * @param	flag
     * 			The default behavior state to be registered.
     * @post	The new default behavior state of this unit is equal to the given flag.
     * 		  |	new.isDefaultBehaviorEnabled == flag
     */
    @Raw
    public void setDefaultBehavior(boolean flag) {
        this.defaultBehaviorEnabled = flag;
    }

    /**
     * Check whether this unit can have the given world as its world.
     * 
     * @param	world
     * 			The world to check.
     * @return	True if and only if the given world contains less than the maximum amount of units in the given world.
     * 		  | result == world.getNumberOfUnits() < world.getMaxNbUnits()
     */
    @Override
    protected boolean canHaveAsWorld(World world) {
        return (world.getNbUnits() < world.getMaxNbUnits());
    }

    /**
     * Return the maximum number of hitpoints or stamina points of this unit.
     * 
     * @return	The product of 0,02 and this unit's weight and toughness, rounded up to the next integer.
     * 		  |	result == Math.ceil(200 * (this.getWeight() / 100) * (this.getToughness() / 100))
     */
    public int getMaxPoints() {
        return (int) Math.ceil(200 * ((double) this.getWeight() / 100) * ((double) this.getToughness() / 100));
    }

    /**
     * Check whether the given number of hitpoints or stamina points is a valid number of hitpoints or stamina points for any unit.
     *
     * @param	points
     * 			The number of points to check.
     * @return	True if and only if the given number of points is equal or less than the maximum number of points and positive.
     * 		  | result ==
     * 		  |		points <= getMaxPoints()
     * 		  |		&& points >= 0
     */
    private boolean isValidPoints(int points) {
        return (points <= getMaxPoints() && points >= 0);
    }

    /**
     * Return the number of hitpoints of this unit.
     */
    @Basic
    @Raw
    public int getCurrentHitPoints() {
        return this.hitpoints;
    }

    /**
     * Set the number of hitpoints of this unit to the given hitpoints.
     *
     * @param	hitPoints
     * 			The number of hitpoints for this unit.
     * @pre		The given number of hitpoints must be a valid number of hitpoints for any unit.
     * 		  |	isValidPoints(hitPoints)
     * @post	The number of hitpoints of this unit is equal to the given number of hitpoints.
     * 		  |	new.getCurrentHitPoints() == hitPoints
     */
    public void setCurrentHitPoints(int hitPoints) {
        assert isValidPoints(hitPoints);
        this.hitpoints = hitPoints;
    }

    /**
     * Return the number of stamina points of this unit.
     */
    @Basic
    @Raw
    public int getCurrentStaminaPoints() {
        return this.staminapoints;
    }

    /**
     * Set the number of stamina points of this unit to the given stamina points.
     *
     * @param	stamPoints
     * 			The number of stamina points for this unit.
     * @pre		The given number of stamina points must be a valid number of stamina points for any unit.
     * 		  |	isValidPoints(stamPoints)
     * @post	The number of stamina points of this unit is equal to the given number of stamina points.
     * 		  |	new.getCurrentStamPoints() == stamPoints
     */
    public void setCurrentStaminaPoints(int stamPoints) {
        assert isValidPoints(stamPoints);
        this.staminapoints = stamPoints;
    }

    /**
     * Return the orientation of this unit.
     */
    @Basic
    @Raw
    public float getOrientation() {
        return this.orientation;
    }

    /**
     * Check whether the given orientation is a valid orientation for any unit.
     *
     * @param	orientation
     * 			The orientation to check.
     * @return	True if and only if the orientation is positive and equal or less than the double of PI.
     * 		  |	result ==
     * 		  |		orientation >= 0
     * 		  |		&& orientation <= 2 * Math.PI
     */
    private static boolean isValidOrientation(float orientation) {
        return (orientation >= 0 && orientation <= 2 * Math.PI);
    }

    /**
     * Set the orientation of this unit to the given orientation.
     *
     * @param	orientation
     * 			The new orientation for this unit.
     * @post	If the given orientation is a valid orientation for any unit, the orientation of this new unit is equal to the given orientation.
     * 			Otherwise, the orientation of this new unit is equal to the half of PI.
     * 		  | if (isValidOrientation(orientation))
     * 		  |		then new.getOrientation() == orientation
     * 		  |		else new.getOrientation() == Math.PI / 2
     */
    @Raw
    public void setOrientation(float orientation) {
        if (isValidOrientation(orientation))
            this.orientation = orientation;
        else
            this.orientation = (float) ((Math.PI) / 2);
    }

    /**
     * Return whether the unit is carrying an object.
     */
    @Basic
    @Raw
    public boolean isCarrying() {
        return this.isCarrying;
    }
    
    /**
     * Set the state of carrying of this unit according to the given flag.
     *
     * @param	flag
     * 			The carrying state to be registered.
     * @post	The new carrying state of this unit is equal to the given flag.
     * 		  |	new.isCarrying == flag
     */
    private void setCarrying(boolean flag) {
    	this.isCarrying = flag;
    }
    
    /**
     * Check whether the given object can be carried by this unit.
     *
     * @return	True if and only if this unit is not already carrying an object.
     * 		  |	result == !this.isCarrying()
     */
    private boolean canCarry() {
        return !this.isCarrying();
    }

    /**
     * Return the object carried by this unit.
     */
    @Basic
    @Raw
    public InanimateMovableWorldObject getCarriedObject() {
        return this.carriedObject;
    }
    
    /**
     * Set the object this unit is carrying to the given object.
     *
     * @param	object
     * 			The new object this unit is carrying.
     * @post	The object this unit is carrying is equal to the given object.
     *		  | new.getCarriedObject() == object
     */
    private void setCarriedObject(InanimateMovableWorldObject object) {
    	this.carriedObject = object;
    }

    /**
     * Let the unit carry the given object.
     *
     * @param	objectToBeCarried
     * 			The object which needs to be carried by the unit.
     * @effect	This unit carries the given object.
     * 		  | this.setCarriedObject(objectToBeCarried)
     * @effect	This unit's state of carrying is set to true.
     * 		  | this.setCarrying(true)
     * @effect	The given objectis unregistered.
     * 		  |	objectToBeCarried.unregister()
     * @throws	IllegalArgumentException
     * 			The given object cannot be carried by any unit.
     *		  |	! canBeCarried(getCarriedObject())
     */
    public void carry(InanimateMovableWorldObject objectToBeCarried) throws IllegalArgumentException { 	
        if (!canCarry())
        	throw new IllegalArgumentException("This object cannot be carried.");
        this.setCarriedObject(objectToBeCarried);
        this.setCarrying(true);
        objectToBeCarried.unregister();            
    }

    /**
     * Let the unit drop the given carried object.
     *
     * @param	carriedObject
     * 			The object to drop.
     * @effect	The carried object of this unit is not effective.
     * 		  | this.setCarriedObject(null)
     * @effect	This unit's state of carrying is set to false.
     * 		  | this.setCarrying(false)
     * @effect	The given object's location is set to this unit's location.
     * 		  |	carriedObject.setLocation(locArray)
     */
    public void drop(InanimateMovableWorldObject carriedObject) {
        this.setCarriedObject(null);
        this.setCarrying(false);
        double[] locArray = this.getLocation().getArray();
        carriedObject.setLocation(locArray);
    }

    /**
     * Return whether the unit is sprinting.
     */
    @Basic
    @Raw
    public boolean isSprinting() {
        return this.isSprinting;
    }

    /**
     * Set the state of sprinting of this unit according to the given flag.
     *
     * @param	flag
     * 			The sprinting state to be registered.
     * @post	The new sprinting state of this unit is equal to the given flag.
     * 		  |	new.isSprinting() == flag
     */
    public void setSprinting(boolean flag) {
        this.isSprinting = flag;
    }

    /**
     * Let this unit work at given cube position.
     *
     * @param	targetCube
     * 			The position of the cube to let this unit work at.
     * @effect	This unit's current activity is interrupted by its work activity at the given target cube.
     * 		  |	this.interrupt(work)
     */
    public void work(int[] targetCube) {
        Work work = new Work(this, targetCube);
        this.interrupt(work);
    }

    /**
     * Let this unit attack the given unit.
     *
     * @param	defender
     * 			The unit to attack.
     * @effect	If this unit's faction is different from the given defender's faction, if this unit and the defender are located in the same or a neighboring
     * 			cube and if the defender is not falling, this unit interrupts its current activity with an attack to the defender and the defender conducts
     * 			a defence against this unit.
     * 		  |	if (this.getFaction() != defender.getFaction() && !(hasneighboringX && hasneighboringY && hasneighboringZ) && defender.getActivity().getId() == 6)
     * 		  |		then this.interrupt(new Attack(this, defender))
     * 		  |			 defender.setActivity(new Defend(defender))
     * @throws	IllegalArgumentException
     * 			The attack cannot be conducted.
     */
    public void attack(Unit defender) throws IllegalArgumentException {
        if (this.getFaction() == defender.getFaction())
            throw new IllegalArgumentException("Units belonging to the same faction cannot fight against each other.");
        int ACubeX = this.getLocation().getCubeLocation()[0];
        int ACubeY = this.getLocation().getCubeLocation()[1];
        int ACubeZ = this.getLocation().getCubeLocation()[2];
        int DCubeX = defender.getLocation().getCubeLocation()[0];
        int DCubeY = defender.getLocation().getCubeLocation()[1];
        int DCubeZ = defender.getLocation().getCubeLocation()[2];
        boolean hasneighboringX = false;
        boolean hasneighboringY = false;
        boolean hasneighboringZ = false;
        for (int x = ACubeX - 1; x < ACubeX + 2; x++) {
            if (x == DCubeX)
                hasneighboringX = true;
        }
        for (int y = ACubeY - 1; y < ACubeY + 2; y++) {
            if (y == DCubeY)
                hasneighboringY = true;
        }
        for (int z = ACubeZ - 1; z < ACubeZ + 2; z++) {
            if (z == DCubeZ)
                hasneighboringZ = true;
        }
        if (!(hasneighboringX && hasneighboringY && hasneighboringZ))
            throw new IllegalArgumentException("Units can only attack other Units in the same or a neighboring cube.");
        if (defender.getActivity().getId() == 6)
            throw new IllegalArgumentException("Units cannot attack other Units that are falling.");
        Attack attack = new Attack(this, defender);
        if (!this.interrupt(attack))
            return;
        defender.setActivity(new Defend(defender));
    }

    /**
     * Return the faction of this unit.
     */
    @Basic
    @Raw
    public Faction getFaction() {
        return this.faction;
    }

    /**
     * Check whether this unit can have the given faction as its faction.
     *
     * @param	faction
     * 			The faction to check.
     * @return	True if and only if the given faction contains less than the maximum amount of units in the given faction.
     * 		  |	result == faction.getNumberOfUnits() < faction.getMaxNbUnits()
     */
    boolean canHaveAsFaction(Faction faction) {
        return (faction.getNbUnits() < faction.getMaxNbUnits());
    }

    /**
     * Set the faction of this unit.
     *
     * @post	If the maximum amount of active factions is not reached yet, the faction of this new unit is equal to a newly created faction.
     * 			Otherwise, the faction of this new unit is equal to the faction with the smallest amount of units.
     * 		  |	if this.getWorld().getNbFactions() < this.getWorld().getMaxNbFactions()
     * 		  |		then new.getFaction == new Faction(this, getWorld())
     * 		  |		else new.getFaction == this.getWorld().getSmallestFaction()
     * @throws	IllegalArgumentException
     * 			The faction of this unit's world with the smallest number of units is not a valid faction for this unit.
     *		  |	! canHaveAsFaction(this.getWorld().getSmallestFaction())
     */
    @Raw
    private void setFaction() throws IllegalArgumentException {
        if (this.getWorld().getNbFactions() < this.getWorld().getMaxNbFactions())
            this.faction = new Faction(this, getWorld());
        else {
        	if (!canHaveAsFaction(this.getWorld().getSmallestFaction()))
        		throw new IllegalArgumentException("This smallest faction has already reached its max amount of units.");
        	this.faction = this.getWorld().getSmallestFaction();
            this.getWorld().getSmallestFaction().addUnit(this);
        }
    }

    /**
     * Let this unit deal with its damage.
     *
     * @param	damage
     * 			The damage to deal with.
     * @effect	If the given number of damage points is equal or higher than this unit's hitpoints, this unit dies.
     * 			Otherwise, the given number of damage points is subtracted from this unit's hitpoints.
     * 		  |	if Math.floor(damage) >= this.getCurrentHitPoints()
     * 		  |		then terminate();
     * 		  |		else this.setCurrentHitPoints(this.getCurrentHitPoints() - intDamage)
     */
    public void dealDamage(double damage) {
        int intDamage = (int) Math.floor(damage);
        if (intDamage >= this.getCurrentHitPoints())
            terminate();
        else
            this.setCurrentHitPoints(this.getCurrentHitPoints() - intDamage);
    }

	/**
	 * Return whether this unit is terminated.
	 */
    @Basic
    @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	} 
    
    /**
     * Let this unit die.
     *
     * @post	This unit is terminated.
     * 		  |	new.isTerminated() == true
     * @effect	This unit is unregistered.
     * 		  |	this.unregister()
     * @effect	This unit is removed from its faction.
     * 		  |	this.getFaction().removeUnit(this)
     * @effect	This unit is removed from its world.
     * 		  |	this.getWorld().removeUnit(this)
     */
    private void terminate() {
        this.isTerminated = true;
        this.unregister();
        this.getFaction().removeUnit(this);
        this.getWorld().removeUnit(this);
    }

    /**
     * Return a random name.
     *
     * @return	An element supplied by a list of random String names.
     */
    private String getRandomName() {
    	String[] redneckNameArray = new String[]{"Cletus", "Billy", "Daquan", "Bill", "Uncle Bob", "Minnie", "John", "Harly", "Molly",
    			"Tyrone", "Daisy", "Dale", "Ruby", "Bonnie", "Britney", "Earl", "Jessie", "Moe", "Major Marquis Warren", "Daisy Domergue", "Marco the Mexican",
    			"Chester Charles Smithers", "Gemma", "Chris Mannix", "Sweet Dave", "Billy Crash", "Rodney", "Dicky Speck", "Chicken Charlie", "Django Freeman"};
    	return redneckNameArray[random.nextInt(redneckNameArray.length)];
    }
    
    /**
     * Return the experience points of this unit.
     */
    @Basic
    @Raw
    public int getXP() {
        return this.xp;
    }

    /**
     * Check whether the given number of experience points is a valid number of experience points for any unit.
     *
     * @param	xp
     * 			The number of experience points to check.
     * @return	True if and only if the number of experience points is positive.
     * 		  |	result == xp >= 0
     */
    private static boolean isValidXP(int xp) {
        return (xp >= 0);
    }

    /**
     * Set the number of experience points of this unit to the given number of experience points.
     *
     * @param	xp
     * 			The new number of experience points for this unit.
     * @post	If the given number of experience points is a valid number of experience points for any unit, the number of experience points of this new unit
     * 			is equal to the given number of experience points.
     *		  |	if (isValidXP(xp))
     *		  |		then new.getXP() == xp
     * @effect	The lowest of the strength, agility and toughness attributes is incremented by 1 per 10 experience points this unit has gained.
     * 		  |	if getAgility() < getStrength()
     * 		  |		then if getToughness() < getAgility()
     * 		  |				then setToughness(getToughness() + 1)
     * 		  |				else setAgility(getAgility() + 1)
     * 		  |		else if getToughness() < getStrength()
     * 		  |				then setToughness(getToughness() + 1)
     * 		  |				else setStrength(getStrength() + 1)
     */
    @Raw
    private void setXP(int xp) {
        if (isValidXP(xp))
            this.xp = xp;
        int xpToUse = this.getXP() - this.getXPUsed();
        int propertyPointsToAdd = xpToUse / 10;
        while (propertyPointsToAdd > 0) {
            if (getAgility() < getStrength()) {
                if (getToughness() < getAgility())
                	setToughness(getToughness() + 1);
                else
                	setAgility(getAgility() + 1);
            } else {
            	if (getToughness() < getStrength()) 
            		setToughness(getToughness() + 1);
            	else
            		setStrength(getStrength() + 1);
            }	
            propertyPointsToAdd -= 1;
            this.setXPUsed(this.getXPUsed() + 10);
        }
    }
    
    /**
     * Add the given number of experience points.
     *
     * @param	xp
     * 			The experience points to add.
     * @effect	The number of experience points of this unit is incremented by the given number of experience points.
     * 		  |	this.setXP(this.getXP() + xp)
     */
    public void addXP(int xp) {
        this.setXP(this.getXP() + xp);
    }

    /**
     * Return the number of experience points of this unit that have already been used to increase its strength, agility or toughness.
     */
    @Basic
    @Raw
    private int getXPUsed() {
    	return this.xpused;
    }
    
    /**
     * Check whether the given number of experience points of this unit that have already been used to increase its strength, agility or toughness
     * is a valid number of used experience points for any unit.
     *
     * @param	xpused
     * 			The number of used experience points to check.
     * @return	True if and only if the number of used experience points is divisible by 10.
     * 		  |	result == (xpused / 10 == 0)
     */
    private static boolean isValidXPUsed(int xpused) {
    	return (xpused / 10 == 0);
    }
    
    /**
     * Set the number of experience points of this unit that have already been used to increase its strength, agility or toughness to the given number of used
     * experience points.
     *
     * @param	xpused
     * 			The new number of used experience points for this unit.
     * @effect	If the given number of used experience points is a valid number of used experience points for any unit, the number of used experience points
     * 			of this new unit is equal to the given number of used experience points.
     *		  |	if (isValidXPUsed(xpused))
     *		  |		then new.getXPUsed() == xpused
     */
    private void setXPUsed(int xpused) {
    	if (isValidXPUsed(xpused))
    		this.xpused = xpused;
    }

    /**
     * Let this unit move to the given cube location.
     *
     * @param	destination
     * 			The cube location to let this unit move to.
     * @effect	If this unit's world can have the given destination cube as a cube location, this unit's current activity is interrupted by the movement to
     * 			the given destination.
     *		  |	if (this.getWorld().canHaveAsCubeLocation(destination, this))
     *		  |		then this.interrupt(new Movement(this, destination))
     */
    public void moveTo(int[] destination) {
        if (!this.getWorld().canHaveAsCubeLocation(destination, this))
            return;
        Movement movement = new Movement(this, destination);
        interrupt(movement);
    }

    /**
     * Let this unit move to a location in an adjacent cube.
     *
     * @param	dx
     * 			The movement along the x axis to do.
     * @param	dy
     * 			The movement along the y axis to do.
     * @param	dz
     * 			The movement along the z axis to do.
     * @effect	If this unit is not already moving, it moves to a position in an adjacent cube, calculated as the addition of given dx, dy and dz and
     * 			the current position coordinates.
     * 		  |	if (! this.getActivity().getId() == 3)
     * 		  |		then this.moveTo(destination)
     * @throws	IllegalLocation
     * 			The intended movement is not a movement to an adjacent cube.
     */
    public void moveToAdjacent(int dx, int dy, int dz) throws IllegalLocation {
        if (Math.abs(dx) > 1 || Math.abs(dy) > 1 || Math.abs(dz) > 1)
            throw new IllegalLocation("Illegal move to adjacent destination");
        if (this.getActivity().getId() == 3)
            return;
        int[] destination = this.getLocation().getCubeLocation();
        destination[0] = destination[0] + dx;
        destination[1] = destination[1] + dy;
        destination[2] = destination[2] + dz;
        this.moveTo(destination);
    }

    /**
     * Let this unit rest.
     * 
     * @effect	This unit's current activity is interrupted by resting.
     * 		  |	this.interrupt(new Rest(this))
     */
    public void rest() {
        Rest rest = new Rest(this);
        interrupt(rest);
    }
    
    /**
     * Return whether this unit has a task.
     */
    @Basic
    @Raw
    public boolean hasTask() {
    	return this.hasTask;
    }
    
    /**
     * Return the task assigned to this unit.
     */
    @Basic
    @Raw
    public Task getTask() {
    	return this.task;
    }
    
    /**
     * Check whether the given task is a valid task for any unit.
     *  
     * @param	task
     *			The task to check.
     * @return	Always true. The task validity is checked upon creation.
     *		  |	result == true
     */
    public static boolean isValidTask(Task task) {
    	return ProgramExecutor.checkBreakLegality(task);
    }
    
    /**
     * Set the task of this unit to the given task.
     * 
     * @param	task
     *			The new task for this unit.
     * @post	The task of this new unit is equal to the given task.
     *		  |	new.getTask() == task
     * @throws	IllegalArgumentException
     *			The given task is not a valid task for any unit.
     *		  |	! isValidTask(getTask())
     */
    public void setTask(Task task) throws IllegalArgumentException {
    	if (!isValidTask(task))
    		throw new IllegalArgumentException("Invalid task");
    	this.task = task;
        this.hasTask = (task != null);
    }
    /**
     * Return whether the unit is idle.
     * 
     * @return	True if and only if this unit's default behavior is enabled and if it's not conducting any activity.
     *		  |	result ==
     *		  |     !this.hasTask()
     *		  |		&& this.isDefaultBehaviorEnabled()
     *		  |		&& this.getActivity().getId() == 0
     */
    public boolean isIdle() {
        return !this.hasTask() && this.isDefaultBehaviorEnabled() && (this.getActivity().getId() == 0);
    }
    
    /**
     * Return whether this unit has a paused activity.
     */
    @Basic
    @Raw
    public boolean hasPausedActivity() {
    	return this.hasPausedActivity;
    }
    
    /**
     * Return the paused activity of this unit.
     */
    @Basic
    @Raw
    public IActivity getPausedActivity() {
    	return this.pausedActivity;
    }
    
    /**
     * Check whether the given activity is a valid activity for any unit.
     *
     * @param	activity
     * 			The activity to check.
     * @return	Always true.
     * 		  |	result == true
     */
    @Override
    public boolean isValidActivity(IActivity activity) {
        return true;
    }
    
    /**
     * Set the paused activity of this unit to the given activity.
     * 
     * @param	activity
     *			The new paused activity for this unit.
     * @post	The paused activity of this new unit is equal to the given activity.
     *		  |	new.getPausedActivity() == activity
     * @throws	IllegalArgumentException
     *			The given activity is not a valid activity for any unit.
     *		  |	! isValidActivity(getPausedActivity())
     */
    private void setPausedActivity(IActivity activity) throws IllegalArgumentException {
    	if (!isValidActivity(activity))
    		throw new IllegalArgumentException("Invalid activity");
    	this.pausedActivity = activity;
        this.hasPausedActivity = !(activity instanceof NoActivity);
    }

    /**
     * Check whether this unit's current activity can be interrupted by the given new activity.
     *
     * @param	newActivity
     * 			The new activity for this unit.
     * @effect	If the new activity is a defense, if the current activity is a movement or work and if this unit's activity can be interrupted by the new activity,
     *          this unit's paused activity is set to its current activity.
     * 		  |	if (newActivity.getId() == 2 && (this.getActivity().getId() == 3 || this.getActivity().getId() == 4)
     * 		  |		&& this.getActivity().canBeInterruptedBy(newActivity))
     * 		  |		then this.setPausedActivity(this.getActivity())
     * @effect	If this unit's activity can be interrupted by the new activity, this unit's current activity is set to the given new activity.
     * 		  |	this.setActivity(newActivity)
     * @return	True if and only if this unit's current activity can be interrupted by the given new activity.
     * 		  |	result == this.getActivity().canBeInterruptedBy(newActivity)
     */
    private boolean interrupt(IActivity newActivity) {
        if (!this.getActivity().canBeInterruptedBy(newActivity))
            return false;
        if (newActivity.getId() == 2 && (this.getActivity().getId() == 3 || this.getActivity().getId() == 4))
        	this.setPausedActivity(this.getActivity()); 
        this.setActivity(newActivity);
        this.task.stopExecution();
        return true;
    }
    
    /**
     * Let this unit finish its current activity.
     *
     * @effect	If this unit has a paused activity, this unit's current activity is set to its paused activity and its paused activity is set to none.
     * 		  |	if (this.hasPausedActivity())
     * 		  |		then this.setActivity(this.getPausedActivity())
     * 		  |			 this.setPausedActivity(new NoActivity())
     * @effect	If this unit doesn't have a paused activity, its current activity is set to none.
     * 		  |	if (! this.hasPausedActivity())
     * 		  |		then this.setActivity(new NoActivity())
     */
    public void activityFinished() {
        if (this.hasPausedActivity()) {
            this.setActivity(this.getPausedActivity());
            this.setPausedActivity(new NoActivity());
        } else
            this.setActivity(new NoActivity());
    }
}
