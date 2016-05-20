package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Boulder;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

import java.util.List;

/**
 * A class of work activities involving a unit and a target location.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Work implements IActivity {

    /**
	 * Initialize this new work with given unit and given target location.
     *
     * @param  unit
     *         The unit for this new work.
     * @param  targetLocation
     * 		   The target location for this new work.
     * @post   The unit of this new work is equal to the given unit.
     * @post   The target location of this new work is equal to the given target location.
     */
    public Work(Unit unit, int[] targetLocation){
        this.timeLeft = ((double) 500)/unit.getStrength();
        this.unit = unit;
        if (!this.canHaveAsTargetLocation(targetLocation))
        	this.finishActivity();
        this.targetLocation = targetLocation;
    }

    /* Variables */
    
    /**
     * Variable registering whether this work has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering whether this work is finished.
     */
    private boolean isFinished = false;
    
    /**
     * Variable registering the unit of this work.
     */
    private Unit unit;
    
    /**
     * Variable registering the target location of this work.
     */
	private final int[] targetLocation;
	
    /**
     * Variable registering the time left until finishing this work.
     */
    private double timeLeft;

    /* Methods */
    
    /**
     * Return whether this work has been dictated by a statement.
     */
    @Override
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this work.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}

    /**
     * Update this work according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     * @effect	If the given time is equal or higher than the time left until finishing this work, conduct this work.
     * @effect	If the given time is lower than the time left until finishing this work, the time left for this work is reduced by the given amount of time.
     */
	@Override
	public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.getTimeLeft())) {
            this.timeLeft = 0;
            conductWork();
        } else {
            double time = getTimeLeft() - dt;
            this.setTimeLeft(time);
        }
	}

    /**
     * Return the time left until this work is finished.
     */
    @Basic
    @Raw
    public double getTimeLeft() {
    	return this.timeLeft;
    }

    /**
     * Check whether the given time left is a valid time left for any work.
     *
     * @param	timeLeft
     *			The time left to check.
     * @return	True if and only if the given time left is higher than zero.
     */
    private static boolean isValidTimeLeft(double timeLeft) {
        return timeLeft > 0;
    }	
	
    /**
     * Set the time left for this work to the given time left.
     *
     * @param	timeLeft
     *			The new time left for this work.
     * @post	The time left of this new work is equal to the given time left.
     * @throws	IllegalTimeException
     *			The given time left is not a valid time left for any work.
     */
    @Raw
    private void setTimeLeft(double timeLeft) throws IllegalTimeException {
      if (! isValidTimeLeft(timeLeft))
        throw new IllegalTimeException("Invalid time left");
      this.timeLeft = timeLeft;
    }

    /**
     * Check whether this work can be interrupted by the given activity.
     * 
     * @param	activity
     *			The activity to check.
     * @return	Always true.
     */
	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return true;
	}

    /**
     * Return the ID of this work.
     */
	@Override
	public int getId() {
		return 4;
	}

    /**
     * Return whether this work is finished.
     */
    @Override
    public boolean isFinished() {
        return this.isFinished;
    }

    /**
     * Finish this work.
     */
    @Override
    public void finishActivity() {
        this.isFinished = true;
        unit.activityFinished();
    }
    
    /**
     * Let the unit of this work conduct work.
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
        if (unit.getWorld().getCubeIDAt(targetLocation) == 3) {
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
        if (unit.getWorld().getCubeIDAt(targetLocation) == 2 || unit.getWorld().getCubeIDAt(targetLocation) == 1) {
            destroyWork();
            unit.addXP(10);
            return;
        }
        unit.activityFinished();
    }
    
    private boolean canHaveAsTargetLocation(int[] targetLocation) {
        if (targetLocation.length != 3
        		|| targetLocation[0] >= unit.getWorld().xSideSize
        		|| targetLocation[1] >= unit.getWorld().ySideSize
        		|| targetLocation[2] >= unit.getWorld().zSideSize)
        	return false;   
        return true;
    }

    /**
     * Let the unit drop the object it is carrying.
     * 
     * @effect The unit drops the object it is carrying and this work is finished.
     */
    private void dropWork(){
        unit.drop(unit.getCarriedObject()); 
        unit.activityFinished();
    }
    
    /**
     * Let the unit work out.
     * 
     * @effect The unit's weight and toughness is increased and this work is finished.
     */
    private void workOutWork() {
        World world = unit.getWorld();
        Log log = world.getLogsAt(targetLocation).get(0);
        Boulder boulder = world.getBouldersAt(targetLocation).get(0);
        this.unit.setWeight((int)Math.ceil(unit.getWeight()*1.1));
        this.unit.setToughness(((int)Math.ceil(unit.getToughness()*1.1)));
        log.unregister();
        boulder.unregister();
        unit.activityFinished();
    }

    /**
     * Let the unit carry the object.
     * 
     * @effect The unit picks up the boulder or log and this work is finished.
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
     * @effect	The cube the target location is in is destroyed and this work is finished.
     */
    private void destroyWork() {
        unit.getWorld().destroyCube(targetLocation);
        unit.activityFinished();
    }
}