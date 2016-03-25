
package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.exceptions.UnitIllegalLocation;
import ogp.framework.util.Util;

/**
 * A class of a hillbilly Unit involving a name, an initial position, a weight,
 * agility, strength, toughness and a facility to enable the default behaviour.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt, Julie Allard
 * Julie Allard - Handelsingenieur in de beleidsinformatica  
 * Decloedt Arthur - Bachelor in de Informatica
 * https://github.com/julieallard/hillbillies.git
 */
public class Unit extends MovableWorldObject {
    /**
     * Initialize this new hillbilly Unit with given name, given initial position,
     * given weight, given agility, given strength, given toughness
     * and given default behaviour state.
     * 
     * @param  	name
     * 		 	The name for this Unit.
     * @param  	initialPosition
     *         	The initial position for this Unit, as an Array with 3 integers {x, y, z}.
     * @param  	weight
     *         	The weight for this Unit.
     * @param  	agility
     *         	The agility for this Unit.
     * @param  	strength
     *         	The strength for this Unit.
     * @param  	toughness
     *         	The toughness for this Unit.
     * @param  	enableDefaultBehavior
     *         	The state of behaviour for this Unit.
     * @post   	The new name of this new Unit is equal to the given name.
     * 		  |	new.getName() == name
     * @post   	The new initial position of this new Unit is set to the given initial
     * 		   	position.
     *        |	new.setlocation(initialPosition) == location
     * @post   	If the given weight is not below 25 and not above 100, the initial
     * 			weight of this new Unit is equal to the given weight. Otherwise, its
     * 			initial weight is equal to 25, respectively 100.
     * @post   	If the given agility is not below 25 and not above 100, the initial
     * 			agility of this new Unit is equal to the given agility. Otherwise, its
     * 			initial agility is equal to 25, respectively 100.
     * @post   	If the given strength is not below 25 and not above 100, the initial
     * 			strength of this new Unit is equal to the given strength. Otherwise, its
     * 			initial strength is equal to 25, respectively 100.
     * @post   	If the given toughness is not below 25 and not above 100, the initial
     * 			toughness of this new Unit is equal to the given toughness. Otherwise, its
     * 			initial toughness is equal to 25, respectively 100.
     * @post	The initial state of behavior of this new Unit is equal to the given
     * 			flag.
     * @throws	IllegalArgumentException()
     * 			The given initial name is not a valid name for any Unit.
     * 		  | ! isValidName(name)
     * @throws UnitIllegalLocation()
     * 			The given initial position is not a valid location for any Unit.
     * 		  | ! isValidLocation(initialPosition)  
     * @invar  	The location of each Unit must be a valid location for any Unit.
     *        | isValidlocation(getlocation())
     */

    public Unit(String name, double[] initialPosition, int weight, int agility, int strength, int toughness,
                boolean enableDefaultBehavior) throws UnitIllegalLocation  {
        this.setlocation(initialPosition);
        if (weight < 25) {this.setWeight(25);}
        else if (weight > 100) {this.setWeight(100);}
        else {this.setWeight(weight);}
        if (agility < 25) {this.setAgility(25);}
        else if (agility > 100) {this.setAgility(100);}
        else {this.setAgility(agility);}
        if (strength < 25) {this.setStrength(25);}
        else if (strength > 100) {this.setStrength(100);}
        else {this.setStrength(strength);}
        if (toughness < 25) {this.setToughness(25);}
        else if (toughness > 100) {this.setToughness(100);}
        else {this.setToughness(toughness);}
        this.setDefaultbehavior(enableDefaultBehavior);
        this.hitpoints = getMaxHitPoints();
        this.staminapoints = getMaxStaminaPoints();
        this.orientation = (float) (0.5*Math.PI);
        this.setName(name);

    }
    private String name;
    private int weight;
    private int agility;
    private int strength;
    private int toughness;
    private boolean defaultbehaviorenabled;
    private int hitpoints;
    private int staminapoints;
    /**
     * Return the name of this Unit.
    */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of this Unit to the given name.
     * 
     * @param	newName
     *       	The new name for this Unit.
     * @post   	The new name of this new Unit is equal to the given name.
     * 		  |	new.getName == name	
     * @throws	IllegalArgumentException()
     * 			The given initial name is not a valid name for any Unit.
     * 		  | ! isValidName(name)
     */

