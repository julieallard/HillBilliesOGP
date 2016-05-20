package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

import java.util.List;

/**
 * A class of work activities involving a unit and a target location.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Work implements IActivity {

    /**
	 * Initialize this new work with given unit and given target location.
     *
     * @param	unit
     *			The unit for this new work.
     * @param	targetCubeLocation
     *			The target location for this new work.
     * @post	The unit of this new work is equal to the given unit.
     * @post	The target location of this new work is equal to the given target location.
     * @effect	The time left for this new work is set to 500 divided by this work's unit strength.
     */
    public Work(Unit unit, int[] targetCubeLocation) {
        this.unit = unit;
        if (!this.canHaveAsTargetCubeLocation(targetCubeLocation))
        	this.finishActivity();
        this.setTimeLeft(500/unit.getStrength());
        this.targetCubeLocation = targetCubeLocation;
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
    private final Unit unit;
    
    /**
     * Variable registering the target location of this work.
     */
	private final int[] targetCubeLocation;
	
    /**
     * Variable registering the time left until finishing this work.
     */
    private double timeLeft;

    /* Methods */
    
    /**
     * Return whether this work has been dictated by a statement.
     */
    @Override
	@Basic
	@Raw
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
	@Basic
	@Raw
    public boolean isFinished() {
        return this.isFinished;
    }

    /**
     * Finish this work.
     */
    @Override
    public void finishActivity() {
        this.isFinished = true;
        this.getUnit().activityFinished();
    }
    
    /**
     * Return the unit of this work.
     */
	@Basic
	@Raw
    private Unit getUnit() {
    	return this.unit;
    }
    
    /**
     * Return the target cube of this work.
     */
	@Basic
	@Raw
    private int[] getTargetCubeLocation() {
    	return this.targetCubeLocation;
    }
    
    /**
     * Check whether this work can have the target cube coordinates supplied by the given array as its target cube coordinates.
     * 
     * @param	targetCubeLocation
     * 			An array supplying the target cube coordinates for this work.
     * @return	True if and only if the given array's length is equal to three and if the target cube coordinates supplied by the given array are within the
     * 			borders of the world of this work's unit.
     */
    private boolean canHaveAsTargetCubeLocation(int[] targetCubeLocation) {
        return (targetCubeLocation.length == 3
        		&& targetCubeLocation[0] < this.getUnit().getWorld().getxSideSize()
        		&& targetCubeLocation[1] < this.getUnit().getWorld().getySideSize()
        		&& targetCubeLocation[2] < this.getUnit().getWorld().getzSideSize());
    }
    
    /**
     * Let the unit of this work conduct work.
     * 
     * @effect	If this work's unit is carrying an object, it drops it.
     * 			Otherwise, if this work's target cube location is in a workshop cube with a boulder and a log in it, the unit works out.
     * 			Or if either a boulder or either a log is in it, the unit picks it up.
     * 			Otherwise, if this work's target cube location is in a wood or rock cube, the cube collapses.
     * @effect	This work's unit finishes this activity.
     */
    private void conductWork() {
        if (this.getUnit().isCarrying()) {
            dropWork();
            return;
        }
        if (this.getUnit().getWorld().getCubeIDAt(this.getTargetCubeLocation()) == 3) {
            if (! this.getUnit().getWorld().getLogsAt(this.getTargetCubeLocation()).isEmpty() && ! this.getUnit().getWorld().getBouldersAt(this.getTargetCubeLocation()).isEmpty()) {
                workOutWork();
                return;
            }
        }
        if (! this.getUnit().getWorld().getBouldersAt(this.getTargetCubeLocation()).isEmpty() || ! this.getUnit().getWorld().getLogsAt(this.getTargetCubeLocation()).isEmpty()) {
            pickupWork();
            return;
        }
        if (this.getUnit().getWorld().getCubeIDAt(this.getTargetCubeLocation()) == 2 || this.getUnit().getWorld().getCubeIDAt(this.getTargetCubeLocation()) == 1) {
            destroyWork();
            return;
        }
        this.getUnit().activityFinished();
    }

    /**
     * Let this work's unit drop the object it is carrying.
     * 
     * @effect	This work's unit drops the object it is carrying.
     * @effect	This work's unit finishes this activity.
     * @effect	This work's unit gains 10 experience points.
     */
    private void dropWork() {
        this.getUnit().drop(this.getUnit().getCarriedObject()); 
        this.getUnit().activityFinished();
        this.getUnit().addXP(10);
    }
    
    /**
     * Let this work's unit work out.
     * 
     * @effect	The weight and toughness of this work's unit are increased by 10%.
     * @effect	The log and boulder used for the workout are unregistered.
     * @effect	This work's unit finishes this activity.
     * @effect	This work's unit gains 10 experience points.
     */
    private void workOutWork() {
        World world = this.getUnit().getWorld();
        Log log = world.getLogsAt(this.getTargetCubeLocation()).get(0);
        Boulder boulder = world.getBouldersAt(this.getTargetCubeLocation()).get(0);
        this.getUnit().setWeight((int) Math.ceil(this.getUnit().getWeight() * 1.1));
        this.getUnit().setToughness(((int) Math.ceil(this.getUnit().getToughness() * 1.1)));
        log.unregister();
        boulder.unregister();
        this.getUnit().activityFinished();
        this.getUnit().addXP(10);
    }

    /**
     * Let this work's unit pick up the object at the target cube.
     * 
     * @effect	The unit picks up the boulder or log.
     * @effect	This work's unit finishes this activity.
     * @effect	This work's unit gains 10 experience points.
     */
    private void pickupWork() {
        World world = this.getUnit().getWorld();
        List<Boulder> boulderList = world.getBouldersAt(this.getTargetCubeLocation());
        if (! boulderList.isEmpty()) {
            this.getUnit().carry(boulderList.get(0));
        } else {
            List<Log> logList = world.getLogsAt(this.getTargetCubeLocation());
            this.getUnit().carry(logList.get(0));
        }
        this.getUnit().activityFinished();
        this.getUnit().addXP(10);
    }
    
    /**
     * Let this work's unit destroy the cube.
     * 
     * @effect	The target cube is destroyed.
     * @effect	This work's unit finishes this activity.
     * @effect	This work's unit gains 10 experience points.
     */
    private void destroyWork() {
        this.getUnit().getWorld().destroyCube(this.getTargetCubeLocation());
        this.getUnit().activityFinished();
        this.getUnit().addXP(10);
    }
}