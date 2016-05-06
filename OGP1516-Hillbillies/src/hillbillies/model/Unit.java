package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.*;
import hillbillies.model.exceptions.IllegalLocation;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;

import java.util.Random;

/**
 * A class of hillbilly units involving a name, an x, y and z coordinate, a weight, agility, strength, toughness,
 * a facility to enable the default behaviour and a world.
 * 
 * @invar  The name of each unit must be a valid name for any unit.
 *       | isValidName(getName())
 * @invar  The location of each unit must be a valid location for any unit.
 *       | isValidLocation(getLocation)
 * @invar  The weight of each unit must be a valid weight for any unit.
 *       | isValidWeight(getWeight())
 * @invar  The strength of each unit must be a valid strength for any unit.
 *       | isValidProperty(getStrength())
 * @invar  The agility of each unit must be a valid agility for any unit.
 *       | isValidProperty(getAgility()) 
 * @invar  The toughness of each unit must be a valid toughness for any unit.
 *       | isValidProperty(getToughness()) 
 * @invar  The number of hitpoints of each unit must be a valid number of hitpoints for any unit.
 * 		 | isValidPoints(getCurrentHitPoints())
 * @invar  The number of stamina points of each unit must be a valid number of stamina points for any unit.
 * 		 | isValidPoints(getCurrentStaminaPoints())
 * @invar  The orientation of each unit must be a valid orientation for any unit.
 * 		 | isValidOrientation(getOrientation())
 *        
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Unit extends MovableWorldObject {

    /**
     * Initialize this new hillbilly unit with given name, given initial x, y and z
     * coordinate, given weight, given agility, given strength, given toughness,
     * given default behaviour state and given world.
     *
     * @param name                  The name for this unit.
     * @param x                     The x coordinate for this unit.
     * @param y                     The y coordinate for this unit.
     * @param z                     The z coordinate for this unit.
     * @param weight                The weight for this unit.
     * @param strength              The strength for this unit.
     * @param agility               The agility for this unit.
     * @param toughness             The toughness for this unit.
     * @param enableDefaultBehavior The state of behaviour for this unit.
     * @param world                 The world for this unit.
     * @effect The name of this new unit is set to the given name.
     * | this.setName(name)
     * @effect The x coordinate of this new unit is set to the given x coordinate.
     * | this.setLocation(x, y, z)
     * @effect The y coordinate of this new unit is set to the given y coordinate.
     * | this.setLocation(x, y, z)
     * @effect The z coordinate of this new unit is set to the given z coordinate.
     * | this.setLocation(x, y, z)
     * @post If the given weight is not below 25 and not above 100 for any unit, the weight of this new unit is
     * equal to the given weight. Otherwise, the weight of this new unit is equal to 25, respectively 100.
     * | if (weight < 25)
     * |   then new.getWeight == 25
     * |   else if (weight > 100)
     * |	 then new.getWeight == 100
     * |   else new.getWeight == weight
     * @post If the given strength is not below 25 and not above 100 for any unit, the strength of this new unit is
     * equal to the given strength. Otherwise, the strength of this new unit is equal to 25, respectively 100.
     * | if (strength < 25)
     * |   then new.getStrength == 25
     * |   else if (strength > 100)
     * |	 then new.getStrength == 100
     * |   else new.getStrength == strength
     * @post If the given agility is not below 25 and not above 100 for any unit, the agility of this new unit is
     * equal to the given agility. Otherwise, the agility of this new unit is equal to 25, respectively 100.
     * | if (agility < 25)
     * |   then new.getAgility == 25
     * |   else if (agility > 100)
     * |	 then new.getAgility == 100
     * |   else new.getAgility == agility
     * @post If the given toughness is not below 25 and not above 100 for any unit, the toughness of this new unit is
     * equal to the given toughness. Otherwise, the toughness of this new unit is equal to 25, respectively 100.
     * | if (toughness < 25)
     * |   then new.getToughness == 25
     * |   else if (toughness > 100)
     * |	 then new.getToughness == 100
     * |   else new.getToughness == toughness
     * @post The initial state of behavior of this new unit is set according to the given flag.
     * | new.defaultbehaviorenabled == enableDefaultBehavior
     * @effect The world of this new unit is set to the given world.
     * | this.setWorld(world)
     */
    public Unit(String name, double x, double y, double z, int weight, int strength, int agility, int toughness,
                boolean enableDefaultBehavior, World world) throws IllegalLocation, IllegalArgumentException {
        this.setName(name);
        this.setAgility(agility);
        this.setStrength(strength);
        this.setToughness(toughness);
        this.setWeight(weight);
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setLocation(x, y, z);
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(new NoActivity());
        this.setFaction();
    }

    /**
     * Initialize this new hillbilly unit with given name, given initial x, y and z coordinate, given weight,
     * given agility, given strength, given toughness and given default behaviour state.
     *
     * @param name                  The name for this unit.
     * @param x                     The x coordinate for this unit.
     * @param y                     The y coordinate for this unit.
     * @param z                     The z coordinate for this unit.
     * @param weight                The weight for this unit.
     * @param strength              The strength for this unit.
     * @param agility               The agility for this unit.
     * @param toughness             The toughness for this unit.
     * @param enableDefaultBehavior The state of behaviour for this unit.
     * @effect The name of this new unit is set to the given name.
     * | this.setName(name)
     * @effect The x coordinate of this new unit is set to the given x coordinate.
     * | this.setLocation(x, y, z)
     * @effect The y coordinate of this new unit is set to the given y coordinate.
     * | this.setLocation(x, y, z)
     * @effect The z coordinate of this new unit is set to the given z coordinate.
     * | this.setLocation(x, y, z)
     * @post If the given weight is not below 25 and not above 100 for any unit, the weight of this new unit is
     * equal to the given weight. Otherwise, the weight of this new unit is equal to 25, respectively 100.
     * | if (weight < 25)
     * |   then new.getWeight == 25
     * |   else if (weight > 100)
     * |	 then new.getWeight == 100
     * |   else new.getWeight == weight
     * @post If the given strength is not below 25 and not above 100 for any unit, the strength of this new unit is
     * equal to the given strength. Otherwise, the strength of this new unit is equal to 25, respectively 100.
     * | if (strength < 25)
     * |   then new.getStrength == 25
     * |   else if (strength > 100)
     * |	 then new.getStrength == 100
     * |   else new.getStrength == strength
     * @post If the given agility is not below 25 and not above 100 for any unit, the agility of this new unit is
     * equal to the given agility. Otherwise, the agility of this new unit is equal to 25, respectively 100.
     * | if (agility < 25)
     * |   then new.getAgility == 25
     * |   else if (agility > 100)
     * |	 then new.getAgility == 100
     * |   else new.getAgility == agility
     * @post If the given toughness is not below 25 and not above 100 for any unit, the toughness of this new unit is
     * equal to the given toughness. Otherwise, the toughness of this new unit is equal to 25, respectively 100.
     * | if (toughness < 25)
     * |   then new.getToughness == 25
     * |   else if (toughness > 100)
     * |	 then new.getToughness == 100
     * |   else new.getToughness == toughness
     * @post The initial state of behavior of this new unit is set according to the given flag.
     * | new.defaultbehaviorenabled == enableDefaultBehavior
     */
    public Unit(String name, double x, double y, double z, int weight, int strength, int agility, int toughness,
                boolean enableDefaultBehavior) throws IllegalLocation, IllegalArgumentException {
        this.setName(name);
        this.setAgility(agility);
        this.setStrength(strength);
        this.setToughness(toughness);
        this.setWeight(weight);
        this.setDefaultBehavior(enableDefaultBehavior);
        int extent = (int) Math.ceil(x);
        if (y > extent)
            extent = (int) Math.ceil(y);
        if (z > extent)
            extent = (int) Math.ceil(z);
        int[][][] randomCubeWorld = new int[extent][extent][extent];
        TerrainChangeListener randomTerrainChangeListener = new DefaultTerrainChangeListener();
        World world = new World(randomCubeWorld, randomTerrainChangeListener);
        this.setWorld(world);
        this.setLocation(x, y, z);
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(new NoActivity());
        this.setFaction();
    }

    /**
     * Initialize this new hillbilly unit with given world and given default behaviour state.
     *
     * @param world                 The world for this unit.
     * @param enableDefaultBehavior The state of behaviour for this unit.
     * @post The initial state of behavior of this new unit is set according to the given flag.
     * | new.defaultbehaviorenabled == enableDefaultBehavior
     * @effect The world of this new unit is set to the given world.
     * | this.setWorld(world)
     */
    public Unit(World world, boolean enableDefaultBehavior) {
        Random random = new Random();
        String name = getRandomName();
        int randomLocX;
        int randomLocY;
        int randomLocZ;
        while (true) {
            randomLocX = random.nextInt(world.sideSize);
            randomLocY = random.nextInt(world.sideSize);
            randomLocZ = random.nextInt(world.sideSize);
            int[] randomLoc = new int[]{randomLocX, randomLocY, randomLocZ};
            if (world.canHaveAsCubeLocation(randomLoc, this))
                break;
        }
        int randomWeight = random.nextInt(76) + 25;
        int randomStrength = random.nextInt(76) + 25;
        int randomAgility = random.nextInt(76) + 25;
        int randomToughness = random.nextInt(76) + 25;
        this.setAgility(randomAgility);
        this.setStrength(randomStrength);
        this.setToughness(randomToughness);
        this.setWeight(randomWeight);
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setLocation(randomLocX, randomLocY, randomLocZ);
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(new NoActivity());
        this.setFaction();
        this.setName(name);
    }

    /**
     * Variable registering the time since this unit last rested
     */
    public double timeSinceLastRest;

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
     * Variable registering the state of default behavior of this unit.
     */
    private boolean defaultBehaviorEnabled;

    /**
     * Variable registering the hitpoints of this unit.
     */
    private int hitpoints;

    /**
     * Variable registering the stamina points of this unit.
     */
    private int staminapoints;

    /**
     * Variable registering the experience points of this unit.
     */
    private int xp = 0;

    /**
     * Variable registering the number of experience points of this unit that have already been used to increase
     * its strength, agility or toughness.
     */
    private int xpused = 0;

    /**
     * Variable registering the orientation of this unit.
     */
    private float orientation;

    /**
     * Variable registering the activity of this unit which is paused but will be continued after this unit
     * finishes its current activity.
     */
    private IActivity pausedActivity;

    /**
     * Variable registering whether this unit has a paused activity or not.
     */
    private boolean hasPausedActivity;

    /**
     * Variable registering whether this unit is carrying an object or not.
     */
    private boolean isCarrying = false;

    /**
     * Variable registering the object this unit is carrying.
     */
    private InanimateMovableWorldObject carriedObject;

    /**
     * Variable registering whether this unit is sprinting or not.
     */
    private boolean isSprinting = false;

    /**
     * Variable registering the faction this unit belongs to.
     */
    private Faction faction;
    
    /**
     * Object holding the random generator used during the random cration of the unit.
     */
    private Random random;

    /**
     * Variable registering whether this unit is alive or not.
     */
    private boolean isalive;
    
    /**
     * Variable registering the task assigned to this unit.
     */
    private Task task;
    
    /**
     * Variable registering whether a task is assigned to this unit.
     */
    private boolean hasTask = false;
    
    /**
     * Variable registering whether an initial weight still needs to be assigned to this unit.
     */
    private boolean weightUponCreation = true;
    
    /**
     * Variable registering whether an initial agility still needs to be assigned to this unit.
     */
    private boolean agilityUponCreation = true;
    
    /**
     * Variable registering whether an initial strength still needs to be assigned to this unit.
     */
    private boolean strengthUponCreation = true;
    
    /**
     * Variable registering whether an initial toughness still needs to be assigned to this unit.
     */
    private boolean toughnessUponCreation = true;
    
	/**
	 * Constant reflecting the lowest possible value for the weight, strength, agility and toughness properties of a unit.
	 * 
	 * @return The lowest possible value for the weight, strength, agility and toughness properties of all units is 1.
	 *       | result == 1
	 */
	public static final int MIN_PROPERTY = 1;
	
	/**
	 * Constant reflecting the highest possible value for the weight, strength, agility and toughness properties of a unit.
	 * 
	 * @return The highest possible value for the weight, strength, agility and toughness properties of all units is 200.
	 *       | result == 200
	 */
	public static final int MAX_PROPERTY = 200;
	
	/**
	 * Constant reflecting the lowest possible value for the weight, strength, agility and toughness properties of a unit upon creation of the unit.
	 * 
	 * @return The lowest possible value for the weight, strength, agility and toughness properties of all units is 25 upon creation of the units.
	 *       | result == 25
	 */
	public static final int MIN_PROPERTY_UPON_CREATION = 25;
	
	/**
	 * Constant reflecting the highest possible value for the weight, strength, agility and toughness properties of a unit upon creation of the unit.
	 * 
	 * @return The highest possible value for the weight, strength, agility and toughness properties of all units is 100 upon creation of the units.
	 *       | result == 100
	 */
	public static final int MAX_PROPERTY_UPON_CREATION = 100;
    
    /**
     * Return the name of this unit.
     */
    @Basic
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
     * 		  |	new.getName == name
     * @throws	IllegalArgumentException()
     * 			The given initial name is not a valid name for any unit.
     *        | ! isValidName(getName)
     */
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
     */
    @Basic
    @Override
    public int getWeight() {
        if (isCarrying())
            return this.weight + carriedObject.getWeight();
        return this.weight;
    }
    
    /**
     * Return the minimum weight of this unit.
     */
    public int getMinWeight() {
        return (int) 0.5 * (this.getStrength() + this.getAgility());
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
     *		  | if (isValidProperty(weight, getMinWeight(), max))
     * 		  |		then new.getWeight() == weight
     * 		  |		else if (weight < getMinWeight())
     * 		  |		then new.getWeight() == getMinWeight()
     * 		  |	 	else if (weight > max)
     * 		  |	 	then new.getWeight() == max
     */
    public void setWeight(int weight) {
    	int max = MAX_PROPERTY;
    	if (weightUponCreation) {
    		max = MAX_PROPERTY_UPON_CREATION;
    		weightUponCreation = false;
    	}    
        if (isValidProperty(weight, getMinWeight(), max))
            this.weight = weight;
        if (weight < getMinWeight())
            this.weight = getMinWeight();
        else if (weight > max)
            this.weight = max;
    }

    /**
     * Return the strength of this unit.
     */
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
    public void setStrength(int strength) {
    	int min = MIN_PROPERTY;
    	int max = MAX_PROPERTY;
    	if (strengthUponCreation) {
    		min = MIN_PROPERTY_UPON_CREATION;
    		max = MAX_PROPERTY_UPON_CREATION;
    		strengthUponCreation = false;
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
    public void setAgility(int agility) {
    	int min = MIN_PROPERTY;
    	int max = MAX_PROPERTY;
    	if (agilityUponCreation) {
    		min = MIN_PROPERTY_UPON_CREATION;
    		max = MAX_PROPERTY_UPON_CREATION;
    		agilityUponCreation = false;
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
    public void setToughness(int toughness) {
    	int min = MIN_PROPERTY;
    	int max = MAX_PROPERTY;
    	if (toughnessUponCreation) {
    		min = MIN_PROPERTY_UPON_CREATION;
    		max = MAX_PROPERTY_UPON_CREATION;
    		toughnessUponCreation = false;
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
    public boolean isDefaultBehaviorEnabled() {
        return this.defaultBehaviorEnabled;
    }

    /**
     * Set the state of default behavior of this unit according to the given flag.
     *
     * @param	flag
     * 			The default behavior state to be registered.
     * @post	The new default behavior state of this unit is equal to the given flag.
     * 		  |	this.defaultbehaviorenabled == flag
     */
    public void setDefaultBehavior(boolean flag) {
        this.defaultBehaviorEnabled = flag;
    }

    /**
     * Check whether this unit can have the given world as its world.
     */
    @Override
    protected boolean canHaveAsWorld(World world) {
        return (world.getNumberOfUnits() < 100);
    }

    /**
     * Return the maximum number of hitpoints or stamina points of this unit.
     */
    public int getMaxPoints() {
        return (int) Math.ceil(200 * ((double) this.getWeight() / 100) * ((double) this.getToughness() / 100));
    }

    /**
     * Check whether the given number of hitpoints or stamina points is a valid number of hitpoints or stamina points for any unit.
     *
     * @param	points
     * 			The number of points to check.
     * @return	True if and only if the given number of points is equal or less than the maximum number of points
     * 			and equal or higher than zero.
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
    public float getOrientation() {
        return this.orientation;
    }

    /**
     * Check whether the given orientation is a valid orientation for any unit.
     *
     * @param	orientation
     * 			The orientation to check.
     * @return	True if and only if the orientation is equal or higher than zero and equal or less than 2*PI.
     * 		  |	result == orientation >= 0
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
     * 			Otherwise, the orientation of this new unit is equal to Math.PI / 2.
     * 		  | if (isValidOrientation(orientation))
     * 		  |		then new.getOrientation() == orientation
     * 		  |		else new.getOrientation() == Math.PI / 2
     */
    @Raw
    public void setOrientation(float orientation) {
        if (isValidOrientation(orientation))
            this.orientation = orientation;
        else
            this.orientation = ((float) Math.PI) / 2;
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
     * Return whether the unit is carrying an object.
     */
    public boolean isCarrying() {
        return isCarrying;
    }

    /**
     * Return the object carried by this unit.
     */
    public InanimateMovableWorldObject getCarriedObject() {
        return this.carriedObject;
    }

    /**
     * Let the unit carry the given object.
     *
     * @param	objectToBeCarried
     * 			The object which needs to be carried by the unit.
     * @post	The carried object of this unit is equal to the given object and the carrying state of this unit is set to true.
     * @effect	The given object is unregistered.
     * @throws	IllegalArgumentException
     * 			The given object cannot be carried by any unit.
     *		  |	! canBeCarried(getCarriedObject())
     */
    public void carry(InanimateMovableWorldObject objectToBeCarried) throws IllegalArgumentException {
        if (canBeCarried(objectToBeCarried)) {
            this.carriedObject = objectToBeCarried;
            this.isCarrying = true;
            objectToBeCarried.unregister();
        } else
            throw new IllegalArgumentException("This object cannot be carried.");
    }

    /**
     * Check whether the given object can be carried by this unit.
     *
     * @param	object
     * 			The object to check.
     * @return	True if and only if this unit is not already carrying an object.
     */
    private boolean canBeCarried(InanimateMovableWorldObject object) {
        return !this.isCarrying();
    }

    /**
     * Let the unit drop the given carried object.
     *
     * @param	carriedObject
     * 			The object to drop.
     * @post	The carried object of this unit is not effective and the carrying state of this unit is set to false.
     * @effect	The given object's location is set to this unit's location.
     */
    public void drop(InanimateMovableWorldObject carriedObject) {
        this.carriedObject = null;
        this.isCarrying = false;
        double[] locArray = this.getLocation().getArray();
        carriedObject.setLocation(locArray);
    }

    /**
     * Return whether the unit is sprinting or not.
     */
    public boolean isSprinting() {
        return this.isSprinting;
    }

    /**
     * Set the state of sprinting of this unit according to the given flag.
     *
     * @param	flag
     * 			The sprinting state to be registered.
     * @post	The new sprinting state of this unit is equal to the given flag.
     * 		  |	new.isSprinting == flag
     */
    public void setSprinting(boolean flag) {
        this.isSprinting = flag;
    }

    @Override
    public void advanceTime(double dt) {
        this.timeSinceLastRest = this.timeSinceLastRest + dt;
        if (this.timeSinceLastRest >= 300) {
            boolean flag = this.interruptCurrentAct(new Rest(this));
            if (flag) this.timeSinceLastRest = 0;
        }
        if (this.getActivity().getId() == 0 && isDefaultBehaviorEnabled())
            behaveDefault();
        this.getActivity().advanceActivityTime(dt);
    }

    /**
     * Let this unit conduct its default behavior.
     */
    private void behaveDefault() {
        this.setActivity(new Rest(this));
        //TODO flesh out this method
    }

    /**
     * Check whether this unit's current activity can be interrupted by the given new activity.
     *
     * @param	newActivity
     * 			The new activity for this unit.
     * @return	True if and only if this unit's current activity can be interrupted by the given new activity.
     */
    private boolean interruptCurrentAct(IActivity newActivity) {
        if (!this.getActivity().canBeInterruptedBy(newActivity))
            return false;
        if (newActivity.getId() == 2 && (this.getActivity().getId() == 3 || this.getActivity().getId() == 4)) {
            this.hasPausedActivity = true;
            this.pausedActivity = this.getActivity();
        }
        this.setActivity(newActivity);
        return true;
    }

    /**
     * Let this unit finish its current activity.
     *
     * @effect	If this unit has a paused activity, the paused activity is resumed, this unit doesn't have a paused activity anymore, this unit's paused activity
     * 			is not effective.
     * 			If this unit doesn't have a paused activity, its current activity is set to none.
     */
    public void activityFinished() {
        if (this.hasPausedActivity) {
            this.setActivity(this.pausedActivity);
            this.hasPausedActivity = false;
            this.pausedActivity = null;
        } else {
            this.setActivity(new NoActivity());
        }
    }

    /**
     * Let this unit conduct a generic labor at a specified cube position.
     *
     * @param	targetCube
     * 			The position of the cube to let this unit work at.
     * @effect	The unit conducts work at the given target cube.
     */
    public void work(int[] targetCube) {
        Work work = new Work(this, targetCube);
        if (!interruptCurrentAct(work))
            return;
        this.setActivity(work);
    }

    /**
     * Let this unit attack the given unit.
     *
     * @param defender The unit to attack.
     * @throws IllegalArgumentException The attack cannot be conducted.
     * @effect The unit conducts an attack against the defender and the defender conducts a defence against this unit.
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
        if (!this.interruptCurrentAct(attack))
            return;
        this.setActivity(attack);
        defender.setActivity(new Defend(defender, this));
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
     * Check whether the given faction is a valid faction for any unit.
     *
     * @param faction The faction to check.
     * @return True if and only if the given faction contains less than 50 Units.
     * | result == (faction.getNumberOfUnits() < 50)
     */
    boolean canHaveAsFaction(Faction faction) {
        return (faction.getNumberOfUnits() < 50);
    }

    /**
     * Set the faction of this unit.
     *
     * @throws IllegalArgumentException The faction with the smallest number of Units is not a valid faction for any unit.
     *                                  | ! canHaveAsFaction(getFaction())
     * @effect The faction of this new unit is equal to a newly created faction if the maximum number of active
     * factions is not reached yet, or is equal to the faction with the smallest number of Units if the
     * maximum number of active factions has already been reached.
     */
    @Raw
    private void setFaction() throws IllegalArgumentException {
        if (this.getWorld().getNumberOfFactions() < 5) {
            this.faction = new Faction(this, getWorld());
        } else if (!canHaveAsFaction(this.getWorld().getSmallestFaction())) {
            throw new IllegalArgumentException("This faction has already reached its max amount of Units.");
        } else {
            this.faction = this.getWorld().getSmallestFaction();
            this.getWorld().getSmallestFaction().addUnit(this);
        }
    }

    /**
     * Let this unit deal with its damage.
     *
     * @param damage The damage this unit is dealing with.
     * @effect If the given number of damage points is equal or higher than this unit's hitpoints, the unit will die.
     * Otherwise, the given number of damage points will be subtracted from this unit's hitpoints.
     */
    public void dealDamage(double damage) {
        int intDamage = (int) Math.floor(damage);
        if (intDamage >= this.getCurrentHitPoints())
            die();
        else
            this.setCurrentHitPoints(this.getCurrentHitPoints() - intDamage);
    }

    /**
     * Let this unit die.
     *
     * @effect This unit is unregistered, removed from its world and removed from its faction.
     */
    private void die() {
        this.setAlive(false);
        this.unregister();
        this.getWorld().removeUnit(this);
        this.getFaction().removeUnit(this);
    }

    /**
     * Return a random name.
     *
     * @return An element supplied by a list of random names.
     */
    private String getRandomName() {
        boolean flag = random.nextBoolean();
        if (flag) {
            String[] redneckNameArr = new String[]{"Cletus", "Billy", "Daquan", "Bill", "Uncle Bob", "Minnie", "John", "Harly", "Molly",
                    "Tyrone", "Daisy", "Dale", "Ruby", "Bonnie", "Britney", "Earl", "Jessie", "Moe", "Major Marquis Warren", "Daisy Domergue", "Marco the Mexican"
                    , "Chester Charles Smithers", "Gemma", "Chris Mannix", "Sweet Dave", "Billy Crash", "Rodney", "Dicky Speck", "Chicken Charlie", "Django Freeman"};
            return redneckNameArr[random.nextInt(redneckNameArr.length)];
        }
        int index = random.nextInt(51);
        String[] namearr = new String[]{"Alfonso Addie", "Terrence Truluck", "Russel Rouse", "Fritz Forst", "Mckinley Marrow",
                "Sidney Suttles", "Todd Tamura", "Lee Lassiter", "Sonny Sumpter", "Tony Thames", "Phil Pittman", "Jonathon Jenning",
                "Clifford Conerly", "Tod Tegeler", "Lindsay Loken", "Bud Bateman", "Bradly Bedgood", "Reed Rentas", "Bernie Balch",
                "Ezra Eason", "Erin Enriguez", "Luther Lines", "Samuel Stromberg", "Enrique Esqueda", "Paul Plowden", "Francesco Fitton",
                "Moses Mcmurry", "Mickey Mccoin", "Romeo Risher", "Maxwell Melder", "Roman Riddell", "Anthony Aycock", "Anton Aquilino",
                "Gale Gerhardt", "Oren Ogawa", "Hershel Hyslop", "Jared Jiminez", "Hassan Haubrich", "Armando Arner", "Graham Glotfelty",
                "Monroe Martineau", "Maynard Milani", "Galen Gavin", "Boyd Basta", "Tyree Threatt", "Florentino Forand", "King Krom",
                "Ferdinand Fikes", "Gary Gander", "Hans Harnish", "Julie Allard", "Arthur Decloedt"};
        return namearr[index];
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
     * @param xp The number of experience points to check.
     * @return True if and only if the number of experience points is equal or higher than zero.
     * | result == (xp >= 0)
     */
    private static boolean isValidXP(int xp) {
        return (xp >= 0);
    }

    /**
     * Set the number of experience points of this unit to the given number of experience points.
     *
     * @param xp The new number of experience points for this unit.
     * @effect If the given number of experience points is a valid number of experience points for any unit,
     * the number of experience points of this new unit is equal to the given number of experience points.
     * | if (isValidXP(sp))
     * |   then new.getXP() == sp
     */
    @Raw
    private void setXP(int xp) {
        if (isValidXP(xp))
            this.xp = xp;
        int xpToUse = this.getXP() - xpused;
        int propertyPoints = xpToUse / 10;
        while (propertyPoints > 0) {
            if (getAgility() < getStrength()) {
                setAgility(getAgility() + 1);
            } else if (getToughness() < getAgility()) {
                setAgility(getAgility() - 1);
                setToughness(getToughness() + 1);
            } else
                setStrength(getStrength() + 1);
            propertyPoints -= 1;
            this.xpused += 10;
        }
    }

    /**
     * Add the given number of experience points.
     *
     * @param xp The number of experience points to add.
     * @effect The given number of experience points is added to the current amount of experience points of this unit.
     */
    public void addXP(int xp) {
        this.setXP(this.getXP() + xp);
    }

    /**
     * Let this unit conduct a movement to the given location.
     *
     * @param destination The location to let this unit move to.
     * @effect This unit conducts a movement to the given location and this unit's current activity is interrupted
     * by the movement.
     */
    public void moveTo(int[] destination) {
        if (!this.getWorld().canHaveAsCubeLocation(destination, this))
            return;
        Movement movement = new Movement(this, destination);
        interruptCurrentAct(movement);
    }

    /**
     * Let this unit move to a location in an adjacent cube.
     *
     * @param dx The movement along the x axis to make.
     * @param dy The movement along the y axis to make.
     * @param dz The movement along the z axis to make.
     * @throws IllegalLocation The intended movement is not a movement to an adjacent cube.
     * @effect This unit moves to the adjacent cube, referred to by given dx, dy and dz.
     */
    public void moveToAdjacent(int dx, int dy, int dz) throws IllegalLocation {
        if (Math.abs(dx) > 1 || Math.abs(dy) > 1 || Math.abs(dz) > 1)
            throw new IllegalLocation("Illegal move to adjacent destination");
        if (this.getActivity().getId() == 3)
            return;
        int[] loc = this.getLocation().getCubeLocation();
        loc[0] = loc[0] + dx;
        loc[1] = loc[1] + dy;
        loc[2] = loc[2] + dz;
        this.moveTo(loc);
    }

    /**
     * Let this unit rest.
     * @effect This unit conducts an activity of resting and this unit's current activity is interrupted
     * by the activity of resting.
     */
    public void rest() {
        Rest rest = new Rest(this);
        interruptCurrentAct(rest);
    }

    /**
     * Return whether the unit is alive.
     */
    public Boolean isAlive() {
        return this.isalive;
    }

    /**
     * Set the state of being alive of this unit according to the given flag.
     *
     * @param  flag
     * 		   The state of being alive to be registered.
     * @post   The new state of being alive of this unit is equal to the given flag.
     * 		 | this.isalive == flag
     */
    public void setAlive(Boolean flag) {
        this.isalive = flag;
    }
    
    /**
     * Return whether a task is assigned to this unit.
     */
    public boolean hasTask() {
    	return hasTask;
    }
    
    /**
     * Return the task assigned to this unit.
     */
    public Task getAssignedTask() {
    	return this.task;
    }
    
    /**
     * Check whether the given task is a valid task for any unit.
     *  
     * @param  task
     *         The task to check.
     * @return Always true. The task validity is checked upon creation.
     *       | result == true
    */
    public static boolean isValidTask(Task task) {
    	return true;
    }
    
    /**
     * Set the task of this unit to the given task.
     * 
     * @param  task
     *         The new task for this unit.
     * @post   The task of this new unit is equal to the given task.
     *       | new.gettask() == task
     * @throws IllegalArgumentException
     *         The given task is not a valid task for any unit.
     *       | ! isValidTask(getTask())
     */
    @Raw
    public void setTask(Task task) throws IllegalArgumentException {
    	if (! isValidTask(task))
    		throw new IllegalArgumentException();
    	this.task = task;
    	if (task == null) {
    		this.hasTask = false;
    		return;
    	}
    	this.hasTask = true;
    }

    /**
     * Return whether the unit is idle.
     */
    public Boolean isIdle() {
        return this.isDefaultBehaviorEnabled() && (this.getActivity().getId() == 0);
    }
    
}


