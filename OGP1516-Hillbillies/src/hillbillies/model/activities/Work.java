package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.*;
import hillbillies.model.CubeObjects.Air;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

import java.util.List;
import java.util.Set;

/**
 * The Id's of the activities are the following:
 * 0: noActivity
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
 */

//TODO Units shall gain 10 experience points for every completed work order. No
//experience points shall be awarded for interrupted activities.
public class Work implements IActivity {
	
    public Work(Unit unit, int[] targetLocation){
        this.timeLeft = ((double) 500)/unit.getStrength();
        this.unit = unit;
        this.targetLocation = targetLocation;
    }

    private boolean canHaveAsTargetLocation(int[] targetLocation){
        if(targetLocation.length!=3) return false;
        for (int loc :
                targetLocation) {
            if (loc>=unit.getWorld().sideSize) return false;
        }
        return true;
    }
    
    private Unit unit;
    private double timeLeft;
	private final int[] targetLocation;

	@Override
	public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.returnSimpleTimeLeft())) {
            this.timeLeft = 0;
            conductWork();
        } else {
            double time = returnSimpleTimeLeft() - dt;
            this.setTimeLeft(time);
        }
	}

    /**
     * Return the time left for this Work.
     */
    @Basic
    @Raw
    @Override
	public double returnSimpleTimeLeft() throws IllegalArgumentException {
		return this.timeLeft;
	}

	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return true;
	}

	@Override
	public int getId() {
		return 4;
	}

    /**
     * Check whether the given time left is a valid time left for
     * any Work.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return Always true.
     */
    private static boolean isValidTimeLeft(double timeLeft) {
        return timeLeft>0;
    }	
	
    /**
     * Set the time left for this Work to the given time left.
     *
     * @param  timeLeft
     *         The new time left for this Work.
     * @post   The time left fot this new Work is equal to
     *         the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any
     *         Work.
     */
    @Raw
    public void setTimeLeft(double timeLeft) throws IllegalTimeException {
        if (! isValidTimeLeft(timeLeft))
            throw new IllegalTimeException();
        this.timeLeft = timeLeft;
    }    
    
    private void conductWork() {
        if (unit.isCarrying()) {
            dropWork();
            return;
        }
        if (unit.getWorld().getCubeAt(targetLocation)==3){
            if(!unit.getWorld().getLogsAt(targetLocation).isEmpty()&&!unit.getWorld().getBouldersAt(targetLocation).isEmpty()){
                workOutWork();
                return;
            }
        }
        if (!unit.getWorld().getBouldersAt(targetLocation).isEmpty()||!unit.getWorld().getLogsAt(targetLocation).isEmpty()){
            pickupWork();
            return;
        }
        if (unit.getWorld().getCubeAt(targetLocation)==2||unit.getWorld().getCubeAt(targetLocation)==1){
            destroyWork();
            return;
        }
    	
    }

    private void dropWork(){
        unit.drop(unit.getCarriedObject());
        unit.activityFinished();

    }
    private void workOutWork(){
        World wereld=unit.getWorld();
        Log log=wereld.getLogsAt(targetLocation).get(0);
        Boulder boulder=wereld.getBouldersAt(targetLocation).get(0);
        //TODO Increase weight and toughness?
        log.unregister();
        boulder.unregister();
        unit.activityFinished();

    }

    private void pickupWork(){
        World wereld=unit.getWorld();
        List<Boulder> boulderList=wereld.getBouldersAt(targetLocation);
        if(!boulderList.isEmpty()){
            unit.carry(boulderList.get(0));

        }
        else {
            List<Log> logList=wereld.getLogsAt(targetLocation);
            unit.carry(logList.get(0));
        }
        unit.activityFinished();
    }
    private void destroyWork(){
        unit.getWorld().destroyCube(targetLocation);
        unit.activityFinished();

    }
}