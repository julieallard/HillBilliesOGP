
package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.activities.Attack;
import hillbillies.model.activities.Defend;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.activities.Work;
import hillbillies.model.exceptions.UnitIllegalLocation;

/**
 * A class of a hillbilly Unit involving a name, an initial position, a weight,
 * agility, strength, toughness and a facility to enable the default behaviour.
 * 
 * @version 0.9 alpha
 * @author  Decloedt Arthur - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/hillbillies.git
 */
public class Unit extends MovableWorldObject {

    /**
     * Initialize this new hillbilly Unit with given name, given initial position,
     * given weight, given agility, given strength, given toughness
     * and given default behaviour state.
     *
     * @param name                  The name for this Unit.
     * @param initialPosition       The initial position for this Unit, as an Array with 3 integers {x, y, z}.
     * @param weight                The weight for this Unit.
     * @param agility               The agility for this Unit.
     * @param strength              The strength for this Unit.
     * @param toughness             The toughness for this Unit.
     * @param enableDefaultBehavior The state of behaviour for this Unit.
     * @throws UnitIllegalLocation() The given initial position is not a valid location for any Unit.
     *                               | ! isValidLocation(initialPosition)
     * @post The new name of this new Unit is equal to the given name.
     * |	new.getName() == name
     * @post If the given weight is not below 25 and not above 100, the initial
     * weight of this new Unit is equal to the given weight. Otherwise, its
     * initial weight is equal to 25, respectively 100.
     * @post If the given agility is not below 25 and not above 100, the initial
     * agility of this new Unit is equal to the given agility. Otherwise, its
     * initial agility is equal to 25, respectively 100.
     * @post If the given strength is not below 25 and not above 100, the initial
     * strength of this new Unit is equal to the given strength. Otherwise, its
     * initial strength is equal to 25, respectively 100.
     * @post If the given toughness is not below 25 and not above 100, the initial
     * toughness of this new Unit is equal to the given toughness. Otherwise, its
     * initial toughness is equal to 25, respectively 100.
     * @post The initial state of behavior of this new Unit is equal to the given
     * flag.
     * @throws IllegalArgumentException() The given initial name is not a valid name for any Unit.
     * | ! isValidName(name)
     * @invar The location of each Unit must be a valid location for any Unit.
     * | isValidlocation(getlocation())
     */
    public Unit(String name, double x, double y, double z, int weight, int agility, int strength, int toughness,
                boolean enableDefaultBehavior,World world) throws UnitIllegalLocation {
        this.setWorld();
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
        this.setDefaultbehavior(enableDefaultBehavior);
        this.hitpoints = getMaxHitPoints();
        this.staminapoints = getMaxStaminaPoints();
        this.orientation = (float) (0.5 * Math.PI);
        this.setName(name);
        this.setActivity(null);
        this.setFaction();
        this.setWorld();
    }

    private boolean isPausedActivity;
    private IActivity pausedActivity;    
    
    private String name;
    private int weight;
    private int agility;
    private int strength;
    private int toughness;
    private boolean defaultbehaviorenabled;
    private int hitpoints;
    private int staminapoints;
    private Faction faction;

    /**
     * Return the name of this Unit.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of this Unit to the given name.
     *
     * @param    newName The new name for this Unit.
     * @post The new name of this new Unit is equal to the given name.
     * |	new.getName == name
     * @throws IllegalArgumentException() The given initial name is not a valid name for any Unit.
     * | ! isValidName(name)
     */

    public void setName(String newName) throws IllegalArgumentException {
        if (!isValidName(newName)) {
            throw new IllegalArgumentException("This is an invalid name.");
        } else {
            this.name = newName;
        }
    }


    /**
     * Check whether the given name is a valid name for all Units.
     *
     * @param    newName The name to check.
     * @return True if and only if the given name contains at least two characters, if
     * the first character is an uppercase letter and if the all characters are
     * uppercase or lowercase letters, single or double quotes or spaces.
     */

    public boolean isValidName(String newName) {
        return (newName.length() >= 2) && Character.isUpperCase(newName.charAt(0)) && ("[A-Z|a-z|\"|\'|\\s]*".matches(newName));
    }


