package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.IllegalLocation;
import ogp.framework.util.Util;

import java.util.Arrays;

/**
 * A class of Movement activites involving a unit and a destination.
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
public class Movement implements IActivity {

	/**
	 * Initialize this new Movement with given unit and given destination.
     *
     * @param  unit
     *         The unit for this new Movement.
     * @param  destination
     * 		   The destination for this Movement.
     * @post   The unit of this new Movement is equal to the given unit.
     * @effect The destination of this new Movement is equal to the cube with given destination.
     */
    public Movement(Unit unit, int[] destination) throws IllegalLocation {
        this.unit = unit;
        if (! canHaveAsDestination(destination))
            throw new IllegalLocation();
        Cube destincube = new Cube(destination);
        this.destination = destincube;
        this.pathing = new Astar(unit);
    }

    public Movement(Unit unit,int[] destination,Statement controllingStatement) {
        this(unit,destination);
        this.controllingStatement = controllingStatement;
        this.isDictatedByStatement = true;
    }

    private boolean isDictatedByStatement;

    private Statement controllingStatement;

    /**
     * Variable registering the pathing of this Movement, calculated according to the A star algorithm.
     */
    private final Astar pathing;
    
    /**
     * Variable registering the unit of this Movement.
     */
    private final Unit unit;
    
    /**
     * Variable registering the destination cube of this Movement.
     */
    private final Cube destination;
    
    /**
     * Variable registering the cube where the next stop of this Movement will be.
     */
    private Cube nextStop;

    @Override
    public boolean isDictatedByStatement() {
        return isDictatedByStatement;
    }

    @Override
    public Statement getControllingStatement() {
        return this.controllingStatement;
    }

    /**
	 * No documentation required.
	 */
    @Override
    public void advanceActivityTime(double dt) {
        if (nextStop == null) {
            boolean foundpath = setNextStop();
            if (! foundpath) {
                unit.activityFinished();
                if (isDictatedByStatement()) controllingStatement.finishExecuting();
                unit.addXP(1);
                return;
            }
        }
        double[] nextStopFine = Arrays.stream(nextStop.locArray).asDoubleStream().toArray();
        nextStopFine[0] = nextStopFine[0] + 0.5;
        nextStopFine[1] = nextStopFine[1] + 0.5;
        nextStopFine[2] = nextStopFine[2] + 0.5;
        double[] currLockFine = unit.getLocation().getArray();
        double[] speed = normalizeSpeed(currLockFine, nextStopFine);
        if (unit.isSprinting()) {
            double sprintTimeLeft=getTimeLeftSprinting();
            if (Util.fuzzyGreaterThanOrEqualTo(dt,sprintTimeLeft)) dt=sprintTimeLeft;
            unit.setSprinting(false);
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
            unit.setLocation(nextStopFine);
            this.nextStop = null;
            dt = timeleft;
        } else {
            double[] newloc = new double[3];
            newloc[0] = currLockFine[0] + speed[0] * dt;
            newloc[1] = currLockFine[1] + speed[1] * dt;
            newloc[2] = currLockFine[2] + speed[2] * dt;
            this.unit.setLocation(newloc);
        }
        if (unit.isSprinting()) {
            int usedStamPoints = (int) Math.ceil(dt * 10);
            int newStamPoints = unit.getCurrentStaminaPoints() - usedStamPoints;
            unit.setCurrentStaminaPoints(newStamPoints);
        }
    }
    
    /**
     * Return the time left for sprinting.
     */
    public double getTimeLeftSprinting() {
        if (unit.getCurrentStaminaPoints() == 0)
        	return 0;
        return 10 * (double) unit.getCurrentStaminaPoints();
    }

    /**
     * Return the time left until finishing this Movement.
     */
    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        throw new IllegalArgumentException("A movement does not have a SimpleTimeLeft attribute.");
    }

    /**
     * Check whether this Movement can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return true;
    }

    /**
     * Return the ID of this Movement.
     */
    @Override
    public int getId() {
        return 3;
    }


    /**
     * Return the destination of this Movement.
     */
    @Basic
    @Raw
    @Immutable
    public int[] getDestination() {
        return this.destination.locArray;
    }

    /**
     * Check whether this Movement can have the given destination as its destination.
     *
     * @param  destination
     * 		   The destination to check.
     * @return True if and only if the destination is a three dimensional array and if the Unit can have the given
     * 		   destination as a cube location.
     */
    @Raw
    public boolean canHaveAsDestination(int[] destination) {
        if (destination.length != 3)
            return false;
        return this.unit.getWorld().canHaveAsCubeLocation(destination, unit);
    }

    /**
     * Return whether a next destination cube was found as this Movement still needs to be continued until the next stop
     * will be this Movement's destination or not.
     */
    public boolean setNextStop() {
        Cube[] path = pathing.FindPath(new Cube(unit.getLocation().getCubeLocation()), destination);
        if (Arrays.equals(path, new Cube[]{new Cube(new int[]{-1, -1, -1})})) {
            return false;
        }
        this.nextStop = path[0];
        return true;
    }

    /**
     * Return the speed of this Movement.
     */
    public double getSpeed() {
        double basespeed = 0.75 * ((double) unit.getStrength() + unit.getAgility()) / ((double) unit.getWeight());
        double realSpeed;
        double zDiff = (unit.getLocation().getZLocation() - (((double) nextStop.locArray[2]) + 0.5));
        if (zDiff > 0)
            realSpeed = basespeed * 1.2;
        else if (zDiff < 0) 
            realSpeed = basespeed * 0.5;
        else
            realSpeed = basespeed;
        if (unit.isSprinting())
            realSpeed = realSpeed * 2;
        return realSpeed;
    }

    /**
     * Return an array supplying the normalized speed of this Movement.
     * 
     * @param  startArray
     * 		   The array supplying the start x, y and z coordinate for this Movement.
     * @param  destArray
     * 		   The array supplying the destination x, y and z coordinate for this Movement.
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

    private Boolean isFinished;

    @Override
    public boolean isFinished() {
        return isFinished;
    }


}