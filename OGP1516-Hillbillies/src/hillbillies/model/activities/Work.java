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
 * A class of Work activities involving a unit and a target location.
 * 
 * The Id's of the activities are the following:
 * 0: noActivity
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Work implements IActivity {
	
	/**
	 * Initialize this new Work with given unit and given target location.
     *
     * @param  unit
     *         The unit for this new Work.
     * @param  targetLocation
     * 		   The target location for this Work.
     * @post   The unit of this new Work is equal to the given unit.
     * @post   The target location of this new Work is equal to the given target location.
     */
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
    
    /**
     * Variable registering the unit of this Work.
     */
    private Unit unit;
    
    /**
     * Variable registering the time left until finishing this Work.
     */
    private double timeLeft;
    
    /**
     * Variable registering the target location of this Work.
     */
	private final int[] targetLocation;

	/**
	 * No documentation required.
	 */
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

    /**
     * Check whether this Work can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return true;
	}

    /**
     * Return the ID of this Work.
     */
	@Override
	public int getId() {
		return 4;
	}

    /**
     * Check whether the given time left is a valid time left for any Work.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return True if and only if the time left is greater than zero.
     */
    private static boolean isValidTimeLeft(double timeLeft) {
        return timeLeft > 0;
    }	
	
    /**
     * Set the time left for this Work to the given time left.
     *
     * @param  timeLeft
     *         The new time left for this Work.
     * @post   The time left fot this new Work is equal to the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any Work.
     */
    @Raw
    public void setTimeLeft(double timeLeft) throws IllegalTimeException {
        if (! isValidTimeLeft(timeLeft))
            throw new IllegalTimeException("thrown by advanceTime from Work timeleft duration was not valid");
        this.timeLeft = timeLeft;
    }    
    
    /**
     * Let the unit of this Work conduct Work.
     * 
     * @effect If the unit was carrying an object, the object is dropped and the unit gains 10 experience points.
     * 		   If the target location is in a workshop cube and a Boulder and Log are present at the target location, 
     * 		   the unit works out and gains 10 experience points. If a Boulder or Log is present at the target location,
     * 		   the unit picks up the Boulder or Log and gains 10 experience points. If the target location is in a wood or rock
     * 		   cube, the cube collapses and the unit gains 10 experience points.
     */
    private void conductWork() {
        if (unit.isCarrying()) {
            dropWork();
            unit.addXP(10);
            return;
        }
        if (unit.getWorld().getCubeAt(targetLocation) == 3) {
            if (! unit.getWorld().getLogsAt(targetLocation).isEmpty() && ! unit.getWorld().getBouldersAt(targetLocation).isEmpty()) {
                workOutWork();
                unit.addXP(10);
                return;
            }
        }
        if (! unit.getWorld().getBouldersAt(targetLocation).isEmpty() || ! unit.getWorld().getLogsAt(targetLocation).isEmpty()) {
            pickupWork();
            unit.addXP(10);
            return;
        }
        if (unit.getWorld().getCubeAt(targetLocation) == 2 || unit.getWorld().getCubeAt(targetLocation) == 1) {
            destroyWork();
            unit.addXP(10);
            return;
        }
    }

    /**
     * Let the unit drop the object it is carrying.
     * 
     * @effect The unit drops the object it is carrying and this Work is finished.
     */
    private void dropWork(){
        unit.drop(unit.getCarriedObject()); 
        unit.activityFinished();
    }
    
    /**
     * Let the unit work out.
     * 
     * @effect The unit's weight and toughness is increased and this Work is finished.
     */
    private void workOutWork() {
        World world = unit.getWorld();
        Log log = world.getLogsAt(targetLocation).get(0);
        Boulder boulder = world.getBouldersAt(targetLocation).get(0);
        this.unit.setWeight(unit.getWeight() + 1);
        this.unit.setToughness(unit.getToughness() + 1);
        //TODO Increase weight and toughness? by how much
        log.unregister();
        boulder.unregister();
        unit.activityFinished();
    }

    /**
     * Let the unit carry the object.
     * 
     * @effect The unit picks up the boulder or log and this Work is finished.
     */
    private void pickupWork() {
        World world = unit.getWorld();
        List<Boulder> boulderList = world.getBouldersAt(targetLocation);
        if (! boulderList.isEmpty()) {
            unit.carry(boulderList.get(0));
        } else {
            List<Log> logList = world.getLogsAt(targetLocation);
            unit.carry(logList.get(0));
        }
        unit.activityFinished();
    }
    
    /**
     * Let the unit destroy the cube.
     * 
     * @effect The cube the target location is in is destroyed and this Work is finished.
     */
    private void destroyWork() {
        unit.getWorld().destroyCube(targetLocation);
        unit.activityFinished();
    }
    
}