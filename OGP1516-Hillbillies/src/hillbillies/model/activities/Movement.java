package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.IllegalLocation;
import ogp.framework.util.Util;

import java.util.Arrays;

/**
 * A class of movements involving a unit and a destination.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Movement implements IActivity {

	/**
	 * Initialize this new movement with given unit and given destination.
     *
     * @param  unit
     *         The unit for this new movement.
     * @param  destination
     * 		   The destination for this movement.
     * @post   The unit of this new movement is equal to the given unit.
     * @effect The destination of this new movement is equal to the cube with given destination.
     */
    public Movement(Unit unit, int[] destination) throws IllegalLocation {
        this.unit = unit;
        if (! canHaveAsDestination(destination))
            throw new IllegalLocation();
        Cube destCube = new Cube(destination);
        this.destinationCube = destCube;
        this.pathing = new Astar(unit);
    }

    /* Variables */
    
    /**
     * Variable registering whether this movement has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering whether this movement is finished.
     */
    private boolean isFinished = false;
    
    /**
     * Variable registering the unit of this movement.
     */
    private final Unit unit;
    
    /**
     * Variable registering the destination cube of this movement.
     */
    private final Cube destinationCube;
    
    /**
     * Variable registering the pathing of this movement, calculated according to the A star algorithm.
     */
    private final Astar pathing;
    
    /**
     * Variable registering the cube where the next stop of this movement will be.
     */
    private Cube nextStop;

    /* Methods */
    
    /**
     * Return whether this movement has been dictated by a statement.
     */
    @Override
	@Basic
	@Raw
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this movement according to the given flag.
	 * 
	 * @param	flag
	 * 			The new state of being dictated by a statement for this movement.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}

	// TODO 
    /**
     * Update this movement according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     * @effect
     */
    @Override
    public void advanceActivityTime(double dt) {
        if (nextStop == null) {
            boolean foundpath = setNextStop();
            if (! foundpath) {
                this.getUnit().activityFinished();
                this.getUnit().addXP(1);
                return;
            }
        }
        double[] nextStopFine = Arrays.stream(nextStop.locArray).asDoubleStream().toArray();
        nextStopFine[0] = nextStopFine[0] + 0.5;
        nextStopFine[1] = nextStopFine[1] + 0.5;
        nextStopFine[2] = nextStopFine[2] + 0.5;
        double[] currLockFine = this.getUnit().getLocation().getArray();
        double[] speed = normalizeSpeed(currLockFine, nextStopFine);
        if (this.getUnit().isSprinting()) {
            double sprintTimeLeft=getTimeLeftSprinting();
            if (Util.fuzzyGreaterThanOrEqualTo(dt,sprintTimeLeft)) dt=sprintTimeLeft;
            this.getUnit().setSprinting(false);
        }
        double timeleft;
        if (speed[0] != 0) {
        	timeleft = (nextStopFine[0] - currLockFine[0]) / speed[0];
        } else if (speed[1] != 0) {
        	timeleft = (nextStopFine[1] - currLockFine[1]) / speed[1];
        } else if (speed[2] != 0) {
        	timeleft = (nextStopFine[2] - currLockFine[2]) / speed[2];
        } else
        	throw new RuntimeException("the next stop is equal to this stop (Movement)");
        if (Util.fuzzyGreaterThanOrEqualTo(dt,timeleft)) {
            this.getUnit().setLocation(nextStopFine);
            this.nextStop = null;
            dt = timeleft;
        } else {
            double[] newloc = new double[3];
            newloc[0] = currLockFine[0] + speed[0] * dt;
            newloc[1] = currLockFine[1] + speed[1] * dt;
            newloc[2] = currLockFine[2] + speed[2] * dt;
            this.getUnit().setLocation(newloc);
        }
        if (this.getUnit().isSprinting()) {
            int usedStamPoints = (int) Math.ceil(dt * 10);
            int newStamPoints = this.getUnit().getCurrentStaminaPoints() - usedStamPoints;
            this.getUnit().setCurrentStaminaPoints(newStamPoints);
        }
    }

    /**
     * Check whether this movement can be interrupted by the given activity.
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
     * Return the ID of this movement.
     */
    @Override
    public int getId() {
        return 3;
    }

    /**
     * Return whether this movement is finished.
     */
    @Override
	@Basic
	@Raw
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Finish this movement.
     */
    @Override
    public void finishActivity() {
        this.isFinished = true;
        this.getUnit().activityFinished();
    }

    /**
     * Return the unit of this movement.
     */
	@Basic
	@Raw
    private Unit getUnit() {
    	return this.unit;
    }
    
    /**
     * Return the time left until the sprint is finished.
     */
    public double getTimeLeftSprinting() {
        if (this.getUnit().getCurrentStaminaPoints() == 0)
        	return 0;
        return 10 * (double) this.getUnit().getCurrentStaminaPoints();
    }
    
    /**
     * Return the destination of this movement.
     */
    @Basic
    @Raw
    @Immutable
    public int[] getDestination() {
        return this.destinationCube.locArray;
    }

    /**
     * Check whether this movement can have the given destination as its destination.
     *
     * @param  destination
     * 		   The destination to check.
     * @return True if and only if the destination is a three dimensional array and if the unit can have the given
     * 		   destination as a cube location.
     */
    @Raw
    public boolean canHaveAsDestination(int[] destination) {
        return destination.length == 3 && this.getUnit().getWorld().canHaveAsCubeLocation(destination, this.getUnit());
    }

    /**
     * Return whether a next destination cube was found as this movement still needs to be continued until the next stop
     * will be this movement's destination or not.
     */
    public boolean setNextStop() {
        Cube[] path = pathing.FindPath(new Cube(this.getUnit().getLocation().getCubeLocation()), destinationCube);
        if (Arrays.equals(path, new Cube[]{new Cube(new int[]{-1, -1, -1})})) {
            return false;
        }
        this.nextStop = path[0];
        return true;
    }

    /**
     * Return the speed of this movement.
     */
    public double getSpeed() {
        double basespeed = 0.75 * ((double) this.getUnit().getStrength() + this.getUnit().getAgility()) / ((double) this.getUnit().getWeight());
        double realSpeed;
        double zDiff = (this.getUnit().getLocation().getZLocation() - (((double) nextStop.locArray[2]) + 0.5));
        if (zDiff > 0)
            realSpeed = basespeed * 1.2;
        else if (zDiff < 0) 
            realSpeed = basespeed * 0.5;
        else
            realSpeed = basespeed;
        if (this.getUnit().isSprinting())
            realSpeed = realSpeed * 2;
        return realSpeed;
    }

    /**
     * Return an array supplying the normalized speed of this movement.
     * 
     * @param  startArray
     * 		   The array supplying the start x, y and z coordinate for this movement.
     * @param  destArray
     * 		   The array supplying the destination x, y and z coordinate for this movement.
     */
    private static double[] normalizeSpeed(double[] startArray, double[] destArray) {
        double dx = destArray[0] - startArray[0];
        double dy = destArray[1] - startArray[1];
        double dz = destArray[2] - startArray[2];
        double norm = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
        double newX = dx / norm;
        double newY = dy / norm;
        double newZ = dy / norm;
        return new double[]{newX, newY, newZ};
    }
    
}