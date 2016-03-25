package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.UnitIllegalLocation;
import ogp.framework.util.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Movement {

    /** Opmerking, oppassen met references=> na movement finished of terminated moet verwijderd worden => beschikbaar voor garbage collection (memory leak)
     * @invar Each Movement can have its startLocation as startLocation.
     *       | canHaveAsStartLocation(this.getStartLocation())
     * @invar  Each Movement can have its destination as destination.
     *       | canHaveAsdestination(this.getdestination())
     */
    /**
     * Initialize this new Movement with given startLocation.
     *
     * @param StartLocation The startLocation for this new Movement.
     * @throws UnitIllegalLocation This new Movement cannot have the given startLocation as its startLocation.
     *                             | ! canHaveAsStartLocation(this.getStartLocation())
     * @post The startLocation of this new Movement is equal to the given
     * startLocation.
     * | new.getStartLocation() == StartLocation
     * @param  destination
     *         The destination for this new Movement.
     * @post   The destination of this new Movement is equal to the given
     *         destination.
     *       | new.getdestination() == destination
     * @throws UnitIllegalLocation
     *         This new Movement cannot have the given destination as its destination.
     *       | ! canHaveAsdestination(this.getdestination())
     */

    public Movement(Unit unit,double[] StartLocation,double[] destination) throws UnitIllegalLocation {
        if (Arrays.equals(StartLocation,destination)){
            throw new UnitIllegalLocation();
        }
        if (!canHaveAsStartLocation(StartLocation))
            throw new UnitIllegalLocation();
        this.StartLocation = StartLocation;
        if (!canHaveAsdestination(destination))
            throw new UnitIllegalLocation();
        this.destination = destination;
        setIsSprinting(false);
        this.setcurrentPosition(currentPosition);
        setCurrentSpeed(unit);
        Queue<AdjMovement> pathQueue = new LinkedList<>();
        AdjMovement movetoadj=new AdjMovement(StartLocation,destination,getCurrentSpeed());
        pathQueue.add(movetoadj);
        this.moveQueue=pathQueue;

    }
    /** Opmerking, oppassen met references=> na movement finished of terminated moet verwijderd worden => beschikbaar voor garbage collection (memory leak)
     * @invar Each Movement can have its startLocation as startLocation.
     *       | canHaveAsStartLocation(this.getStartLocation())
     * @invar  Each Movement can have its destination as destination.
     *       | canHaveAsdestination(this.getdestination())
     */
    /**
     * Initialize this new Movement with given startLocation.
     *
     * @param StartLocation The startLocation for this new Movement.
     * @throws UnitIllegalLocation This new Movement cannot have the given startLocation as its startLocation.
     *                             | ! canHaveAsStartLocation(this.getStartLocation())
     * @post The startLocation of this new Movement is equal to the given
     * startLocation.
     * | new.getStartLocation() == StartLocation
     * @param  destination
     *         The destination for this new Movement.
     * @post   The destination of this new Movement is equal to the given
     *         destination.
     *       | new.getdestination() == destination
     * @throws UnitIllegalLocation
     *         This new Movement cannot have the given destination as its destination.
     *       | ! canHaveAsdestination(this.getdestination())
     * @param  isSprinting
     *         The isSprinting for this new Movement.
     * @post   If the given isSprinting is a valid isSprinting for any Movement,
     *         the isSprinting of this new Movement is equal to the given
     *         isSprinting. Otherwise, the isSprinting of this new Movement is equal
     *         to false.
     *       | if (isValidisSprinting(isSprinting))
     *       |   then new.getisSprinting() == isSprinting
     *       |   else new.getisSprinting() == false
     * @invar  The isSprinting of each Movement must be a valid isSprinting for any
     *         Movement.
     *       | isValidisSprinting(getisSprinting())
     */

    public Movement(Unit unit,double[] StartLocation,double[] destination,boolean isSprinting) throws UnitIllegalLocation {
        if (Arrays.equals(StartLocation,destination)){
            throw new UnitIllegalLocation();
        }
        if (!canHaveAsStartLocation(StartLocation))
            throw new UnitIllegalLocation();
        this.StartLocation = StartLocation;
        if (!canHaveAsdestination(destination))
            throw new UnitIllegalLocation();
        this.destination = destination;
        if (!isValidisSprinting(isSprinting))
            isSprinting = false;
        setIsSprinting(isSprinting);
        this.setcurrentPosition(currentPosition);
        setCurrentSpeed(unit);
        Queue<AdjMovement> pathQueue = new LinkedList<>();
        double positieX=(Math.floor(StartLocation[0])+0.5);
        double positieY=Math.floor(StartLocation[1])+0.5;
        double positieZ=Math.floor(StartLocation[2])+0.5;
        double[] positieXYZ=new double[]{positieX,positieY,positieZ};
        AdjMovement newadj = new AdjMovement(StartLocation,positieXYZ,currentSpeed);
        pathQueue.add(newadj);
        double[] posPrev=positieXYZ.clone();
        double destPositieX=destination[0]+0.5;
        double destPositieY=destination[1]+0.5;
        double destPositieZ=destination[2]+0.5;
        while (!((Util.fuzzyEquals(positieX, destPositieX)) &&
                (Util.fuzzyEquals(positieY, destPositieY)) &&
                (Util.fuzzyEquals(positieZ, destPositieZ)))) {

            if(Util.fuzzyEquals(positieX,destPositieX)){}
            else if (Util.fuzzyGreaterThanOrEqualTo(positieX,destPositieX)) {positieX=+1;}
            else if (Util.fuzzyLessThanOrEqualTo(positieX,destPositieX)) {positieX=-1;}
            if(Util.fuzzyEquals(positieY,destPositieY)){}
            else if (Util.fuzzyGreaterThanOrEqualTo(positieY,destPositieY)) {positieY=+1;}
            else if (Util.fuzzyLessThanOrEqualTo(positieY,destPositieY)) {positieY=-1;}
            if(Util.fuzzyEquals(positieX,destPositieX)){}
            else if (Util.fuzzyGreaterThanOrEqualTo(positieZ,destPositieZ)) {positieZ=+1;}
            else if (Util.fuzzyLessThanOrEqualTo(positieZ,destPositieZ)) {positieZ=-1;}
            positieXYZ[0]=positieX;
            positieXYZ[1]=positieY;
            positieXYZ[2]=positieZ;
            newadj= new AdjMovement(posPrev,positieXYZ,currentSpeed);
            pathQueue.add(newadj);
            posPrev=positieXYZ.clone();
        }
        this.moveQueue = pathQueue;

    }

    /**
     * Return the startLocation of this Movement.
     */
    @Basic
    @Raw
    @Immutable
    public double[] getStartLocation() {
        return this.StartLocation;
    }

    /**
     * Check whether this Movement can have the given startLocation as its startLocation.
     *
     * @param StartLocation The startLocation to check.
     * @return | result == startLocation.length==3 and unit.canhaveaslocation(location[0,1,2]
     */
    @Raw
    public boolean canHaveAsStartLocation(double[] StartLocation) {return (StartLocation.length==3);
    }

    /**
     * Variable registering the startLocation of this Movement.
     */
    private final double[] StartLocation;


    /**
     * Return the destination of this Movement.
     */
    @Basic @Raw @Immutable
    public double[] getdestination() {
      return this.destination;
    }

    /**
     * Check whether this Movement can have the given destination as its destination.
     *
     * @param  destination
     *         The destination to check.
     * @return
     *       | result == length.destination==3
     */
    @Raw
    public boolean canHaveAsdestination(double[] destination) {
      return false;
    }

    /**
     * Variable registering the destination of this Movement.
     */
    private final double[] destination;


    /**
     * Return the isSprinting of this Movement.
     */
    @Basic @Raw
    public boolean getisSprinting() {
      return this.isSprinting;
    }

    /**
     * Check whether the given isSprinting is a valid isSprinting for
     * any Movement.
     *
     * @param  isSprinting
     *         The isSprinting to check.
     * @return
     *       | result == true
     */
    public static boolean isValidisSprinting(boolean isSprinting) {return true;}


    /**
     * Set the isSprinting of this Movement to the given isSprinting.
     *
     * @param  isSprinting
     *         The new isSprinting for this Movement.
     * @post   If the given isSprinting is a valid isSprinting for any Movement,
     *         the isSprinting of this new Movement is equal to the given
     *         isSprinting.
     *       | if (isValidisSprinting(isSprinting))
     *       |   then new.getisSprinting() == isSprinting
     */
    @Raw
    public void setIsSprinting(boolean isSprinting) {
      if (isValidisSprinting(isSprinting))
        this.isSprinting = isSprinting;
    }

    /**
     * Variable registering the isSprinting of this Movement.
     */
    private boolean isSprinting;

    /** TO BE ADDED TO CLASS HEADING
     * @invar  The currentPosition of each Movement must be a valid currentPosition for any
     *         Movement.
     *       | isValidcurrentPosition(getcurrentPosition())
     */


    /**
     * Initialize this new Movement with given currentPosition.
     *
     * @param  currentPosition
     *         The currentPosition for this new Movement.
     * @effect The currentPosition of this new Movement is set to
     *         the given currentPosition.
     *       | this.setcurrentPosition(currentPosition)
     */


    /**
     * Return the currentPosition of this Movement.
     */
    @Basic @Raw
    public double[] getcurrentPosition() {
      return this.currentPosition;
    }

    /**
     * Check whether the given currentPosition is a valid currentPosition for
     * any Movement.
     *
     * @param  currentPosition
     *         The currentPosition to check.
     * @return
     *       | result == currentPosition.length=3
     */
    public static boolean isValidcurrentPosition(double[] currentPosition) {
      return (currentPosition.length==3);
    }

    /**
     * Set the currentPosition of this Movement to the given currentPosition.
     *
     * @param  currentPosition
     *         The new currentPosition for this Movement.
     * @post   The currentPosition of this new Movement is equal to
     *         the given currentPosition.
     *       | new.getcurrentPosition() == currentPosition
     * @throws UnitIllegalLocation
     *         The given currentPosition is not a valid currentPosition for any
     *         Movement.
     *       | ! isValidcurrentPosition(getcurrentPosition())
     */
    @Raw
    public void setcurrentPosition(double[] currentPosition)
          throws UnitIllegalLocation {
      if (! isValidcurrentPosition(currentPosition))
        throw new UnitIllegalLocation();
      this.currentPosition = currentPosition;
    }

    /**
     * Variable registering the currentPosition of this Movement.
     */
    private double[] currentPosition;
    private double timeLastSprinted;

    public double getTimeLastSprinted(){
        return this.timeLastSprinted;
    }

    private double currentSpeed;
    private void setCurrentSpeed(Unit unit){
        double speed=(double)(1.5*(unit.getStrength()+unit.getAgility())/(2*unit.getWeight()));
        if(isSprinting){this.currentSpeed=(speed*2);}
        else {this.currentSpeed=speed;}
    }
    public double getCurrentSpeed(){
        return currentSpeed;
    }
    private Queue<AdjMovement> moveQueue;
    public void advanceTime(Unit unit,double dt) {
        this.timeLastSprinted = (double) 0;
        if (isSprinting) {
            double sprintLeft=unit.getTimeLeftSprinting();
            if(Util.fuzzyLessThanOrEqualTo(sprintLeft,dt)){
                double timeleft= sprintLeft;
                advanceLocation(unit,timeleft);
                double timeleft2=(dt-sprintLeft);
                this.setIsSprinting(false);
                advanceLocation(unit,timeleft2);
                this.timeLastSprinted=sprintLeft;
            }
            else{advanceLocation(unit,dt);
                this.timeLastSprinted=dt;}

        }
        else {advanceLocation(unit,dt);
        this.timeLastSprinted=(double) 0;}


    }
    private void advanceLocation(Unit unit,double dt) {
        if (getisSprinting()) {
            dt = dt*2;
        }
        double timeNeeded = moveQueue.peek().getTimeUntilFinished();
        AdjMovement nextAdjMov = moveQueue.peek();
        while (Util.fuzzyGreaterThanOrEqualTo(dt, timeNeeded)) {
            dt = dt - timeNeeded;
            nextAdjMov = moveQueue.poll();
            if (nextAdjMov == null) {
                movementFinished(unit, dt);
                return;
            }
            timeNeeded = moveQueue.peek().getTimeUntilFinished();
        }
        nextAdjMov.proceedPartialMovement(unit,dt);




    }
    protected void setTimeLastSprinted(double timeLastSprinted) {
        this.timeLastSprinted = timeLastSprinted;
    }
    private boolean movementFinished;
    public boolean hasStoppedSprinting;
    private void setMovementFinished(boolean finished){
        this.movementFinished=finished;}
    public boolean getMovementFinished(){
        return this.movementFinished;
    }

    private void movementFinished(Unit unit,double dt){

        double timeSprinted=getTimeLastSprinted();
        setMovementFinished(true);
        if (!getisSprinting()){return;}
        setTimeLastSprinted(timeSprinted-(dt/2));

    }


}