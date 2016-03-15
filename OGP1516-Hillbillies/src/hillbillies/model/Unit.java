
package hillbillies.model;
import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.Util;

/**
 * @invar  The location of each Unit must be a valid location for any
 *         Unit.
 *       | isValidlocation(getlocation())
 */

public class Unit {

    /**
     * Initialize this new Unit with given location.
     *
     * @param  initialPosition
     *         The location for this new Unit.
     * @effect The location of this new Unit is set to
     *         the given initialPosition.
     *       | this.setlocation(initialPosition)
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
     * @param	newName
     *       	The new name for this Unit.
     */
    //DEFENSIVE
    public void setName(String newName) throws IllegalArgumentException {
        if (! isValidName(newName)) {
            throw new IllegalArgumentException("This is an invalid name.");
        } else {
            this.name = newName;
        }
    }

    //DEFENSIVE
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
     * @param	newValue
     *       	The new weight for this Unit.
     */
    //TOTAL
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
     * @param	newValue
     *       	The new strength for this Unit.
     */
    //TOTAL
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
     * @param	newValue
     *       	The new agility for this Unit.
     */
    //TOTAL
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
     * @param	newValue
     *       	The new toughness for this Unit.
     */
    //TOTAL
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
    /** TO BE ADDED TO CLASS HEADING
     * @invar  The location of each Unit must be a valid location for any
     *         Unit.
     *       | isValidlocation(getlocation())
     */
    
    
    /**
     * Initialize this new Unit with given location.
     *
     * @param  location
     *         The location for this new Unit.
     * @effect The location of this new Unit is set to
     *         the given location.
     *       | this.setlocation(location)
     */

    
    
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
    
    public Unit(float orientation) {
        if (! isValidorientation(orientation))
            orientation = 0;
        setorientation(orientation);
    }

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
    private Movement currentMovement;
    public double[] currentDestination;
    private boolean movementPaused;
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

    protected double getTimeLeftSprinting() {
        return timeLeftSprinting;
    }

    protected void setTimeLeftSprinting (){
        double stampoints = (double) this.getCurrentStaminaPoints();
        this.timeLeftSprinting=(stampoints)/10;


    }

    private double timeLeftSprinting;

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
    protected void attackFor(double dt){
        double timeleft =getActivityTimeLeft();
        if (timeleft>dt){setActivityTimeLeft(timeleft-dt);return;}
        setactivity("none");
        setActivityTimeLeft((double) 0);

    }
    protected double getActivityTimeLeft() {
        return activityTimeLeft;
    }

    private void setActivityTimeLeft(double activityTimeLeft) throws IllegalTimeException {
        if (!AdjMovement.isValidTime(activityTimeLeft)){
            throw new IllegalTimeException();
        }
        this.activityTimeLeft = activityTimeLeft;
    }

    private double activityTimeLeft;

    /**
     * @invar  The activity of each unit must be a valid activity for any
     *         unit.
     *       | isValidactivity(getactivity())
     */

    /**
     * Initialize this new unit with given activity.
     *
     * @param  activity
     *         The activity for this new unit.
     * @post   If the given activity is a valid activity for any unit,
     *         the activity of this new unit is equal to the given
     *         activity. Otherwise, the activity of this new unit is equal
     *         to "none".
     *       | if (isValidactivity(activity))
     *       |   then new.getactivity() == activity
     *       |   else new.getactivity() == "none"
     */
    public Unit(String activity) {
      if (! isValidactivity(activity))
        activity = "none";
      setactivity(activity);
    }

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
     *       | result == acivity=="none" or activity=="attacking" or activity=="resting" or activity=="work" or activity=="moving"
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

    protected boolean isMovementPaused() {
        return movementPaused;
    }

    protected Unit setMovementPaused(boolean movementPaused) {
        this.movementPaused = movementPaused;
        return this;
    }

    protected double getWorkTimeLeft() {
        return workTimeLeft;
    }

    protected Unit setWorkTimeLeft(double workTimeLeft)throws IllegalTimeException {
        if (!AdjMovement.isValidTime(workTimeLeft)){
            throw new IllegalTimeException();
        }
        this.workTimeLeft = workTimeLeft;
        return this;
    }

    private double workTimeLeft;

    protected boolean wasWorking() {
        return wasWorking;
    }

    protected void setWasWorking(boolean wasWorking) {
        this.wasWorking = wasWorking;

    }

    /**
     * variable registering if the unit was previously at work
     */
    private boolean wasWorking;

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

    protected int getDamageToBeDone() {
        return damageToBeDone;
    }

    protected void setDamageToBeDone(int damageToBeDone) {
        this.damageToBeDone = damageToBeDone;

    }

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

    protected Unit getAttacker() {
        return attacker;
    }

    protected Unit setAttacker(Unit attacker) {
        this.attacker = attacker;
        return this;
    }

    private Unit attacker;

    public int[] getCubeCoordinate(){
        int[] coordinate=new int[]{0,0,0};
        coordinate[0]=(int) Math.floor(getlocationX());
        coordinate[1]=(int) Math.floor(getlocationY());
        coordinate[2]=(int) Math.floor(getlocationZ());
        return coordinate;
    }
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

    private void workFor(double dt){
        if(Util.fuzzyGreaterThanOrEqualTo(dt,getActivityTimeLeft())){
            this.setActivityTimeLeft(0);
            this.setactivity("none");
            this.setWasWorking(false);
            this.setMovementPaused(false);
        }
    }
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


    public void startSprinting(){
        if (!this.getactivity().equals("moving")){
            throw new IllegalArgumentException("must be moving to sprint");
        }
        else {this.currentMovement.setIsSprinting(true);
            this.isSprinting=true;}
    }
    public void stopSprinting(){
        if (!this.getactivity().equals("moving")){
            throw new IllegalArgumentException("must be moving to (stop) sprint");
        }
        else {this.currentMovement.setIsSprinting(false);
            this.isSprinting=false;}
    }


    public boolean isDefaultBehaviorEnabled() {
        return defaultbehaviorenabled;
    }
    public void setDefaultbehavior(boolean enabled){
        this.defaultbehaviorenabled=enabled;
    }
}