    /**
     * Return the weight of this Unit.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Set the weight of this Unit to the given value.
     *
     * @param    newValue The new weight for this Unit.
     * @post If the given weight is not below 1 and not above 200, the new
     * weight of this Unit is equal to the given weight. Otherwise, its
     * new weight is equal to 1, respectively 200.
     */
    public void setWeight(int newValue) {
        if (newValue < 1) {
            this.setWeight(1);
        } else if (newValue > 200) {
            this.setWeight(200);
        } else if (newValue < 0.5 * (this.getStrength() + this.getAgility())) {
            this.weight = (int) (0.5 * (this.getStrength() + this.getAgility()));
        } else {
            this.weight = newValue;
        }
    }

    /**
     * Return the strength of this Unit.
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Set the agility of this Unit to the given value.
     *
     * @param    newValue The new agility for this Unit.
     * @post If the given agility is not below 1 and not above 200, the new
     * agility of this Unit is equal to the given agility. Otherwise, its
     * new agility is equal to 1, respectively 200.
     */
    public void setStrength(int newValue) {
        if (newValue < 1) {
            this.strength = 1;
        } else if (newValue > 200) {
            this.strength = 200;
        } else {
            this.strength = newValue;
        }
    }

    /**
     * Return the agility of this Unit.
     */
    public int getAgility() {
        return this.agility;
    }

    /**
     * Set the strength of this Unit to the given value.
     *
     * @param    newValue The new strength for this Unit.
     * @post If the given strength is not below 1 and not above 200, the new
     * strength of this Unit is equal to the given strength. Otherwise, its
     * new strength is equal to 1, respectively 200.
     */
    public void setAgility(int newValue) {
        if (newValue < 1) {
            this.agility = 1;
        } else if (newValue > 200) {
            this.agility = 200;
        } else {
            this.agility = newValue;
        }
    }

    /**
     * Return the toughness of this Unit.
     */
    public int getToughness() {
        return this.toughness;
    }

    /**
     * Set the toughness of this Unit to the given value.
     *
     * @param    newValue The new toughness for this Unit.
     * @post If the given toughness is not below 1 and not above 200, the new
     * toughness of this Unit is equal to the given toughness. Otherwise, its
     * new toughness is equal to 1, respectively 200.
     */
    public void setToughness(int newValue) {
        if (newValue < 1) {
            this.toughness = 1;
        } else if (newValue > 200) {
            this.toughness = 200;
        } else {
            this.toughness = newValue;
        }
    }

    /**
     * Return the maximum number of hitpoints of this Unit.
     */
    public int getMaxHitPoints() {
        return 200 * (this.getWeight() / 100) * (this.getToughness() / 100);
    }

    /**
     * Return the hitpoints of this Unit.
     */
    public int getCurrentHitPoints() {
        return this.hitpoints;
    }

    /**
     * Return the maximum number of stamina points of this Unit.
     */
    public int getMaxStaminaPoints() {
        return 200 * (this.getWeight() / 100) * (this.getToughness() / 100);
    }

    /**
     * Set the number of hitpoints of this Unit to the given hitpoints.
     *
     * @param hitPoints The number of hitpoints for this Unit.
     * @pre The given number of hitpoints must be a valid number for any Unit.
     * @post The new number of hitpoints is equal to the given number of
     * hitpoints.
     * | new.getCurrentHitPoints() == balance
     */
    public void setCurrentHitPoints(int hitPoints) {
        if (hitPoints <= getMaxHitPoints() && hitPoints >= 0) {
            this.hitpoints = hitPoints;
        }
    }

        /**
         * Set the number of stamina points of this Unit to the given stamina points.
         *
         * @param    stamPoints
         * 			The number of stamina points for this Unit.
         * @pre The given number of stamina points must be a valid number for any Unit.
         * @post The new number of stamina points is equal to the given number of
         * 			stamina points.
         * 		  | new.getCurrentStaminaPoints() == balance
         */

    public void setCurrentStaminaPoints(int stamPoints) {
        if (stamPoints <= getMaxStaminaPoints() && stamPoints >= 0) {
            this.staminapoints = stamPoints;
        }

    }

    /**
     * Return the stamina points of this Unit.
     */
    public int getCurrentStaminaPoints() {
        return this.staminapoints;
    }


    /**
     * Return the location of this Unit.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return this.location;
    }

    /**
     * Check whether the given location is a valid location for
     * any Unit.
     *
     * @param location The location to check.
     *                 /
     */
    public static boolean isValidlocation(VLocation location) {


        return VLocation.isValidLocation(location);

    }