    public void setName(String newName) throws IllegalArgumentException {
        if (! isValidName(newName)) {
            throw new IllegalArgumentException("This is an invalid name.");
        } else {
            this.name = newName;
        }
    }


    /**
     * Check whether the given name is a valid name for all Units.
     * @param	newName
     * 			The name to check.
     * @return	True if and only if the given name contains at least two characters, if
     * 			the first character is an uppercase letter and if the all characters are
     * 			uppercase or lowercase letters, single or double quotes or spaces.
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
     * @param	newValue
     *       	The new weight for this Unit.
     * @post	If the given weight is not below 1 and not above 200, the new
     * 			weight of this Unit is equal to the given weight. Otherwise, its
     * 			new weight is equal to 1, respectively 200.
     */
    public void setWeight(int newValue) {
        if (newValue < 1) {this.setWeight(1);
        } else if (newValue > 200) {this.setWeight(200);
        } else if (newValue < 0.5*(this.getStrength() + this.getAgility())) {
            this.weight = (int) (0.5*(this.getStrength() + this.getAgility()));
        } else {this.weight = newValue;}
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
     * @param	newValue
     *       	The new agility for this Unit.
     * @post	If the given agility is not below 1 and not above 200, the new
     * 			agility of this Unit is equal to the given agility. Otherwise, its
     * 			new agility is equal to 1, respectively 200.
     */
    public void setStrength(int newValue) {
        if (newValue < 1) {this.strength = 1;
        } else if (newValue > 200) {this.strength = 200;
        } else {this.strength = newValue;}
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
     * @param	newValue
     *       	The new strength for this Unit.
     * @post	If the given strength is not below 1 and not above 200, the new
     * 			strength of this Unit is equal to the given strength. Otherwise, its
     * 			new strength is equal to 1, respectively 200.
     */
    public void setAgility(int newValue) {
        if (newValue < 1) {this.agility = 1;
        } else if (newValue > 200) {this.agility = 200;
        } else {this.agility = newValue;}
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
     * @param	newValue
     *       	The new toughness for this Unit.
     * @post	If the given toughness is not below 1 and not above 200, the new
     * 			toughness of this Unit is equal to the given toughness. Otherwise, its
     * 			new toughness is equal to 1, respectively 200.
     */
    public void setToughness(int newValue) {
        if (newValue < 1) {this.toughness = 1;
        } else if (newValue > 200) {this.toughness = 200;
        } else {this.toughness = newValue;}
    }

    /**
     * Return the maximum number of hitpoints of this Unit.
     */
    public int getMaxHitPoints() {
        return 200*(this.getWeight()/100)*(this.getToughness()/100);
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
        return 200*(this.getWeight()/100)*(this.getToughness()/100);
    }
    /**
     * Set the number of stamina points of this Unit to the given stamina points.
     * 
     * @param 	stamPoints
     * 			The number of stamina points for this Unit.
     * @pre		The given number of stamina points must be a valid number for any Unit.
     * @post	The new number of stamina points is equal to the given number of
     * 			stamina points.
     * 		  | new.getCurrentStaminaPoints() == balance
     */
    public void setCurrentStaminaPoints(int stamPoints) {
        if (stamPoints<=getMaxStaminaPoints()&&stamPoints>=0){
            this.staminapoints=stamPoints;
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
    @Basic @Raw
    public double[] getlocation() {
      return this.location;
    }
    
    /**
     * Check whether the given location is a valid location for
     * any Unit.
     *  
     * @param  location
     *         The location to check.
     * @return 
     *       | result == location.length==3 and location[n] in (0;50) voor n=1,2,3
     */
    public static boolean isValidlocation(double[] location) {
        if (!(location.length==3)) {return false;}
        for (double locationPart:location){
            if (locationPart<0 || locationPart>50){
                return false;
            }
        }
        return true;
        
    }
    
    /**
     * Set the location of this Unit to the given location.
     * 
     * @param  location
     *         The new location for this Unit.
     * @post   The location of this new Unit is equal to
     *         the given location.
     *       | new.getlocation() == location
     * @throws UnitIllegalLocation
     *         The given location is not a valid location for any
     *         Unit.
     *       | ! isValidlocation(getlocation())
     */
    @Raw
    public void setlocation(double[] location) 
          throws UnitIllegalLocation {
      if (! isValidlocation(location))
        throw new UnitIllegalLocation();
      this.location = location;
    }
    
    /**
     * Variable registering the location of this Unit.
     */
    private double[] location;
    
    /**
     * Return the orientation of this Unit.
     */
    @Basic @Raw
    public float getorientation() {
        return this.orientation;
    }

    /**
     * Check whether the given orientation is a valid orientation for
     * any Unit.
     *
     * @param  orientation
     *         The orientation to check.
     * @return
     *       | result == (orientation>=0 and orientation<=2*PI
     */
    public static boolean isValidorientation(float orientation) {
        return (orientation>=0 && orientation<=2*Math.PI);
    }
    /**
     * Set the orientation of this Unit to the given orientation.
     *
     * @param  orientation
     *         The new orientation for this Unit.
     * @post   If the given orientation is a valid orientation for any Unit,
     *         the orientation of this new Unit is equal to the given
     *         orientation.
     *       | if (isValidorientation(orientation))
     *       |   then new.getorientation() == orientation
     */
    @Raw
    public void setorientation(float orientation) {
        if (isValidorientation(orientation)){
            this.orientation = orientation;}
        else {this.orientation= ((float) Math.PI)/2;}
    }

    /**
     * Float registering the orientation of this unit
     */
    private float orientation;

    /**
     * Return the activity of this Unit.
     */
    @Basic @Raw
    public IActivity getActivity() {
      return this.Activity;
    }

    /**
     * Check whether the given activity is a valid activity for
     * any Unit.
     *
     * @param  activity
     *         The activity to check.
     * @return
     *       | result ==
     */
    public static boolean isValidActivity(IActivity activity) {
      return true;
    }

    /**
     * Set the activity of this Unit to the given activity.
     *
     * @param  Activity
     *         The new activity for this Unit.
     * @post   The activity of this new Unit is equal to
     *         the given activity.
     *       | new.getActivity() == Activity
     * @throws IllegalArgumentException
     *         The given activity is not a valid activity for any
     *         Unit.
     *       | ! isValidActivity(getActivity())
     */
    @Raw
    public void setActivity(IActivity Activity)
          throws IllegalArgumentException {
      if (! isValidActivity(Activity))
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
    public boolean isDefaultBehaviorEnabled(){
        return defaultbehaviorenabled;
    }
    
    /**
     * Set the state equal to the given flag.
     * @param	enabled
     * 			The default behavior state of the unit.
     * @post	The default behavior state of the unit is equal to the given flag.
     */
    public void setDefaultbehavior(boolean enabled){
        this.defaultbehaviorenabled=enabled ;
    }

    private boolean isCarrying=false;

    public boolean isCarrying() {
        return isCarrying;
    }

    private void setCarrying(boolean carrying) {
        isCarrying = carrying;

    }
    private MovableWorldObject carriedObject;

    public void carry(Object object){
        MovableWorldObject carrObject=((MovableWorldObject) object);
        if (canBeCarried(carrObject)) {
            this.carriedObject = carrObject;
            this.setCarrying(true);
        }
        else {throw new IllegalArgumentException("supplied Object cannot be carried, pls kill urself");}
    }
    private boolean canBeCarried(MovableWorldObject object){
        if(this.isCarrying()) return false;
        return(!(object instanceof Unit));
    }


    public void advanceTime(double dt){
        if (this.getActivity()==null)
    }
}
