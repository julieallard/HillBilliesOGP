
package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.activities.Attack;
import hillbillies.model.activities.Defend;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.activities.Work;
import hillbillies.model.exceptions.ExceptionName_Java;
import hillbillies.model.exceptions.Raw;
import hillbillies.model.exceptions.UnitIllegalLocation;
import hillbillies.model.exceptions.property_type;

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
 *       | isValidStrength(getStrength())
 * @invar  The agility of each Unit must be a valid agility for any
 *         Unit.
 *       | isValidAgility(getAgility()) 
 * @invar  The toughness of each Unit must be a valid toughness for any
 *         Unit.
 *       | isValidToughness(getToughness()) 
 
 
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
     * @post The initial state of behavior of this new Unit is equal to the given
     * flag.
     * @throws UnitIllegalLocation() 
     * 		   The given location is not a valid location for any Unit.
     * 		 | ! isValidLocation(location)
     */
    public Unit(String name, double x, double y, double z, int weight, int strength,
    		int agility, int toughness, boolean enableDefaultBehavior, world world)
            throws UnitIllegalLocation, IllegalArgumentExpression {
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
        this.setWorld();
        this.hitpoints = getMaxPoints();
        this.staminapoints = getMaxPoints();
        this.orientation = (float) (0.5 * Math.PI);
        this.setActivity(null);
        this.setFaction();
    }

    private String name;
    private VLocation location;
    private int weight;
    private int agility;
    private int strength;
    private int toughness;
    private boolean defaultbehaviorenabled;
    private final world this.getWorld();
    
    private int hitpoints;
    private int staminapoints;
    private float orientation;
    private Faction faction;
    private boolean isPausedActivity;
    private IActivity pausedActivity; 
    
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

    @Override
    public void setLocation(double x, double y, double z) throws UnitIllegalLocation {
        VLocation location = new VLocation(x, y, z, this);
    	this.setLocation(location);
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
        if (!isValidLocation(location))
            throw new UnitIllegalLocation();
        this.location = location;
        this.register(location);
    }
    
    @Override
    public void register(VLocation location) {
    	this.getWorld().getWorldMap().put(location, this);
    }
    
    public 
    
    /**
     * Return the weight of this Unit.
     */
    public int getWeight() {
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
     *       | if (weight < 1)
     *       |   then new.getWeight() == 1
     *       |	 else if (weight > 200)
     *       |	 then new.getWeight() == 200
     *       |	 else if (weight < 0.5 * (this.getStrength() + this.getAgility()))
     *       |	 then new.getWeight() == (0.5 * (this.getStrength() + this.getAgility()))
     *       |	 else new.getWeight() == weight
     */
    public void setWeight(int weight) {
        if (weight < 1) {
            this.setWeight(1);
        } else if (weight > 200) {
            this.setWeight(200);
        } else if (weight < 0.5 * (this.getStrength() + this.getAgility())) {
            this.weight = (int) (0.5 * (this.getStrength() + this.getAgility()));
        } else {
            this.weight = weight;
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
     *       | if (strength < 1)
     *       |   then new.getStrength() == 1
     *       |	 else if (strength > 200)
     *       |	 then new.getStrength() == 200
     *       |	 else new.getStrength() == strength
     */
    public void setStrength(int strength) {
        if (strength < 1) {
            this.strength = 1;
        } else if (strength > 200) {
            this.strength = 200;
        } else {
            this.strength = strength;
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
     *       | if (agility < 1)
     *       |   then new.getAgility() == 1
     *       |	 else if (agility > 200)
     *       |	 then new.getAgility() == 200
     *       |	 else new.getAgility() == agility
     */
    public void setAgility(int agility) {
        if (agility < 1) {
            this.agility = 1;
        } else if (agility > 200) {
            this.agility = 200;
        } else {
            this.agility = agility;
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
     *       | if (toughness < 1)
     *       |   then new.getToughness() == 1
     *       |	 else if (toughness > 200)
     *       |	 then new.getToughness() == 200
     *       |	 else new.getToughness() == toughness
     */
    public void setToughness(int toughness) {
        if (toughness < 1) {
            this.toughness = 1;
        } else if (toughness > 200) {
            this.toughness = 200;
        } else {
            this.toughness = toughness;
        }
    }

    /**
     * Return the maximum number of hitpoints or stamina points of this Unit.
     */
    public int getMaxPoints() {
        return 200 * (this.getWeight() / 100) * (this.getToughness() / 100);
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
    public static boolean isValidPoints(int points) {
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
     * @return
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
     * 		   orientation.
     * 		 | if (isValidorientation(orientation))
     * 		 |	 then new.getOrientation() == orientation
     */
    @Raw
    public void setorientation(float orientation) {
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
        return this.Activity;
    }

    /**
     * Check whether the given activity is a valid activity for
     * any Unit.
     *
     * @param  activity
     * 		   The activity to check.
     * @return
     * 		 | result == true
     */
    public static boolean isValidActivity(IActivity activity) {
        return true;
    }

    /**
     * Set the activity of this Unit to the given activity.
     *
     * @param  Activity
     * 		   The new activity for this Unit.
     * @throws IllegalArgumentException
     * 		   The given activity is not a valid activity for any
     *         Unit.
     *       | ! isValidActivity(getActivity())
     * @post   The activity of this new Unit is equal to
     * 		   the given activity.
     * 		 | new.getActivity() == Activity
     */
    @Raw
    public void setActivity(IActivity Activity)
            throws IllegalArgumentException {
        if (!isValidActivity(Activity))
            throw new IllegalArgumentException();
        this.Activity = Activity;
    }

    /**
     * Variable registering the activity of this Unit.
     */
    private IActivity Activity;

    /**
     * Check whether the default behavior is enabled.
     */
    public boolean isDefaultBehaviorEnabled() {
        return defaultbehaviorenabled;
    }

    /**
     * Set the state equal to the given flag.
     *
     * @param  enabled
     * 		   The default behavior state of the unit.
     * @post   The default behavior state of the unit is equal to the given flag.
     */
    public void setDefaultBehavior(boolean enabled) {
        this.defaultbehaviorenabled = enabled;
    }

    private boolean iscarrying = false;
    private MovableWorldObject carriedObject;
    
    public boolean isCarrying() {
        return iscarrying;
    }

    private void setCarrying(boolean carrying) {
        iscarrying = carrying;
    }

    protected void carry(MovableWorldObject toBeCarriedObject) {
        if (canBeCarried(toBeCarriedObject)) {
            this.carriedObject = toBeCarriedObject;
            this.setCarrying(true);
            toBeCarriedObject.unregister();
        } else {
            throw new IllegalArgumentException("supplied Object cannot be carried");
        }
    }

    private boolean canBeCarried(MovableWorldObject object) {
        if (this.isCarrying()) return false;
        return (!(object instanceof Unit));
    }

    public void drop(MovableWorldObject CarriedObject) {
        this.carriedObject = null;
        this.isCarrying = false;
        CarriedObject.setLocation(this.getLocation());
    }

    public void advanceTime(double dt) {
        if (this.getActivity().getId() == 0 && isDefaultBehaviorEnabled()) {
            behaveDefault(dt);
        }
        this.getActivity().advanceActivityTime(dt);
    }

    private boolean interruptCurrentAct(IActivity newactivity) {
        if (!this.getActivity().canBeInterruptedBy(newactivity)) return false;
        if (newactivity.getId() == 2 && (this.getActivity().getId() == 3 || this.getActivity().getId() == 6)) {
            this.isPausedActivity = true;
            this.pausedActivity = this.getActivity();
        }
        return true;
        //todo split in different functions coz this shit sucks
    }

    public void work(int[] targetCube) {
        VLocation targetLocation = new VLocation(targetCube[0], targetCube[1], targetCube[2], this);
        Work work = new Work(this, targetLocation);
        if (!interruptCurrentAct(work)) return;
        this.setActivity(work);
    }

    public void activityFinished() {
        if (!this.isPausedActivity) {
            this.setActivity(new NoActivity());
            return;
        }
        this.setActivity(this.pausedActivity);
        this.isPausedActivity = false;
        this.pausedActivity = null;
    }

    public void attack(Unit defender) {
        //todo check if can attack and if defender is not falling
        Attack attack = new Attack(this, defender);
        if (!this.interruptCurrentAct(attack)) return;
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
	 *		   factions is not reached and is equal to the faction with the
	 *		   smallest number of units if the maximum number of active
	 *		   factions is already reached.
	 * @throws IllegalArgumentException
	 *         The given property_name_Eng is not a valid property_name_Eng for any
	 *         object_name_Eng.
	 *       | ! isValidPropertyName_Java(getPropertyName_Java())
	 */
	@Raw	
	
	public void setFaction() throws IllegalArgumentException {
		if (this.getWorld().getNumberOfFactions() < 5) {
			Faction faction = new Faction(this);
			this.faction = faction;
		} else if (! canHaveAsFaction(this.getWorld().getSmallestFaction())) {
			throw new IllegalArgumentException("This faction already reached its max amount of Units.");
		} else {
			this.faction = this.getWorld().getSmallestFaction();
			this.getWorld().getSmallestFaction().addUnit(this);
		}
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
     * @return
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
			throw new IllegalArgumentException("This world already reached its max amount of Units.");
		this.world = world;
		this.getWorld().addUnit(this);	
	}  
    
}