    /**
     * Set the location of this Unit to the given location.
     *
     * @param location The new location for this Unit.
     * @throws UnitIllegalLocation The given location is not a valid location for any
     *                             Unit.
     *                             | ! isValidlocation(getlocation())
     * @post The location of this new Unit is equal to
     * the given location.
     * | new.getlocation() == location
     */
    @Raw
    public void setLocation(VLocation location)
            throws UnitIllegalLocation {
        if (!isValidlocation(location))
            throw new UnitIllegalLocation();
        this.location = location;
    }

    /**
     * Variable registering the location of this Unit.
     */
    private VLocation location;

    /**
     * Return the orientation of this Unit.
     */
    @Basic
    @Raw
    public float getorientation() {
        return this.orientation;
    }

    /**
     * Check whether the given orientation is a valid orientation for
     * any Unit.
     *
     * @param orientation The orientation to check.
     * @return | result == (orientation>=0 and orientation<=2*PI
     */
    public static boolean isValidorientation(float orientation) {
        return (orientation >= 0 && orientation <= 2 * Math.PI);
    }

    /**
     * Set the orientation of this Unit to the given orientation.
     *
     * @param orientation The new orientation for this Unit.
     * @post If the given orientation is a valid orientation for any Unit,
     * the orientation of this new Unit is equal to the given
     * orientation.
     * | if (isValidorientation(orientation))
     * |   then new.getorientation() == orientation
     */
    @Raw
    public void setorientation(float orientation) {
        if (isValidorientation(orientation)) {
            this.orientation = orientation;
        } else {
            this.orientation = ((float) Math.PI) / 2;
        }
    }

    /**
     * Float registering the orientation of this unit
     */
    private float orientation;

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
     * @param activity The activity to check.
     * @return | result == true
     */
    public static boolean isValidActivity(IActivity activity) {
        return true;
    }

    /**
     * Set the activity of this Unit to the given activity.
     *
     * @param Activity The new activity for this Unit.
     * @throws IllegalArgumentException The given activity is not a valid activity for any
     *                                  Unit.
     *                                  | ! isValidActivity(getActivity())
     * @post The activity of this new Unit is equal to
     * the given activity.
     * | new.getActivity() == Activity
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
     * @param    enabled The default behavior state of the unit.
     * @post The default behavior state of the unit is equal to the given flag.
     */
    public void setDefaultbehavior(boolean enabled) {
        this.defaultbehaviorenabled = enabled;
    }

    private boolean isCarrying = false;

    public boolean isCarrying() {
        return isCarrying;
    }

    private void setCarrying(boolean carrying) {
        isCarrying = carrying;

    }

    private MovableWorldObject carriedObject;

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

	public void setFaction() throws IllegalArgumentException {
		if (World.getNumberOfFactions() < 5) {
			Faction faction = new Faction(this);
			this.faction = faction;
		} else if (!canHaveAsFaction(World.getSmallestFaction())) {
			throw new IllegalArgumentException("Error");
		} else {
			this.faction = World.getSmallestFaction();
			World.getSmallestFaction().addUnit(this);
		}
	}
	
	public Faction getFaction() {
		return this.faction;
	}
    
	public boolean canHaveAsFaction(Faction faction) {
		if (faction.getNumberOfUnits() < 50) {
			return true;
		} else {
			return false;
		}
	}
	
    @Override
    public void setLocation(double x, double y, double z) throws UnitIllegalLocation {
        VLocation location = new VLocation(x, y, z, this);
    	this.setLocation(location);
        this.register(location);
    }

    @Override
    public void register(VLocation location) {
    	WorldMap.put(location, this);
    }

	public void setWorld() throws IllegalArgumentException {
		if (!canHaveAsWorld(World)) {throw new IllegalArgumentException("Error");
		} else {
			this.World = World;
			World.addUnit(this);
		}
	}        
    
    /**
     * Return the World of this Unit.
     */
    @Basic @Raw @Immutable @Override
    public World getWorld() {
      return this.World;
    }

    /**
     * Check whether this Unit can have the given World as its World.
     *
     * @param  World
     *         The World to check.
     * @return
     *       | result ==
     */
    @Raw
    public boolean canHaveAsWorld(World world) {
      if (World.getNumberOfUnits() < 100) {
    	  return true;
      } else {
    	  return false;
      }
    }

    /**
     * Variable registering the World of this Unit.
     */
    private final World World;
    
}


