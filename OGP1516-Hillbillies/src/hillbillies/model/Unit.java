
package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.Util;

/**
 * A class of a hillbilly Unit involving a name, an initial position, a weight,
 * agility, strength, toughness and a facility to enable the default behaviour.
 * 
 * @version 1.0
 * @author  Arthur Decloedt, Julie Allard
 * Julie Allard - Handelsingenieur in de beleidsinformatica  
 * Decloedt Arthur - Bachelor in de Informatica
 * https://github.com/julieallard/hillbillies.git
 */
public class Unit {
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
     * @throws  UnitIllegalLocation()
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
        this.setactivity("none");

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
        for (double locationPart:location
             ) {if (locationPart<0 || locationPart>50){return false;}
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
    private boolean isSprinting=false;

    protected void setIsSprinting(boolean issprinting){
        this.isSprinting = issprinting;
    }
    public boolean isSprinting(){
        return this.isSprinting;
    }
    /**
     * Variable registering the orientation of this Unit.
     */

    private float orientation;
    /** 
     * Variable registering the current movement of an object
     */
    private Movement currentMovement;
    public double[] currentDestination;
    private boolean movementPaused;

    /**
     * Let the Unit move towards the given destination.
     * 
     * @param	destination
     * 			The destination where the Unit will be heading to.
     * @effect	If the Unit is not resting while there is still time left to rest until
     * 			its first hitpoint, and if it is not attacking or defending, the
     * 			Unit moves towards the given destination.
     */

    
    public void moveToAdjacent(double[] destination){
        if ((this.getactivity().equals("resting")&&this.getActivityTimeLeft()>0)||this.getactivity().equals("moving")||
                this.getactivity().equals("defending")||this.getactivity().equals("attacking")){
            return;}
        this.currentDestination=destination;
        double[] startloc= (this.getlocation()).clone();
        this.currentMovement=new Movement(this,startloc,destination,this.isSprinting);
        this.activity="moving";
        this.movementPaused=false;
    }

    /**
     * Let the Unit move towards the given destination.
     * 
     * @param	destination
     * 			The destination cube where the Unit will be heading to.
     * @effect	If the Unit is not resting while there is still time left to rest until
     * 			its first hitpoint, and if it is not attacking or defending, the
     * 			Unit moves towards the given destination.
     */

    public void moveTo(double[] destination){
        if ((this.getactivity().equals("resting")&&this.getActivityTimeLeft()>0)||
                this.getactivity().equals("defending")||this.getactivity().equals("attacking")){
            return;}
        this.currentDestination=destination;
        double[] startloc= (this.getlocation()).clone();
        this.currentMovement=new Movement(this,startloc,destination,this.isSprinting);
        this.activity="moving";
        this.movementPaused=false;
    }
    public void advanceTime(double dt) {

        //this makes sure the garbage collector can delete the movement if it is no longer used
        if(!this.isMovementPaused()&&!(this.getactivity().equals("moving"))&&!(this.currentMovement==null)){
            this.currentMovement=null;
        }
        setTimeLeftSprinting();
        String activity = getactivity();
        switch (activity) {
            case "moving":
                moveFor(dt);
                break;
            case "resting":
                restFor(dt);
                break;
            case "working":
                workFor(dt);
                break;
            case "defending":
                defendFor(dt);
                return;
            case "attacking":
                attackFor(dt);
            default:
                spendTimeFor(dt);
                break;
        }
    }

    
    /**
     * Let the Unit continue the activity of moving for the given time.
     * 
     * @param	dt
     * 			The time in seconds for which the Unit has to continue its movement.
     * @effect	The Unit is dt seconds further in its movement.
     */    
    protected void moveFor(double dt){
        this.currentMovement.advanceTime(this,dt);
        double sprintTime;
        sprintTime=this.currentMovement.getTimeLastSprinted();
        int stamPoints=(int) sprintTime*10;
        this.setCurrentStaminaPoints(getCurrentStaminaPoints()-stamPoints);
        if(this.currentMovement.getMovementFinished()) {
            setactivity("none");
            setMovementPaused(false);
            setActivityTimeLeft(0);
            setWasWorking(false);
            this.currentMovement=null;
        }

    }

    /**
     * Return how many seconds the Unit has left for sprinting.
     */
    protected double getTimeLeftSprinting() {
        return timeLeftSprinting;
    }
    /**
     * Calculate and set the time that the Unit has left for sprinting.
     */
    protected void setTimeLeftSprinting (){
        double stampoints = (double) this.getCurrentStaminaPoints();
        this.timeLeftSprinting=(stampoints)/10;


    }
    
    private double timeLeftSprinting;

    /**
     * Attack another Unit in the Game World.
     * 
     * @param	defender
     * 			The other Unit that this Unit is attacking.
     * @effect	The defender is currently defending, the attacker is currently attacking.
     */
    public void attack(Unit defender) {
        String prevactdef = defender.getactivity();
        if ((this.getactivity().equals("resting") && this.getActivityTimeLeft()>0)||
                this.getactivity().equals("defending")||this.getactivity().equals("attacking")){
            return;}
        setActivityTimeLeft(1);
        setWasWorking(false);
        setMovementPaused(false);

        switch (prevactdef) {
            case "resting":
            case "working":
                defender.setWasWorking(true);
                defender.setWorkTimeLeft(this.getActivityTimeLeft());
                break;
            case "moving":
                defender.setMovementPaused(true);
                break;
            default:
                break;
        }
        defender.setDamageToBeDone((int) ((this.getStrength())/10));
        this.setactivity("attacking");
        defender.setactivity("defending");
        defender.setAttacker(this);
        defender.setorientation((float)(Math.atan2((defender.getlocationY() - this.getlocationY()), (defender.getlocationX() - this.getlocationX()))));
        this.setorientation((float)(Math.atan2((this.getlocationY() - defender.getlocationY()), (this.getlocationX() - defender.getlocationX()))));
    }
    /**
     * Continue the activity of attacking for the given time.
     * 
     * @param	dt
     * 			The time for which the Unit has to continue its attack.
     * @effect	The Unit is dt seconds further in its attack.
     */  
    protected void attackFor(double dt){
        double timeleft =getActivityTimeLeft();
        if (timeleft>dt){setActivityTimeLeft(timeleft-dt);return;}
        setactivity("none");
        setActivityTimeLeft((double) 0);

    }
    /**
     * Return how many seconds the Unit has left to do the current activity.
     */
    protected double getActivityTimeLeft() {
        return activityTimeLeft;
    }

    /**
     * Set the time that is left to do the current activity.
     * 
     * @param	activityTimeLeft
     * 			The time that is left to do the current activity.
     * @post	The time that is left to do the current activity is equal to the given
     * 			time that is left to do the current activity.
     * @throws 	IllegalTimeException()
     * 			The given time that is left to do the current activity is not valid.
     * 		  | ! isValidTime(activityTimeLeft)
     */
    private void setActivityTimeLeft(double activityTimeLeft) throws IllegalTimeException {
        if (!AdjMovement.isValidTime(activityTimeLeft)){
            throw new IllegalTimeException();
        }
        this.activityTimeLeft = activityTimeLeft;
    }

    private double activityTimeLeft;

    /**
     * Return the activity of this unit.
     */
    @Basic @Raw
    public String getactivity() {
      return this.activity;
    }

    /**
     * Check whether the given activity is a valid activity for
     * any unit.
     *
     * @param  activity
     *         The activity to check.
     * @return
     *       | result == activity=="none" or activity=="attacking" or activity=="resting" or activity=="work" or activity=="moving"
     */
    protected static boolean isValidactivity(String activity) {
        switch (activity){
            case "none" :case "moving" :case "attacking" :case "defending":case "resting":case "working": return true;

            default:return false;

        }
    }

    /**
     * Set the activity of this unit to the given activity.
     *
     * @param  activity
     *         The new activity for this unit.
     * @post   If the given activity is a valid activity for any unit,
     *         the activity of this new unit is equal to the given
     *         activity.
     *       | if (isValidactivity(activity))
     *       |   then new.getactivity() == activity
     */
    @Raw
    protected void setactivity(String activity) {
      if (isValidactivity(activity))
        this.activity = activity;
    }

    /**
     * Variable registering the activity of this unit.
     */
    private String activity;
    /**
     * Check whether the movement of the Unit is paused.
     */
    protected boolean isMovementPaused() {
        return movementPaused;
    }


    /**
     * Set the state of movement of the Unit to the given state of movement.
     * 
     * @param	movementPaused
     * 			The state of movement of the Unit.
     * @post	The state of movement is equal to the given flag.
     */
    protected Unit setMovementPaused(boolean movementPaused) {
        this.movementPaused = movementPaused;
        return this;
    }

    /**
     * Return how many seconds the Unit has left to work.
     */

    protected double getWorkTimeLeft() {
        return workTimeLeft;
    }

    /**
     * Set the time that the Unit has left to work to the given time that the Unit
     * has left to work.
     * 
     * @param	workTimeLeft
     * 			The time in seconds that the Unit has left to work.
     * @post	The time that the Unit has left to work is equal to the given time that
     * 			the Unit has left to work.
     * 		  | new.getWorkTimeLeft == workTimeLeft
     * @throws	IllegalTimeException()
     * 			The given time that the Unit has left to work is not valid for any Unit.
     * 		  | ! isValidTime(workTimeLeft)
     */
    protected Unit setWorkTimeLeft(double workTimeLeft)throws IllegalTimeException {
        if (!AdjMovement.isValidTime(workTimeLeft)){
            throw new IllegalTimeException();
        }
        this.workTimeLeft = workTimeLeft;
        return this;
    }

    private double workTimeLeft;

    /**
     * Check whether the Unit was working before or not.
     */
    protected boolean wasWorking() {
        return wasWorking;
    }

    /**
     * Set the state of having worked to the given state of having worked.
     * 
     * @param	wasWorking
     * 			The state of having worked of the Unit.
     * @post	The state of having worked of the Unit is equal to the given state
     * 			of having worked.
     */
    protected void setWasWorking(boolean wasWorking) {
        this.wasWorking = wasWorking;

    }

    /**
     * variable registering if the unit was previously at work
     */
    private boolean wasWorking;

    /**
     * Let the Unit continue the activity of defence for the given time.
     * 
     * @param	dt
     * 			The time in seconds for which the Unit has to continue its defence.
     * @effect	The Unit is dt seconds further in its defence.
     */
    protected void defendFor(double dt){
        if(Util.fuzzyGreaterThanOrEqualTo(dt,getActivityTimeLeft())){
            this.defend();
            this.setActivityTimeLeft(0);
            if(wasWorking()){setactivity("working");
                setActivityTimeLeft(getWorkTimeLeft());
                setWasWorking(false);
                setWorkTimeLeft(0);}
            else if(movementPaused){
                setactivity("moving");
                setMovementPaused(false);}
        }
        else setActivityTimeLeft(getActivityTimeLeft()-dt);
    }
    private int damageToBeDone;

    /**
     * Return how much damage has to be done to the Unit at the end of the defence.
     */
    protected int getDamageToBeDone() {
        return damageToBeDone;
    }
    /**
     * Set the damage that has to be done to the unit equal to the given damage that
     * has to be done to the unit.
     * 
     * @param	damageToBeDone
     * 			The damage that has to be done to the unit.
     * @post	The damage that has to be done to the unit is equal to the given damage
     * 			that has to be done to the unit.
     */
    protected void setDamageToBeDone(int damageToBeDone) {
        this.damageToBeDone = damageToBeDone;

    }

    /**
     * The defending Unit reacts to the attacker's attack. The Unit will either receive
     * damage, dodge the attack and move to a random location, or block the attack. 
     */
    protected void defend() {

        //generate two random numbers which are 0<=x<100
        double d = (double) Math.random()*100;
        double b = (double) Math.random()*100;
        //probability for successfully dodging
        if (d <= 0.2*(this.getAgility()/this.attacker.getAgility())) {
            //move to a random position (with the same z-coordinate though)
            //check whether the random position is within the boundaries of the Game World
            double randomX = 0;
            double randomY = 0;

            boolean locIsValid=false;
            while (!locIsValid) {
                randomX =  Math.random();
                double negX =  Math.random();
                if (negX < 0.5) {randomX = randomX*(-1);}
                randomY =  Math.random();
                double negY =  Math.random();
                if (negY < 0.5) {randomY = randomY*(-1);}
                double [] newLoc =new double[]{getlocationX()+randomX,getlocationY()+randomY,getlocationZ()};
                locIsValid = isValidlocation(newLoc);
            }
            this.setlocation(( new double[]{getlocationX()+randomX,getlocationY()+randomY,getlocationZ()}));
            //probability for successfully blocking
        } else if (! (b <= 0.25*((this.getStrength()+this.getAgility())/(this.attacker.getStrength()+this.attacker.getAgility())))) {
            this.hitpoints = this.hitpoints - this.getDamageToBeDone();
        }
        this.attacker=null;
        this.setDamageToBeDone(0);
    }
    protected double getlocationX(){
        return this.location[0];
    }
    protected double getlocationY(){
        return this.location[1];
    }
    protected double getlocationZ(){
        return this.location[2];
    }
    /**
     * Return the attacker of the Unit.
     */
    protected Unit getAttacker() {
        return attacker;
    }
    /**
     * Set the attacker of this Unit to the given attacker.
     * 
     * @param	attacker
     * 			The other Unit that is attacking this Unit.
     * @post	The Unit that is attacking this Unit is equal to the given attacker.
     */
    protected Unit setAttacker(Unit attacker) {
        this.attacker = attacker;
        return this;
    }

    private Unit attacker;
    /**
     * Return the coordinate of the cube which the Unit is in.
     */
    public int[] getCubeCoordinate(){
        int[] coordinate=new int[]{0,0,0};
        coordinate[0]=(int) Math.floor(getlocationX());
        coordinate[1]=(int) Math.floor(getlocationY());
        coordinate[2]=(int) Math.floor(getlocationZ());
        return coordinate;
    }

    /**
     * Let the Unit continue the activity of resting for the given time.
     * 
     * @param	dt
     * 			The time in seconds for which the Unit has to continue its rest.
     * @effect	The Unit is dt seconds further in its rest.
     */  
    protected void restFor(double dt){

        if (getActivityTimeLeft()>0){
            if (Util.fuzzyGreaterThanOrEqualTo(dt,getActivityTimeLeft())){
                this.setActivityTimeLeft(0);
            }
            else{ this.setActivityTimeLeft(getActivityTimeLeft()-dt);}
        }
        if (this.getCurrentHitPoints()<this.getMaxHitPoints()){
            double hitpoints=dt*this.getToughness()/(200*0.2);
            int newhitpoints=(int) Math.round(hitpoints);
            this.hitpoints=(getCurrentHitPoints()+newhitpoints);
            return;
        }

        else if (this.getCurrentStaminaPoints()<this.getMaxStaminaPoints()){
            double stampoints=dt*this.getToughness()/(100*0.2);
            int newStampoints=(int) Math.round(stampoints);
            this.setCurrentStaminaPoints(getCurrentStaminaPoints()+newStampoints);
            return;
        }
        else{this.setactivity("none");
        }


    }
    /**
     * Let the unit rest.
     */
    public void rest(){
        if ((this.getactivity().equals("resting") && this.getActivityTimeLeft()>0)||
                this.getactivity().equals("defending")||this.getactivity().equals("attacking")){
            return;}
        double setTimeleft= (double) 1/(this.getToughness()/(100*0.2));
        this.setActivityTimeLeft(setTimeleft);
        this.setactivity("resting");
        this.setWasWorking(false);
        this.setWorkTimeLeft(0);
        this.setMovementPaused(false);

    }
    /**
     * Let the unit work.
     */
    public void work(){
        if ((this.getactivity().equals("resting") && this.getActivityTimeLeft()>0)||
                this.getactivity().equals("defending")||this.getactivity().equals("attacking")){
            return;}
        this.setactivity("working");
        this.setWasWorking(false);
        this.setMovementPaused(false);
        double worktime=((double) getStrength())/500;
        this.setActivityTimeLeft(worktime);
    }
    /**
     * Let the Unit continue the activity of working for the given time.
     * 
     * @param	dt
     * 			The time in seconds for which the Unit has to continue its work.
     * @effect	The Unit is dt seconds further in its work.
     */  
    private void workFor(double dt){
        if(Util.fuzzyGreaterThanOrEqualTo(dt,getActivityTimeLeft())){
            this.setActivityTimeLeft(0);
            this.setactivity("none");
            this.setWasWorking(false);
            this.setMovementPaused(false);
        }
    }
    /**
     * Let this Unit spend time when it is not doing any activity. If default behavior is
     * enabled, the Unit will start doing random activities.
     * 
     * @param	dt
     * 			The time for which the Unit has to spend time without doing any activity.
     * @effect	The Unit is dt seconds further in its movement.
     * 			If default behaviour is enabled, the Unit will be doing random activities.
     * 			Otherwise, it will remain passive.
     */  
    private void spendTimeFor(double dt){
        if (isDefaultBehaviorEnabled()){
            if (Math.random()<=0.333){
                this.work();
                return;
            }
            else if(Math.random()>=0.5){
                double randX = Math.random()*50;
                double randY = Math.random()*50;
                double randZ = Math.random()*50;
                if (isValidlocation(new double[]{randX,randY,randZ})){
                    moveTo(new double[]{randX,randY,randZ});return;}
                else {this.work();return;}}
            else {rest();return;}



        }

    }

    /**
     * make this unit start sprinting if it is moving
     */
    public void startSprinting(){
        if (!this.getactivity().equals("moving")){
            throw new IllegalArgumentException("must be moving to sprint");
        }
        else {this.currentMovement.setIsSprinting(true);
            this.isSprinting=true;}
    }
    /**
     * make this unit stop sprinting if it is moving
     */
    public void stopSprinting(){
        if (!this.getactivity().equals("moving")){
            throw new IllegalArgumentException("must be moving to (stop) sprint");
        }
        else {this.currentMovement.setIsSprinting(false);
            this.isSprinting=false;}
    }

    /**
     * Check whether the default behavior is enabled.
     */
    public boolean isDefaultBehaviorEnabled() {
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
    }}