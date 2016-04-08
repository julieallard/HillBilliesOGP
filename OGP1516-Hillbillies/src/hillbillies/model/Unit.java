
package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.activities.*;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/**
 * A class of hillbilly Units involving a name, an x, y and z coordinate, a weight,
 * agility, strength, toughness, a facility to enable the default behaviour and a world.
 * 
 * @invar  The name of each Unit must be a valid name for any
 *         Unit.
 *       | isValidName(getName())
 * @invar  The x coordinate of each Unit must be a valid x coordinate for any
 *         Unit.
 *       | isValidLocation(getLocation)
 * @invar  The y coordinate of each Unit must be a valid y coordinate for any
 *         Unit.
 *       | isValidLocation(getLocation)
 * @invar  The z coordinate of each Unit must be a valid z coordinate for any
 *         Unit.
 *       | isValidLocation(getLocation)
 * @invar  The weight of each Unit must be a valid weight for any
 *         Unit.
 *       | isValidWeight(getWeight())
 * @invar  The strength of each Unit must be a valid strength for any
 *         Unit.
 *       | isValidProperty(getStrength())
 * @invar  The agility of each Unit must be a valid agility for any
 *         Unit.
 *       | isValidProperty(getAgility()) 
 * @invar  The toughness of each Unit must be a valid toughness for any
 *         Unit.
 *       | isValidProperty(getToughness()) 
 * @invar  The number of hitpoints of each Unit must be a valid number of
 * 		   hitpoints for any Unit.
 * 		 | isValidPoints(getCurrentHitPoints())
 * @invar  The number of stamina points of each Unit must be a valid number of
 *		   stamina points for any Unit.
 * 		 | isValidPoints(getCurrentStaminaPoints())
 * @invar  The orientation of each Unit must be a valid orientation for any
 * 		   Unit.
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
     * @param  name                  
     * 		   The name for this Unit.
     * @param  x
     * 		   The x coordinate for this Unit.
     * @param  y
     * 		   The y coordinate for this Unit.
     * @param  z
     * 		   The z coordinate for this Unit.
     * @param  weight                
     * 		   The weight for this Unit.
     * @param  strength              
     * 		   The strength for this Unit.
     * @param  agility               
     * 		   The agility for this Unit.
     * @param  toughness             
     * 		   The toughness for this Unit.
     * @param  enableDefaultBehavior 
     * 		   The state of behaviour for this Unit.
     * @param  world
     * 		   The world for this Unit.
     * @effect The name of this new Unit is set to
     *         the given name.
     *       | this.setName(name)
	 * @effect The x coordinate of this new Unit is set to
	 *         the given x coordinate.
	 *       | this.setLocation(x, y, z)
	 * @effect The y coordinate of this new Unit is set to
	 *         the given y coordinate.
	 *       | this.setLocation(x, y, z)
	 * @effect The z coordinate of this new Unit is set to
	 *         the given z coordinate.
	 *       | this.setLocation(x, y, z)
	 * @post   If the given weight is not below 25 and not above 100 for any Unit,
	 *         the weight of this new Unit is equal to the given
	 *         weight. Otherwise, the weight of this new Unit is equal
	 *         to 25, respectively 100.
	 *       | if (weight < 25)
	 *       |   then new.getWeight == 25
	 *       |   else if (weight > 100)
	 *       |	 then new.getWeight == 100
	 *       |   else new.getWeight == weight
	 * @post   If the given strength is not below 25 and not above 100 for any Unit,
	 *         the strength of this new Unit is equal to the given
	 *         strength. Otherwise, the strength of this new Unit is equal
	 *         to 25, respectively 100.
	 *       | if (strength < 25)
	 *       |   then new.getStrength == 25
	 *       |   else if (strength > 100)
	 *       |	 then new.getStrength == 100
	 *       |   else new.getStrength == strength
	 * @post   If the given agility is not below 25 and not above 100 for any Unit,
	 *         the agility of this new Unit is equal to the given
	 *         agility. Otherwise, the agility of this new Unit is equal
	 *         to 25, respectively 100.
	 *       | if (agility < 25)
	 *       |   then new.getAgility == 25
	 *       |   else if (agility > 100)
	 *       |	 then new.getAgility == 100
	 *       |   else new.getAgility == agility
	 * @post   If the given toughness is not below 25 and not above 100 for any Unit,
	 *         the toughness of this new Unit is equal to the given
	 *         toughness. Otherwise, the toughness of this new Unit is equal
	 *         to 25, respectively 100.
	 *       | if (toughness < 25)
	 *       |   then new.getToughness == 25
	 *       |   else if (toughness > 100)
	 *       |	 then new.getToughness == 100
	 *       |   else new.getToughness == toughness
     * @post   The initial state of behavior of this new Unit is set according
     * 		   to the given flag.
     * @effect The world of this new Unit is set to
     * 		   the given world.
     * 		 | this.setWorld(world)
     */
    public Unit(String name, double x, double y, double z, int weight, int strength,
    		int agility, int toughness, boolean enableDefaultBehavior, World world)
            throws UnitIllegalLocation, IllegalArgumentException {
        this.setName(name);        
    	this.setLocation(x, y, z);
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
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(null);
        this.setFaction();
    }

    public Unit(World world, boolean enableDefaultBehavior) {
    	Random random = new Random();
        String name = getRandomName(random);
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
		//this.setName(name);        
    	this.setLocation(randomLocX, randomLocY, randomLocZ);
        this.setWeight(randomWeight);
        this.setAgility(randomAgility);
        this.setStrength(randomStrength);
        this.setToughness(randomToughness);
        this.setDefaultBehavior(enableDefaultBehavior);
        this.setWorld(world);
        this.setCurrentHitPoints(getMaxPoints());
        this.setCurrentStaminaPoints(getMaxPoints());
        this.setOrientation((float) (0.5 * Math.PI));
        this.setActivity(null);
        this.setFaction();
        this.setName(name);
    }
    
    private String name;
    private VLocation location;
    private int weight;
    private int agility;
    private int strength;
    private int toughness;
    private boolean defaultbehaviorenabled;
    private World world;
    
    private int hitpoints;
    private int staminapoints;
    private int xp = 0;
    private float orientation;
    private IActivity activity;
    private IActivity pausedActivity;    
    private boolean hasPausedActivity;
    private boolean iscarrying = false;
    private MovableWorldObject carriedObject;
    private boolean issprinting = false;
    private Faction faction;
    
    /**
     * Return the name of this Unit.
     */
    @Basic
    @Raw
    public String getName() {
        return this.name;
    }

    /**
     * Check whether the given name is a valid name for any Unit.
     *
     * @param  name
     * 		   The name to check.
     * @return True if and only if the given name contains at least two characters,
     * 		   if the first character is an uppercase letter and if all characters
     * 		   are uppercase or lowercase letters, single or double quotes or spaces.
     *       | result == (name.length() >= 2)
     *       		&& Character.isUpperCase(name.charAt(0))
     *       		&& "[A-Z|a-z|\"|\'|\\s]*".matches(name)
     */
    public boolean isValidName(String name) {
        return (name.length() >= 2) && Character.isUpperCase(name.charAt(0)) && "[A-Z|a-z|\"|\'|\\s]*".matches(name);
    }    
    
    /**
     * Set the name of this Unit to the given name.
     *
     * @param  name
     * 		   The new name for this Unit.
     * @post   The name of this new Unit is equal to the given name.
     * 		 | new.getName == name
     * @throws IllegalArgumentException()
     * 		   The given initial name is not a valid name for any Unit.
     * 		 | ! isValidName(getName)
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException {
        if (! isValidName(name)) 
            throw new IllegalArgumentException("This is an invalid name.");
        this.name = name;
    }

    /**
     * Return the location of this Unit.
     */
    @Basic
    @Raw
    @Override
    public VLocation getLocation() {
        return this.location;
    }

    /**
     * Check whether the given location is a valid location for
     * any Unit.
     *
     * @param  location
     * 		   The location to check.
     * @return 
     *       | result == VLocation.isValidLocation(location)
     */
    public static boolean isValidLocation(VLocation location) {
        return VLocation.isValidLocation(location);
    }

    /**
     * Set the location of this Unit to the given x, y and z coordinate.
     *
     * @param  x
     * 		   The x coordinate for this Unit.
     * @param  y
     * 		   The y coordinate for this Unit.
     * @param  z
     * 		   The z coordinate for this Unit.
     * @post   The x coordinate of the location of this new Unit is equal to
     * 		   the given x coordinate.
     * 		 | new.getLocation() == location
     * @post   The y coordinate of the location of this new Unit is equal to
     * 		   the given y coordinate.
     * 		 | new.getLocation() == location
     * @post   The z coordinate of the location of this new Unit is equal to
     * 		   the given z coordinate.
     * 		 | new.getLocation() == location
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any
     *         Unit.
     *       | ! isValidLocation(getLocation())
     */
    @Override
    public void setLocation(double x, double y, double z) throws UnitIllegalLocation {
        VLocation location = new VLocation(x, y, z, this);
    	this.setLocation(location);
    }
    
    /**
     * Set the location of this Unit to the given array of x, y and z coordinate.
     *
     * @param  array
     * 		   The array of the new x, y and z coordinate for this Unit.
     * @post   The x, y and z coordinate of the location of this new Unit is equal to
     * 		   the given x, y and z coordinate.
     * 		 | new.getLocation() == location
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any
     *         Unit.
     *       | ! isValidLocation(getLocation())
     */
    public void setLocation(double[] array) throws UnitIllegalLocation {
        VLocation location = new VLocation(array[0], array[1], array[2], this);
        this.setLocation(location);
    }
    
    /**
     * Set the location of this Unit to the given location.
     *
     * @param  location
     * 		   The new location for this Unit.
     * @post   The location of this new Unit is equal to
     * 		   the given location.
     * 		 | new.getLocation() == location
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any
     *         Unit.
     *       | ! isValidLocation(getLocation())
     */
    @Raw
    @Override
    public void setLocation(VLocation location) throws UnitIllegalLocation {

        if ((!isValidLocation(location))||location.occupant==this)
            throw new UnitIllegalLocation();
        this.location = location;
        this.register(location);
    }
    
    @Override
    public void register(VLocation location) {
    	this.getWorld().getWorldMap().put(location, this);
    }
    
    /**
     * Check whether the given property is a valid property for
     * any Unit.
     *  
     * @param  property
     *         The property to check.
     * @return True if and only if the property number is equal or higher than 1 and if
     * 		   it is equal or lower than 200, inclusive.
     *       | result == (property >= 1
     *       		&& property <= 200)
    */
    public boolean isValidProperty(int property) {
    	return (property >= 1 && property <= 200);
    }
    
    /**
     * Check whether the given weight is a valid weight for
     * any Unit.
     *  
     * @param  weight
     *         The weight to check.
     * @return True if and only if the property number is equal or higher than 1, if
     * 		   it is equal or lower than 200, inclusive, and if it is equal or higher
     * 		   than the Unit's minimum weight.
     *       | result == (weight >= 1 && weight <= 200
     *       		&& weight >= 0.5 * (this.getStrength() + this.getAgility()))
    */
    public boolean isValidWeight(int weight) {
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
     * @param  weight
     * 		   The new weight for this Unit.
     * @post   If the given weight is not below 1, not above 200 nor above
     * 		   the minimum weight for any Unit,
     *         the weight of this new Unit is equal to the given
     *         weight. Otherwise, its new weight is equal to 1, 200, respectively
     *         the minimum weight.
     *       | if (isValidWeight(weight))
     *       |   then new.getWeight() == weight
     *       |   else if (weight < 1)
     *       |   then new.getWeight() == 1
     *       |	 else if (weight > 200)
     *       |	 then new.getWeight() == 200
     *       |	 else if (weight < 0.5 * (this.getStrength() + this.getAgility()))
     *       |	 then new.getWeight() == (0.5 * (this.getStrength() + this.getAgility()))
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
     * @param  strength
     * 		   The new strength for this Unit.
     * @post   If the given strength is not below 1 and not above 200 for any Unit,
     *         the strength of this new Unit is equal to the given
     *         strength. Otherwise, its new strength is equal to 1, respectively 200.
     *       | if (isValidProperty(strength))
     *       |	 then new.getStrength() == strength
     *       |	 else if (strength < 1)
     *       |   then new.getStrength() == 1
     *       |	 else if (strength > 200)
     *       |	 then new.getStrength() == 200
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
     * @param  agility
     * 		   The new agility for this Unit.
     * @post   If the given agility is not below 1 and not above 200 for any Unit,
     *         the agility of this new Unit is equal to the given
     *         agility. Otherwise, its new agility is equal to 1, respectively 200.
     *       | if (isValidProperty(agility))
     *       |	 then new.getAgility() == agility
     *       |	 else if (agility < 1)
     *       |   then new.getAgility() == 1
     *       |	 else if (agility > 200)
     *       |	 then new.getAgility() == 200
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
     * @param  toughness
     * 		   The new toughness for this Unit.
     * @post   If the given toughness is not below 1 and not above 200 for any Unit,
     *         the toughness of this new Unit is equal to the given
     *         toughness. Otherwise, its new toughness is equal to 1, respectively 200.
     *       | if (isValidProperty(toughness))
     *       |	 then new.getToughness() == toughness
     *       |	 else if (toughness < 1)
     *       |   then new.getToughness() == 1
     *       |	 else if (toughness > 200)
     *       |	 then new.getToughness() == 200
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
     * Return whether the default behavior is enabled.
     */
    public boolean isDefaultBehaviorEnabled() {
        return defaultbehaviorenabled;
    }

    /**
     * Set the state of default behavior of this Unit according to the given flag.
     *
     * @param  flag
     * 		   The default behavior state to be registered.
     * @post   The new default behavior state of this Unit is equal to the given flag.
     * 		 | this.defaultbehaviorenabled == flag
     */
    public void setDefaultBehavior(boolean flag) {
        this.defaultbehaviorenabled = flag;
    }    
    
    /**
     * Enable the default behavior of this Unit.
     * 
     * @effect The default behavior state of this Unit is set to true.
     * 		 | setDefaultBehavior(true)
     */
    private void startDefaultBehavior() {
    	setDefaultBehavior(true);
    }
   
    /**
     * Disable the default behavior of this Unit.
     * 
     * @effect The default behavior state of this Unit is set to false.
     * 		 | setDefaultBehavior(false)
     */
    private void stopDefaultBehavior() {
    	setDefaultBehavior(false);
    }

    /**
     * Return the world of this Unit.
     */
    @Basic
    @Raw
    @Immutable
    @Override
    public World getWorld() {
      return this.world;
    }

    /**
     * Check whether this Unit can have the given world as its world.
     *
     * @param  world
     *         The world to check.
     * @return True if and only if the given world contains less
     * 		   than 100 Units.
     *       | result == (world.getNumberOfUnits() < 100)
     */
    @Raw
    public boolean canHaveAsWorld(World world) {
    	return (world.getNumberOfUnits() < 100);
    }
    
    /**
     * Set the world of this Unit to the given world.
     * 
     * @param  world
     *         The new world for this Unit.
     * @post   The world of this new Unit is equal to
     *         the given world.
     *       | new.getWorld() == world
     * @throws IllegalArgumentException
     *         The given world is not a valid world for any
     *         Unit.
     *       | ! canHaveAsWorld(getWorld())
     */
    @Raw
    public void setWorld(World world) throws IllegalArgumentException {
		if (! canHaveAsWorld(world))
			throw new IllegalArgumentException("This world has already reached its max amount of Units.");
		this.world = world;
		this.getWorld().addUnit(this);	
	}    
    
    /**
     * Return the maximum number of hitpoints or stamina points of this Unit.
     */
    public int getMaxPoints() {
        return (int) Math.ceil(200 * ((double) this.getWeight() / 100) * ((double) this.getToughness() / 100));
    }

    /**
     * Check whether the given number of points is a valid number of points for
     * any Unit.
     *  
     * @param  points
     *         The number of points to check.
     * @return True if and only if the given number of points are below the maximum
     * 		   number of points and above zero.
     *       | result == (points <= getMaxPoints()
     *       		&& points >= 0)
    */
    public boolean isValidPoints(int points) {
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
     * @param  hitPoints
     * 		   The number of hitpoints for this Unit.
     * @pre	   The given number of hitpoints must be a valid number of hitpoints
     * 		   for any Unit.
     * 		 | isValidPoints(hitPoints)
     * @post   The number of hitpoints of this Unit is equal to the given number of
     * 		   hitpoints.
     * 		 | new.getCurrentHitPoints() == hitPoints
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
     * @param  stamPoints
     * 		   The number of stamina points for this Unit.
     * @pre	   The given number of stamina points must be a valid number of stamina points
     * 		   for any Unit.
     * 		 | isValidPoints(stamPoints)
     * @post   The number of stamina points of this Unit is equal to the given number of
     * 		   stamina points.
     * 		 | new.getCurrentStamPoints() == stamPoints
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
     * Check whether the given orientation is a valid orientation for
     * any Unit.
     *
     * @param  orientation
     * 		   The orientation to check.
     * @return True if and only if the orientation is greater than zero and
     * 		   smaller than 2*PI.
     * 		 | result == (orientation >= 0
     * 				&& orientation <= 2*PI)
     */
    public static boolean isValidOrientation(float orientation) {
        return (orientation >= 0 && orientation <= 2 * Math.PI);
    }

    /**
     * Set the orientation of this Unit to the given orientation.
     *
     * @param  orientation
     * 		   The new orientation for this Unit.
     * @post   If the given orientation is a valid orientation for any Unit,
     * 		   the orientation of this new Unit is equal to the given
     * 		   orientation. Otherwise, the orientation of this new Unit is equal to
     * 		   0.5*PI.
     * 		 | if (isValidOrientation(orientation))
     * 		 |	 then new.getOrientation() == orientation
     * 		 |   else new.getOrientation() == 0.5*PI
     */
    @Raw
    public void setOrientation(float orientation) {
        if (isValidOrientation(orientation)) {
            this.orientation = orientation;
        } else {
            this.orientation = ((float) Math.PI) / 2;
        }
    }

    /**
     * Return the activity of this Unit.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.activity;
    }

    /**
     * Check whether the given activity is a valid activity for
     * any Unit.
     *
     * @param  activity
     * 		   The activity to check.
     * @return Always true.
     * 		 | result == true
     */
    public static boolean isValidActivity(IActivity activity) {
        return true;
    }

    /**
     * Set the activity of this Unit to the given activity.
     *
     * @param  activity
     * 		   The new activity for this Unit.
     * @throws IllegalArgumentException
     * 		   The given activity is not a valid activity for any
     *         Unit.
     *       | ! isValidActivity(getActivity())
     * @post   The activity of this new Unit is equal to
     * 		   the given activity.
     * 		 | new.getActivity() == activity
     */
    @Raw
    public void setActivity(IActivity activity) throws IllegalArgumentException {
        if (! isValidActivity(activity))
            throw new IllegalArgumentException();
        this.activity = activity;
    }
 
    /**
     * Return whether the Unit is carrying an object.
     */    
    public boolean isCarrying() {
        return iscarrying;
    }

    /**
     * Set the state of carrying of this Unit according to the given flag.
     *
     * @param  flag
     * 		   The carrying state to be registered.
     * @post   The new carrying state of this Unit is equal to the given flag.
     * 		 | this.iscarrying == flag
     */
    public void setCarrying(boolean flag) {
        this.iscarrying = flag;
    }    
    
    /**
     * Let this Unit carry an object.
     * 
     * @effect The carrying state of this Unit is set to true.
     * 		 | setCarrying(true)
     */
    private void startCarrying() {
    	setCarrying(true);
    }
   
    /**
     * Let this Unit stop carrying an object.
     * 
     * @effect The carrying state of this Unit is set to false.
     * 		 | setCarrying(false)
     */
    private void stopCarrying() {
    	setCarrying(false);
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
     * @param  toBeCarriedObject
     * 		   The object which needs to be carried by the unit.
     * @post   The carried object of this Unit will be equal to the given
     * 		   object, the carrying state of this Unit will be set on true
     * 		   and the object will not be considered as present in the world.
     * @throws IllegalArgumentException
     *         The given object cannot be carried by any
     *         Unit.
     *       | ! canBeCarried(getCarriedObject())
     */    
    public void carry(MovableWorldObject toBeCarriedObject) throws IllegalArgumentException {
        if (canBeCarried(toBeCarriedObject)) {
            this.carriedObject = toBeCarriedObject;
            this.setCarrying(true);
            toBeCarriedObject.unregister();
        } else {
            throw new IllegalArgumentException("This object cannot be carried");
        }
    }

    private boolean canBeCarried(MovableWorldObject object) {
        if (this.isCarrying())
        	return false;
        return (! (object instanceof Unit));
    }

    /**
     * Let the Unit drop the given carried object.
     *
     * @param  carriedObject
     * 		   The object which needs to be dropped by the unit.
     * @post   The carried object of this Unit will be set to null,
     * 		   the carrying state of this Unit will be set to false
     * 		   and the object will be considered as present in the world.
     */
    public void drop(MovableWorldObject carriedObject) {
        this.carriedObject = null;
        this.iscarrying = false;
        double[] locarray=this.getLocation().getArray();
        carriedObject.setLocation(locarray);
    }

    /**
     * Return whether the Unit is sprinting.
     */    
    public boolean isSprinting() {
        return issprinting;
    }

    /**
     * Set the state of sprinting of this Unit according to the given flag.
     *
     * @param  flag
     * 		   The sprinting state to be registered.
     * @post   The new sprinting state of this Unit is equal to the given flag.
     * 		 | this.issprinting == flag
     */
    public void setSprinting(boolean flag) {
        this.issprinting = flag;
    }    
    
    /**
     * Let this Unit sprint.
     * 
     * @effect The sprinting state of this Unit is set to true.
     * 		 | setSprinting(true)
     */
    private void startSprinting() {
    	setSprinting(true);
    }
   
    /**
     * Let this Unit stop sprinting.
     * 
     * @effect The sprinting state of this Unit is set to false.
     * 		 | setSprinting(false)
     */
    private void stopSprinting() {
    	setSprinting(false);
    }
    
    /**
     * No documentation required.
     */
    public void advanceTime(double dt) {
        if (this.getActivity().getId() == 0 && isDefaultBehaviorEnabled()) {
            behaveDefault();
        }
        this.getActivity().advanceActivityTime(dt);
    }

    public void behaveDefault(){
        this.setActivity(new Rest(this));
        //TODO flesh out this method
    }

    private boolean interruptCurrentAct(IActivity newActivity) {
        if (! this.getActivity().canBeInterruptedBy(newActivity)) 
        	return false;
        if (newActivity.getId() == 2 && (this.getActivity().getId() == 3 || this.getActivity().getId() == 6)) {
            this.hasPausedActivity = true;
            this.pausedActivity = this.getActivity();
        }
        return true;
    }

    /**
     * Let the Unit decently finish its current activity.
     * 
     * @post   The paused activity will be resumed if the Unit has a paused
     * 		   activity or its current activity will be set to nothing
     * 		   if the Unit does not have a paused activity.
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
     * Let the Unit conduct a generic labor at a specified cube position.
     * 
     * @param  targetCube
     * 		   The position of the cube where the Unit will work. 
     * @post   The Unit conducts work at the target cube.
     */      
    public void work(int[] targetCube) {
        Work work = new Work(this, targetCube);
        if (! interruptCurrentAct(work))
        	return;
        this.setActivity(work);
    }

    /**
     * Let the Unit attack another Unit.
     * 
     * @param  defender
     * 		   The Unit this Unit is attacking.
     * @post   The Unit conducts an attack to the defender and the defender conducts
     * 		   a defence against this Unit.
     * @throws IllegalArgumentException
     * 		   The attack cannot be conducted
     */      
    public void attack(Unit defender) throws IllegalArgumentException {
    	if (this.getFaction() == defender.getFaction())
    		throw new IllegalArgumentException("The Units cannot belong to"
    				+ "the same faction.");
    	int ACubeX = this.getLocation().getCubeLocation()[0];
    	int ACubeY = this.getLocation().getCubeLocation()[1];
    	int ACubeZ = this.getLocation().getCubeLocation()[2];
    	int DCubeX = defender.getLocation().getCubeLocation()[0];
    	int DCubeY = defender.getLocation().getCubeLocation()[1];
    	int DCubeZ = defender.getLocation().getCubeLocation()[2];
    	boolean hasneighboringX = false;
    	boolean hasneighboringY = false;
    	boolean hasneighboringZ = false;
    	for (int x = ACubeX-1; x < ACubeX+2; x++) {
    		if (x == DCubeX)
    			hasneighboringX = true;
    	}
        for (int y = ACubeY-1; y < ACubeY+2; y++) {
    		if (y == DCubeY)
    			hasneighboringY = true;
    	}
        for (int z = ACubeZ-1; z < ACubeZ+2; z++) {
    		if (z == DCubeZ)
    			hasneighboringZ = true;
    	}
        if (! (hasneighboringX && hasneighboringY && hasneighboringZ))
        	throw new IllegalArgumentException("Units can only attack other Units in the same or a neighboring cube of the game world");
    	if (defender.getActivity().getId() == 6)
    		throw new IllegalArgumentException("Units cannot attack other Units that are falling.");
        Attack attack = new Attack(this, defender);
        if (! this.interruptCurrentAct(attack))
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
     * Check whether the given faction is a valid faction for
     * any Unit.
     *  
     * @param  faction
     *         The faction to check.
     * @return True if and only if the given faction contains less
     * 		   than 50 Units.
     *       | result == (faction.getNumberOfUnits() < 50)
    */
	public boolean canHaveAsFaction(Faction faction) {
		return (faction.getNumberOfUnits() < 50);
	}     

	/**
	 * Set the faction of this Unit.
	 * 
	 * @post   The faction of this new Unit is equal to
	 *         a newly created faction if the maximum number of active
	 *		   factions is not reached yet, or is equal to the faction with the
	 *		   smallest number of Units if the maximum number of active
	 *		   factions has already been reached.
	 * @throws IllegalArgumentException
	 *         The faction with the smallest number of Units is not a
	 *         valid faction for any Unit.
	 *       | ! canHaveAsFaction(getFaction())
	 */
	@Raw		
	public void setFaction() throws IllegalArgumentException {
		if (this.getWorld().getNumberOfFactions() < 5) {
			Faction faction = new Faction(this, world);
			this.faction = faction;
		} else if (! canHaveAsFaction(this.getWorld().getSmallestFaction())) {
			throw new IllegalArgumentException("This faction has already reached its max amount of Units.");
		} else {
			this.faction = this.getWorld().getSmallestFaction();
			this.getWorld().getSmallestFaction().addUnit(this);
		}
	}	

	@Override
	public void unregister() {
		this.getWorld().getWorldMap().remove(this.getLocation());
	}
	
    public void dealDamage(double damage) {
    	//TODO implement loss of HP when fighting
        int intDamage = (int) Math.floor(damage);
        if (intDamage >= this.getCurrentHitPoints())
        	die();
        else {
        	this.setCurrentHitPoints(this.getCurrentHitPoints() - intDamage);
        	}
    }

    public void die() {
        this.unregister();
        this.getWorld().removeUnit(this);
        this.getFaction().removeUnit(this);
    }

    public String getRandomName(Random random){
        int index=random.nextInt(51);
        String[] namearr=new String[]{"Alfonso Addie","Terrence Truluck","Russel Rouse","Fritz Forst","Mckinley Marrow",
                "Sidney Suttles","Todd Tamura","Lee Lassiter","Sonny Sumpter","Tony Thames","Phil Pittman","Jonathon Jenning",
                "Clifford Conerly","Tod Tegeler","Lindsay Loken","Bud Bateman","Bradly Bedgood","Reed Rentas","Bernie Balch",
                "Ezra Eason","Erin Enriguez","Luther Lines","Samuel Stromberg","Enrique Esqueda","Paul Plowden","Francesco Fitton",
                "Moses Mcmurry","Mickey Mccoin","Romeo Risher","Maxwell Melder","Roman Riddell","Anthony Aycock","Anton Aquilino",
                "Gale Gerhardt","Oren Ogawa","Hershel Hyslop","Jared Jiminez","Hassan Haubrich","Armando Arner","Graham Glotfelty",
                "Monroe Martineau","Maynard Milani","Galen Gavin","Boyd Basta","Tyree Threatt","Florentino Forand","King Krom",
                "Ferdinand Fikes", "Gary Gander","Hans Harnish","Julie Allard","Arthur Decloedt"};
        return namearr[index];

    }
    
    /**
     * @invar  The experience of each Unit must be a valid experience for any
     *         Unit.
     *       | isValidXp(getXp())
     */
    
    /**
     * Initialize this new Unit with given experience.
     * 
     * @param  Xp
     *         The experience for this new Unit.
     * @post   If the given experience is a valid experience for any Unit,
     *         the experience of this new Unit is equal to the given
     *         experience. Otherwise, the experience of this new Unit is equal
     *         to 0.
     *       | if (isValidXp(Xp))
     *       |   then new.getXp() == Xp
     *       |   else new.getXp() == 0
     */
    public Unit(int Xp) {
      if (! isValidXp(Xp))
        Xp = 0;
      setXp(Xp);
    }
    
    /**
     * Return the experience of this Unit.
     */
    @Basic @Raw
    public int getXp() {
      return this.Xp;
    }
    
    /**
     * Check whether the given experience is a valid experience for
     * any Unit.
     *  
     * @param  Xp
     *         The experience to check.
     * @return 
     *       | result == xp>0
     */
    public static boolean isValidXp(int Xp) {
      return (Xp>=0);
    }
    
    /**
     * Set the experience of this Unit to the given experience.
     * 
     * @param  Xp
     *         The new experience for this Unit.
     * @post   If the given experience is a valid experience for any Unit,
     *         the experience of this new Unit is equal to the given
     *         experience.
     *       | if (isValidXp(Xp))
     *       |   then new.getXp() == Xp
     */
    @Raw
    public void setXp(int Xp) {
      if (isValidXp(Xp))
        this.Xp = Xp;
    }
    
    /**
     * Variable registering the experience of this Unit.
     */
    private int Xp;

    public void addXp(int xp){
        this.setXp(this.getXp()+xp);
    }
    
}