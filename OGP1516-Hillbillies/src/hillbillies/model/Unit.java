package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.activities.*;
import hillbillies.model.exceptions.UnitIllegalLocation;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;

import java.util.Random;

/**
 * A class of hillbilly Units involving a name, an x, y and z coordinate, a weight, agility, strength, toughness,
 * a facility to enable the default behaviour and a world.
 * 
 * @invar  The name of each Unit must be a valid name for any Unit.
 *       | isValidName(getName())
 * @invar  The location of each Unit must be a valid location for any Unit.
 *       | isValidLocation(getLocation)
 * @invar  The weight of each Unit must be a valid weight for any Unit.
 *       | isValidWeight(getWeight())
 * @invar  The strength of each Unit must be a valid strength for any Unit.
 *       | isValidProperty(getStrength())
 * @invar  The agility of each Unit must be a valid agility for any Unit.
 *       | isValidProperty(getAgility()) 
 * @invar  The toughness of each Unit must be a valid toughness for any Unit.
 *       | isValidProperty(getToughness()) 
 * @invar  The number of hitpoints of each Unit must be a valid number of hitpoints for any Unit.
 * 		 | isValidPoints(getCurrentHitPoints())
 * @invar  The number of stamina points of each Unit must be a valid number of stamina points for any Unit.
 * 		 | isValidPoints(getCurrentStaminaPoints())
 * @invar  The orientation of each Unit must be a valid orientation for any Unit.
 * 		 | isValidOrientation(getOrientation())
 *        
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Unit extends MovableWorldObject {

    /**
     * Initialize this new hillbilly Unit with given name, given initial x, y and z
     * coordinate, given weight, given agility, given strength, given toughness,
     * given default behaviour state and given world.
     *
     * @param name                  The name for this Unit.
     * @param x                     The x coordinate for this Unit.
     * @param y                     The y coordinate for this Unit.
     * @param z                     The z coordinate for this Unit.
     * @param weight                The weight for this Unit.
     * @param strength              The strength for this Unit.
     * @param agility               The agility for this Unit.
     * @param toughness             The toughness for this Unit.
     * @param enableDefaultBehavior The state of behaviour for this Unit.
     * @param world                 The world for this Unit.
     * @effect The name of this new Unit is set to the given name.
     * | this.setName(name)
     * @effect The x coordinate of this new Unit is set to the given x coordinate.
     * | this.setLocation(x, y, z)
     * @effect The y coordinate of this new Unit is set to the given y coordinate.
     * | this.setLocation(x, y, z)
     * @effect The z coordinate of this new Unit is set to the given z coordinate.
     * | this.setLocation(x, y, z)
     * @post If the given weight is not below 25 and not above 100 for any Unit, the weight of this new Unit is
     * equal to the given weight. Otherwise, the weight of this new Unit is equal to 25, respectively 100.
     * | if (weight < 25)
     * |   then new.getWeight == 25
     * |   else if (weight > 100)
     * |	 then new.getWeight == 100
     * |   else new.getWeight == weight
     * @post If the given strength is not below 25 and not above 100 for any Unit, the strength of this new Unit is
     * equal to the given strength. Otherwise, the strength of this new Unit is equal to 25, respectively 100.
     * | if (strength < 25)
     * |   then new.getStrength == 25
     * |   else if (strength > 100)
     * |	 then new.getStrength == 100
     * |   else new.getStrength == strength
     * @post If the given agility is not below 25 and not above 100 for any Unit, the agility of this new Unit is
     * equal to the given agility. Otherwise, the agility of this new Unit is equal to 25, respectively 100.
     * | if (agility < 25)
     * |   then new.getAgility == 25
     * |   else if (agility > 100)
     * |	 then new.getAgility == 100
     * |   else new.getAgility == agility
     * @post If the given toughness is not below 25 and not above 100 for any Unit, the toughness of this new Unit is
     * equal to the given toughness. Otherwise, the toughness of this new Unit is equal to 25, respectively 100.
     * | if (toughness < 25)
     * |   then new.getToughness == 25
     * |   else if (toughness > 100)
     * |	 then new.getToughness == 100
     * |   else new.getToughness == toughness
     * @post The initial state of behavior of this new Unit is set according to the given flag.
     * | new.defaultbehaviorenabled == enableDefaultBehavior
     * @effect The world of this new Unit is set to the given world.
     * | this.setWorld(world)
     */
    public Unit(String name, double x, double y, double z, int weight, int strength, int agility, int toughness,
                boolean enableDefaultBehavior, World world) throws UnitIllegalLocation, IllegalArgumentException {
        this.setName(name);
        if (weight < 25) {
            this.setWeight(25);
        } else if (weight > 100) {
            this.setWeight(100);
        } else {
            this.setWeight(weight);
        }
        if (agility < 25) {
            this.setAgility(25);
        } else if (agility > 100) {
            this.setAgility(100);
        } else {
            this.setAgility(agility);
        }
        if (strength < 25) {
            this.setStrength(25);
        } else if (strength > 100) {
            this.setStrength(100);
        } else {
            this.setStrength(strength);
        }
        if (toughness < 25) {
            this.setToughness(25);
        } else if (toughness > 100) {
            this.setToughness(100);
        } else {
            this.setToughness(toughness);
        }
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setLocation(x, y, z);
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(null);
        this.setFaction();
    }

    /**
     * Initialize this new hillbilly Unit with given name, given initial x, y and z coordinate, given weight,
     * given agility, given strength, given toughness and given default behaviour state.
     *
     * @param name                  The name for this Unit.
     * @param x                     The x coordinate for this Unit.
     * @param y                     The y coordinate for this Unit.
     * @param z                     The z coordinate for this Unit.
     * @param weight                The weight for this Unit.
     * @param strength              The strength for this Unit.
     * @param agility               The agility for this Unit.
     * @param toughness             The toughness for this Unit.
     * @param enableDefaultBehavior The state of behaviour for this Unit.
     * @effect The name of this new Unit is set to the given name.
     * | this.setName(name)
     * @effect The x coordinate of this new Unit is set to the given x coordinate.
     * | this.setLocation(x, y, z)
     * @effect The y coordinate of this new Unit is set to the given y coordinate.
     * | this.setLocation(x, y, z)
     * @effect The z coordinate of this new Unit is set to the given z coordinate.
     * | this.setLocation(x, y, z)
     * @post If the given weight is not below 25 and not above 100 for any Unit, the weight of this new Unit is
     * equal to the given weight. Otherwise, the weight of this new Unit is equal to 25, respectively 100.
     * | if (weight < 25)
     * |   then new.getWeight == 25
     * |   else if (weight > 100)
     * |	 then new.getWeight == 100
     * |   else new.getWeight == weight
     * @post If the given strength is not below 25 and not above 100 for any Unit, the strength of this new Unit is
     * equal to the given strength. Otherwise, the strength of this new Unit is equal to 25, respectively 100.
     * | if (strength < 25)
     * |   then new.getStrength == 25
     * |   else if (strength > 100)
     * |	 then new.getStrength == 100
     * |   else new.getStrength == strength
     * @post If the given agility is not below 25 and not above 100 for any Unit, the agility of this new Unit is
     * equal to the given agility. Otherwise, the agility of this new Unit is equal to 25, respectively 100.
     * | if (agility < 25)
     * |   then new.getAgility == 25
     * |   else if (agility > 100)
     * |	 then new.getAgility == 100
     * |   else new.getAgility == agility
     * @post If the given toughness is not below 25 and not above 100 for any Unit, the toughness of this new Unit is
     * equal to the given toughness. Otherwise, the toughness of this new Unit is equal to 25, respectively 100.
     * | if (toughness < 25)
     * |   then new.getToughness == 25
     * |   else if (toughness > 100)
     * |	 then new.getToughness == 100
     * |   else new.getToughness == toughness
     * @post The initial state of behavior of this new Unit is set according to the given flag.
     * | new.defaultbehaviorenabled == enableDefaultBehavior
     */
    public Unit(String name, double x, double y, double z, int weight, int strength, int agility, int toughness,
                boolean enableDefaultBehavior) throws UnitIllegalLocation, IllegalArgumentException {
        this.setName(name);
        if (weight < 25) {
            this.setWeight(25);
        } else if (weight > 100) {
            this.setWeight(100);
        } else {
            this.setWeight(weight);
        }
        if (agility < 25) {
            this.setAgility(25);
        } else if (agility > 100) {
            this.setAgility(100);
        } else {
            this.setAgility(agility);
        }
        if (strength < 25) {
            this.setStrength(25);
        } else if (strength > 100) {
            this.setStrength(100);
        } else {
            this.setStrength(strength);
        }
        if (toughness < 25) {
            this.setToughness(25);
        } else if (toughness > 100) {
            this.setToughness(100);
        } else {
            this.setToughness(toughness);
        }
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
        this.setActivity(null);
        this.setFaction();
    }

    /**
     * Initialize this new hillbilly Unit with given world and given default behaviour state.
     *
     * @param world                 The world for this Unit.
     * @param enableDefaultBehavior The state of behaviour for this Unit.
     * @post The initial state of behavior of this new Unit is set according to the given flag.
     * | new.defaultbehaviorenabled == enableDefaultBehavior
     * @effect The world of this new Unit is set to the given world.
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
        this.setWeight(randomWeight);
        this.setAgility(randomAgility);
        this.setStrength(randomStrength);
        this.setToughness(randomToughness);
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setLocation(randomLocX, randomLocY, randomLocZ);
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(null);
        this.setFaction();
        this.setName(name);
    }

    /**
     * Variable registering the time since this unit last rested
     */
    public double timeSinceLastRest;

    /**
     * Variable registering the name of this Unit.
     */
    private String name;

    /**
     * Variable registering the weight of this Unit.
     */
    public int weight;

    /**
     * Variable registering the agility of this Unit.
     */
    private int agility;

    /**
     * Variable registering the strength of this Unit.
     */
    private int strength;

    /**
     * Variable registering the toughness of this Unit.
     */
    private int toughness;

    /**
     * Variable registering the state of default behavior of this Unit.
     */
    private boolean defaultbehaviorenabled;

    /**
     * Variable registering the hitpoints of this Unit.
     */
    private int hitpoints;

    /**
     * Variable registering the stamina points of this Unit.
     */
    private int staminapoints;

    /**
     * Variable registering the experience points of this Unit.
     */
    private int xp = 0;

    /**
     * Variable registering the number of experience points of this Unit that have already been used to increase
     * its strength, agility or toughness.
     */
    private int xpused = 0;

    /**
     * Variable registering the orientation of this Unit.
     */
    private float orientation;

    /**
     * Variable registering the activity of this Unit which is paused but will be continued after this Unit
     * finishes its current activity.
     */
    private IActivity pausedActivity;

    /**
     * Variable registering whether this Unit has a paused activity or not.
     */
    private boolean hasPausedActivity;

    /**
     * Variable registering whether this Unit is carrying an object or not.
     */
    private boolean iscarrying = false;

    /**
     * Variable registering the object this Unit is carrying.
     */
    private MovableWorldObject carriedObject;

    /**
     * Variable registering whether this Unit is sprinting or not.
     */
    private boolean issprinting = false;

    /**
     * Variable registering the faction this Unit belongs to.
     */
    private Faction faction;
    
    /**
     * Object holding the random generator used during the random cration of the unit.
     */
    private Random random;

    /**
     * Variable registering whether this Unit is alive or not.
     */
    private boolean isalive;
    
    /**
     * Variable registering the task assigned to this Unit.
     */
    private Task task;
    
    /**
     * Variable registering whether a task is assigned to this Unit.
     */
    private boolean hasTask = false;
    
    /**
     * Return the name of this Unit.
     */
    @Basic
    @Raw
    public String getName() {
        return name;
    }

    /**
     * Check whether the given name is a valid name for any Unit.
     *
     * @param name The name to check.
     * @return True if and only if the given name contains at least two characters, if the first character is an
     * uppercase letter and if all characters are uppercase or lowercase letters, single or double quotes
     * or spaces.
     * | result == (name.length() >= 2)
     * && Character.isUpperCase(name.charAt(0))
     * && "[A-Z|a-z|\"|\'|\\s]*".matches(name)
     */
    private boolean isValidName(String name) {
        return (name.length() >= 2) && Character.isUpperCase(name.charAt(0)) && "[A-Z|a-z|\"|\'|\\s]*".matches(name);
    }

    /**
     * Set the name of this Unit to the given name.
     *
     * @param name The new name for this Unit.
     * @throws IllegalArgumentException() The given initial name is not a valid name for any Unit.
     *                                    | ! isValidName(getName)
     * @post The name of this new Unit is equal to the given name.
     * | new.getName == name
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException {
        if (!isValidName(name))
            throw new IllegalArgumentException("This is an invalid name.");
        this.name = name;
    }

    /**
     * Check whether the given property is a valid property for any Unit.
     *
     * @param property The property to check.
     * @return True if and only if the property number is equal or higher than 1 and if
     * it is equal or lower than 200, inclusive.
     * | result == (property >= 1
     * && property <= 200)
     */
    private boolean isValidProperty(int property) {
        return (property >= 1 && property <= 200);
    }

    /**
     * Check whether the given weight is a valid weight for any Unit.
     *
     * @param weight The weight to check.
     * @return True if and only if the property number is equal or higher than 1, if
     * it is equal or lower than 200, inclusive, and if it is equal or higher
     * than the Unit's minimum weight.
     * | result == (weight >= 1 && weight <= 200
     * && weight >= 0.5 * (this.getStrength() + this.getAgility()))
     */
    private boolean isValidWeight(int weight) {
        return (weight >= 1 && weight <= 200 && weight >= 0.5 * (this.getStrength() + this.getAgility()));
    }

    /**
     * Return the weight of this Unit.
     */
    @Basic
    @Raw
    @Override
    public int getWeight() {
        if (isCarrying())
            return this.weight + carriedObject.getWeight();
        return this.weight;
    }

    /**
     * Set the weight of this Unit to the given weight.
     *
     * @param weight The new weight for this Unit.
     * @post If the given weight is not below 1, not above 200 nor above the minimum weight for any Unit,
     * the weight of this new Unit is equal to the given weight. Otherwise, its new weight is
     * equal to 1, 200, respectively the minimum weight.
     * | if (isValidWeight(weight))
     * |   then new.getWeight() == weight
     * |   else if (weight < 1)
     * |   then new.getWeight() == 1
     * |	 else if (weight > 200)
     * |	 then new.getWeight() == 200
     * |	 else if (weight < 0.5 * (this.getStrength() + this.getAgility()))
     * |	 then new.getWeight() == (0.5 * (this.getStrength() + this.getAgility()))
     */
    public void setWeight(int weight) {
        if (isValidWeight(weight)) {
            this.weight = weight;
        } else if (weight < 1) {
            this.setWeight(1);
        } else if (weight > 200) {
            this.setWeight(200);
        } else if (weight < 0.5 * (this.getStrength() + this.getAgility())) {
            this.weight = (int) (0.5 * (this.getStrength() + this.getAgility()));
        }
    }

    /**
     * Return the strength of this Unit.
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Set the strength of this Unit to the given strength.
     *
     * @param strength The new strength for this Unit.
     * @post If the given strength is not below 1 and not above 200 for any Unit, the strength of this new Unit is
     * equal to the given strength. Otherwise, its new strength is equal to 1, respectively 200.
     * | if (isValidProperty(strength))
     * |	 then new.getStrength() == strength
     * |	 else if (strength < 1)
     * |   then new.getStrength() == 1
     * |	 else if (strength > 200)
     * |	 then new.getStrength() == 200
     */
    public void setStrength(int strength) {
        if (isValidProperty(strength)) {
            this.strength = strength;
        } else if (strength < 1) {
            this.strength = 1;
        } else if (strength > 200) {
            this.strength = 200;
        }
    }

    /**
     * Return the agility of this Unit.
     */
    public int getAgility() {
        return this.agility;
    }

    /**
     * Set the agility of this Unit to the given agility.
     *
     * @param agility The new agility for this Unit.
     * @post If the given agility is not below 1 and not above 200 for any Unit, the agility of this new Unit is
     * equal to the given agility. Otherwise, its new agility is equal to 1, respectively 200.
     * | if (isValidProperty(agility))
     * |	 then new.getAgility() == agility
     * |	 else if (agility < 1)
     * |   then new.getAgility() == 1
     * |	 else if (agility > 200)
     * |	 then new.getAgility() == 200
     */
    public void setAgility(int agility) {
        if (isValidProperty(agility)) {
            this.agility = agility;
        } else if (agility < 1) {
            this.agility = 1;
        } else if (agility > 200) {
            this.agility = 200;
        }
    }

    /**
     * Return the toughness of this Unit.
     */
    public int getToughness() {
        return this.toughness;
    }

    /**
     * Set the toughness of this Unit to the given toughness.
     *
     * @param toughness The new toughness for this Unit.
     * @post If the given toughness is not below 1 and not above 200 for any Unit, the toughness of this new Unit is
     * equal to the given toughness. Otherwise, its new toughness is equal to 1, respectively 200.
     * | if (isValidProperty(toughness))
     * |	 then new.getToughness() == toughness
     * |	 else if (toughness < 1)
     * |   then new.getToughness() == 1
     * |	 else if (toughness > 200)
     * |	 then new.getToughness() == 200
     */
    public void setToughness(int toughness) {
        if (isValidProperty(toughness)) {
            this.toughness = toughness;
        } else if (toughness < 1) {
            this.toughness = 1;
        } else if (toughness > 200) {
            this.toughness = 200;
        }
    }

    /**
     * Return whether the default behavior is enabled or not.
     */
    public boolean isDefaultBehaviorEnabled() {
        return this.defaultbehaviorenabled;
    }

    /**
     * Set the state of default behavior of this Unit according to the given flag.
     *
     * @param flag The default behavior state to be registered.
     * @post The new default behavior state of this Unit is equal to the given flag.
     * | this.defaultbehaviorenabled == flag
     */
    public void setDefaultBehavior(boolean flag) {
        this.defaultbehaviorenabled = flag;
    }

    @Raw
    @Override
    protected boolean canHaveAsWorld(World world) {
        return (world.getNumberOfUnits() < 100);
    }

    /**
     * Return the maximum number of hitpoints or stamina points of this Unit.
     */
    public int getMaxPoints() {
        return (int) Math.ceil(200 * ((double) this.getWeight() / 100) * ((double) this.getToughness() / 100));
    }

    /**
     * Check whether the given number of points is a valid number of points for any Unit.
     *
     * @param points The number of points to check.
     * @return True if and only if the given number of points is less or equal to the  maximum number of points
     * and greater or equal to zero.
     * | result == (points <= getMaxPoints()
     * && points >= 0)
     */
    private boolean isValidPoints(int points) {
        return (points <= getMaxPoints() && points >= 0);
    }

    /**
     * Return the number of hitpoints of this Unit.
     */
    public int getCurrentHitPoints() {
        return this.hitpoints;
    }

    /**
     * Set the number of hitpoints of this Unit to the given hitpoints.
     *
     * @param hitPoints The number of hitpoints for this Unit.
     * @pre The given number of hitpoints must be a valid number of hitpoints for any Unit.
     * | isValidPoints(hitPoints)
     * @post The number of hitpoints of this Unit is equal to the given number of hitpoints.
     * | new.getCurrentHitPoints() == hitPoints
     */
    @Raw
    public void setCurrentHitPoints(int hitPoints) {
        assert isValidPoints(hitPoints);
        this.hitpoints = hitPoints;
    }

    /**
     * Return the number of stamina points of this Unit.
     */
    public int getCurrentStaminaPoints() {
        return this.staminapoints;
    }

    /**
     * Set the number of stamina points of this Unit to the given stamina points.
     *
     * @param stamPoints The number of stamina points for this Unit.
     * @pre The given number of stamina points must be a valid number of stamina points for any Unit.
     * | isValidPoints(stamPoints)
     * @post The number of stamina points of this Unit is equal to the given number of stamina points.
     * | new.getCurrentStamPoints() == stamPoints
     */
    @Raw
    public void setCurrentStaminaPoints(int stamPoints) {
        assert isValidPoints(stamPoints);
        this.staminapoints = stamPoints;
    }

    /**
     * Return the orientation of this Unit.
     */
    @Basic
    @Raw
    public float getOrientation() {
        return this.orientation;
    }

    /**
     * Check whether the given orientation is a valid orientation for any Unit.
     *
     * @param orientation The orientation to check.
     * @return True if and only if the orientation is greater or equal to zero and less or equal to 2*PI.
     * | result == (orientation >= 0
     * && orientation <= 2*PI)
     */
    private static boolean isValidOrientation(float orientation) {
        return (orientation >= 0 && orientation <= 2 * Math.PI);
    }

    /**
     * Set the orientation of this Unit to the given orientation.
     *
     * @param orientation The new orientation for this Unit.
     * @post If the given orientation is a valid orientation for any Unit, the orientation of this new Unit is
     * equal to the given orientation. Otherwise, the orientation of this new Unit is equal to 0.5*PI.
     * | if (isValidOrientation(orientation))
     * |	 then new.getOrientation() == orientation
     * |   else new.getOrientation() == 0.5*PI
     */
    @Raw
    public void setOrientation(float orientation) {
        if (isValidOrientation(orientation))
            this.orientation = orientation;
        else
            this.orientation = ((float) Math.PI) / 2;
    }

    /**
     * Check whether the given activity is a valid activity for any Unit.
     *
     * @param activity The activity to check.
     * @return Always true.
     * | result == true
     */
    @Override
    public boolean isValidActivity(IActivity activity) {
        return true;
    }

    /**
     * Return whether the Unit is carrying an object.
     */
    public boolean isCarrying() {
        return iscarrying;
    }

    /**
     * Return the object carried by this Unit.
     */
    public MovableWorldObject getCarriedObject() {
        return this.carriedObject;
    }

    /**
     * Let the Unit carry the given object.
     *
     * @param toBeCarriedObject The object which needs to be carried by the unit.
     * @throws IllegalArgumentException The given object cannot be carried by any Unit.
     *                                  | ! canBeCarried(getCarriedObject())
     * @post The carried object of this Unit will be equal to the given object, the carrying state of this Unit
     * will be set to true and the object will removed from its world's world map.
     */
    public void carry(MovableWorldObject toBeCarriedObject) throws IllegalArgumentException {
        if (canBeCarried(toBeCarriedObject)) {
            this.carriedObject = toBeCarriedObject;
            this.iscarrying = true;
            toBeCarriedObject.unregister();
        } else
            throw new IllegalArgumentException("This object cannot be carried");
    }

    /**
     * Check whether the given object can be carried by this Unit.
     *
     * @param object The object to check.
     * @return True if and only if this Unit is not already carrying an object and if the given object is not a Unit.
     */
    private boolean canBeCarried(MovableWorldObject object) {
        return !this.isCarrying() && (!(object instanceof Unit));
    }

    /**
     * Let the Unit drop the given carried object.
     *
     * @param carriedObject The object to drop.
     * @effect The carried object of this Unit is set to null, the carrying state of this Unit is set
     * to false and the object is added in its world's world map.
     */
    public void drop(MovableWorldObject carriedObject) {
        this.carriedObject = null;
        this.iscarrying = false;
        double[] locArray = this.getLocation().getArray();
        carriedObject.setLocation(locArray);
    }

    /**
     * Return whether the Unit is sprinting or not.
     */
    public boolean isSprinting() {
        return this.issprinting;
    }

    /**
     * Set the state of sprinting of this Unit according to the given flag.
     *
     * @param flag The sprinting state to be registered.
     * @post The new sprinting state of this Unit is equal to the given flag.
     * | new.issprinting == flag
     */
    public void setSprinting(boolean flag) {
        this.issprinting = flag;
    }

    /**
     * No documentation required.
     */
    @Override
    public void advanceTime(double dt) {
        this.timeSinceLastRest = this.timeSinceLastRest + dt;
        if (this.timeSinceLastRest >= 300) {
            boolean flag = this.interruptCurrentAct(new Rest(this));
            if (flag) this.timeSinceLastRest = 0;

        }
        if (this.getActivity().getId() == 0 && isDefaultBehaviorEnabled()) {
            behaveDefault();
        }
        this.getActivity().advanceActivityTime(dt);
    }

    /**
     * Let this Unit conduct its default behavior.
     */
    private void behaveDefault() {
        this.setActivity(new Rest(this));
        //TODO flesh out this method
    }

    /**
     * Check whether this Unit's current activity can be interrupted by the given new activity.
     *
     * @param newActivity The new activity for this Unit.
     * @return True if and only if this Unit's current activity can be interrupted by the given new activity.
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
     * Let this Unit finish its current activity.
     *
     * @effect If this Unit had a paused activity, the paused activity is resumed and if this Unit did not have
     * a paused activity, its current activity is set to none.
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
     * Let this Unit conduct a generic labor at a specified cube position.
     *
     * @param targetCube The position of the cube to let this Unit work at.
     * @effect The Unit conducts work at the given target cube.
     */
    public void work(int[] targetCube) {
        Work work = new Work(this, targetCube);
        if (!interruptCurrentAct(work))
            return;
        this.setActivity(work);
    }

    /**
     * Let this Unit attack the given Unit.
     *
     * @param defender The Unit to attack.
     * @throws IllegalArgumentException The attack cannot be conducted.
     * @effect The Unit conducts an attack against the defender and the defender conducts a defence against this Unit.
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
     * Return the faction of this Unit.
     */
    @Basic
    @Raw
    public Faction getFaction() {
        return this.faction;
    }

    /**
     * Check whether the given faction is a valid faction for any Unit.
     *
     * @param faction The faction to check.
     * @return True if and only if the given faction contains less than 50 Units.
     * | result == (faction.getNumberOfUnits() < 50)
     */
    boolean canHaveAsFaction(Faction faction) {
        return (faction.getNumberOfUnits() < 50);
    }

    /**
     * Set the faction of this Unit.
     *
     * @throws IllegalArgumentException The faction with the smallest number of Units is not a valid faction for any Unit.
     *                                  | ! canHaveAsFaction(getFaction())
     * @effect The faction of this new Unit is equal to a newly created faction if the maximum number of active
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
     * Let this Unit deal with its damage.
     *
     * @param damage The damage this Unit is dealing with.
     * @effect If the given number of damage points is greater or equal to this Unit's hitpoints, the Unit will die.
     * Otherwise, the given number of damage points will be subtracted from this Unit's hitpoints.
     */
    public void dealDamage(double damage) {
        int intDamage = (int) Math.floor(damage);
        if (intDamage >= this.getCurrentHitPoints())
            die();
        else
            this.setCurrentHitPoints(this.getCurrentHitPoints() - intDamage);
    }

    /**
     * Let this Unit die.
     *
     * @effect This Unit is unregistered, removed from its world and removed from its faction.
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
     * Return the experience points of this Unit.
     */
    @Basic
    @Raw
    public int getXP() {
        return this.xp;
    }

    /**
     * Check whether the given number of experience points is a valid number of experience points for any Unit.
     *
     * @param xp The number of experience points to check.
     * @return True if and only if the number of experience points is greater or equal to zero.
     * | result == (xp >= 0)
     */
    private static boolean isValidXP(int xp) {
        return (xp >= 0);
    }

    /**
     * Set the number of experience points of this Unit to the given number of experience points.
     *
     * @param xp The new number of experience points for this Unit.
     * @effect If the given number of experience points is a valid number of experience points for any Unit,
     * the number of experience points of this new Unit is equal to the given number of experience points.
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
     * @effect The given number of experience points is added to the current amount of experience points of this Unit.
     */
    public void addXP(int xp) {
        this.setXP(this.getXP() + xp);
    }

    /**
     * Let this Unit conduct a movement to the given location.
     *
     * @param destination The location to let this Unit move to.
     * @effect This Unit conducts a movement to the given location and this Unit's current activity is interrupted
     * by the movement.
     */
    public void moveTo(int[] destination) {
        if (!this.getWorld().canHaveAsCubeLocation(destination, this))
            return;
        Movement movement = new Movement(this, destination);
        interruptCurrentAct(movement);
    }

    /**
     * Let this Unit move to a location in an adjacent cube.
     *
     * @param dx The movement along the x axis to make.
     * @param dy The movement along the y axis to make.
     * @param dz The movement along the z axis to make.
     * @throws UnitIllegalLocation The intended movement is not a movement to an adjacent cube.
     * @effect This Unit moves to the adjacent cube, referred to by given dx, dy and dz.
     */
    public void moveToAdjacent(int dx, int dy, int dz) throws UnitIllegalLocation {
        if (Math.abs(dx) > 1 || Math.abs(dy) > 1 || Math.abs(dz) > 1)
            throw new UnitIllegalLocation("Illegal move to adjacent destination");
        if (this.getActivity().getId() == 3)
            return;
        int[] loc = this.getLocation().getCubeLocation();
        loc[0] = loc[0] + dx;
        loc[1] = loc[1] + dy;
        loc[2] = loc[2] + dz;
        this.moveTo(loc);
    }

    /**
     * Let this Unit rest.
     * @effect This Unit conducts an activity of resting and this Unit's current activity is interrupted
     * by the activity of resting.
     */
    public void rest() {
        Rest rest = new Rest(this);
        interruptCurrentAct(rest);
    }

    /**
     * Return whether the Unit is alive.
     */
    public Boolean isAlive() {
        return this.isalive;
    }

    /**
     * Set the state of being alive of this Unit according to the given flag.
     *
     * @param  flag
     * 		   The state of being alive to be registered.
     * @post   The new state of being alive of this Unit is equal to the given flag.
     * 		 | this.isalive == flag
     */
    public void setAlive(Boolean flag) {
        this.isalive = flag;
    }
    
    /**
     * Return whether a task is assigned to this Unit.
     */
    public boolean hasTask() {
    	return hasTask;
    }
    
    /**
     * Return the task assigned to this Unit.
     */
    public Task getTask() {
    	return this.task;
    }
    
    /**
     * Check whether the given task is a valid task for any Unit.
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
     * Set the task of this Unit to the given task.
     * 
     * @param  task
     *         The new task for this Unit.
     * @post   The task of this new Unit is equal to the given task.
     *       | new.gettask() == task
     * @throws IllegalArgumentException
     *         The given task is not a valid task for any Unit.
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
     * Return whether the Unit is idle.
     */
    public Boolean isIdle() {
        return this.isDefaultBehaviorEnabled() && (this.getActivity().getId() == 0);
    }
    
}


