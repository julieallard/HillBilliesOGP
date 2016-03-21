package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.exceptions.UnitIllegalLocation;
import ogp.framework.util.Util;

/** @invar  Each movement can have its currentstart as currentstart.
        *       | canHaveAsCurrentStart(this.getCurrentStart())
        * @invar  The timeUntilFinished of each AdjMovement must be a valid timeUntilFinished for any
        *         AdjMovement.
        *       | isValidTime(getTimeUntilFinished())
        * Initialize this new movement with given currentstart.
        */
public class AdjMovement {
    /**
     * @param  CurrentStart
     *         The currentstart for this new movement.
     * @post   The currentstart of this new movement is equal to the given
     *         currentstart.
     *       | new.getCurrentStart() == CurrentStart
     * @throws UnitIllegalLocation
     *         This new movement cannot have the given currentstart as its currentstart.
     *       | ! canHaveAsCurrentStart(this.getCurrentStart())
     * Initialize this new movement with given currentstart.
     *
     * @param  CurrentDestination   
     *         The currentdestination for this new movement.
     * @post   The currentdestination of this new movement is equal to the given
     *         currentdestination.
     *       | new.getCurrentDestination() == CurrentDestination
     * @throws UnitIllegalLocation
     *         This new movement cannot have the given currentDestination as its currentdestination.
     *       | ! canHaveAsCurrentDestination(this.getCurrentDestination())
     */
    public AdjMovement(double[] CurrentStart,double[] CurrentDestination,double horSpeed) throws UnitIllegalLocation,IllegalTimeException {
      if (! canHaveAsCurrentStart(CurrentStart)) {throw new UnitIllegalLocation();}
        this.CurrentStart = CurrentStart;
      if(!canHaveAsCurrentDestination(CurrentDestination)){ throw new UnitIllegalLocation();}
        this.CurrentDestination=CurrentDestination;
        double dist =Math.sqrt(Math.pow(CurrentStart[0]-CurrentDestination[0],2)+Math.pow(CurrentStart[1]-CurrentDestination[1],2)+Math.pow(CurrentStart[2]-CurrentDestination[2],2));
        double speed = horSpeed;
        if (CurrentStart[2]-CurrentDestination[2]==-1){speed=speed*0.5;}
        else if (CurrentStart[2]-CurrentDestination[2]==1){speed=speed*1.2;}
        double duration =(dist/speed);
        this.setTimeUntilFinished(duration);
        this.speedX=((CurrentDestination[0]-CurrentStart[0])/dist)*speed;
        this.speedY=((CurrentDestination[1]-CurrentStart[1])/dist)*speed;
        this.speedZ=((CurrentDestination[2]-CurrentStart[2])/dist)*speed;


    }

    /**
     * Return the currentstart of this movement.
     */
    @Basic
    @Raw @Immutable
    public double[] getCurrentStart() {
      return this.CurrentStart;
    }

    /**
     * Check whether this movement can have the given currentstart as its currentstart.
     *
     * @param  currentStart
     *         The currentstart to check.
     * @return
     *       | result == isValidLocation(currentStart) methode van Unit
     */
    @Raw
    public boolean canHaveAsCurrentStart(double[] currentStart) {
      return Unit.isValidlocation(currentStart);
    }

    /**
     * Variable registering the currentstart of this movement.
     */
    private final double[] CurrentStart;
    /**
     * Return the currentdestination of this movement.
     */
    @Basic
    @Raw @Immutable
    public double[] getCurrentDestination() {
        return this.CurrentDestination;
    }

    /**
     * Check whether this movement can have the given currentdestination as its currentdestination.
     *
     * @param  CurrentDestination
     *         The currentdestination to check.
     * @return
     *       | result == isValidLocation(currentStart) methode van Unit
     */
    @Raw
    public boolean canHaveAsCurrentDestination(double[] CurrentDestination) {
        return Unit.isValidlocation(CurrentDestination);
    }

    /**
     * Variable registering the currentdestination of this movement.
     */
    private final double[] CurrentDestination;
    /**
     * Return the timeUntilFinished of this AdjMovement.
     */
    @Basic @Raw
    public double getTimeUntilFinished() {
      return this.TimeUntilFinished;
    }
    
    /**
     * Check whether the given time is a valid Time
     *  
     * @param  time
     *         The timeUntilFinished to check.
     * @return 
     *       | result == timeUntilFinished>=0
     */
    public static boolean isValidTime(Double time) {
      return(time>=0);
    }
    
    /**
     * Set the timeUntilFinished of this AdjMovement to the given timeUntilFinished.
     * 
     * @param  TimeUntilFinished
     *         The new timeUntilFinished for this AdjMovement.
     * @post   The timeUntilFinished of this new AdjMovement is equal to
     *         the given timeUntilFinished.
     *       | new.getTimeUntilFinished() == TimeUntilFinished
     * @throws IllegalTimeException
     *         The given timeUntilFinished is not a valid timeUntilFinished for any
     *         AdjMovement.
     *       | ! isValidTime(getTimeUntilFinished())
     */
    @Raw
    public void setTimeUntilFinished(double TimeUntilFinished)
          throws IllegalTimeException {
      if (! isValidTime(TimeUntilFinished))
        throw new IllegalTimeException();
      this.TimeUntilFinished = TimeUntilFinished;
    }
    
    /**
     * Variable registering the timeUntilFinished of this AdjMovement.
     */
    private double TimeUntilFinished;


    @Basic @Immutable
    public double getSpeedX(){
        return this.speedX;
    }
    public final double speedX;
    @Basic @Immutable
    public double getSpeedY(){
        return this.speedY;
    }
    public final double speedY;
    @Basic @Immutable
    public double getSpeedZ(){
        return this.speedZ;
    }
    public final double speedZ;
    public void proceedPartialMovement(Unit unit,double dt){
        if(Util.fuzzyEquals(dt,this.getTimeUntilFinished())){
            unit.setlocation(this.getCurrentDestination());}

        else{
            double newLocX =dt*speedX;
            double newLocY =dt*speedY;
            double newLocZ =dt*speedZ;
            double[] newloc = {newLocX,newLocY,newLocZ};
            unit.setlocation(newloc);
            this.setTimeUntilFinished(getTimeUntilFinished()-dt);
        }

    }

}

