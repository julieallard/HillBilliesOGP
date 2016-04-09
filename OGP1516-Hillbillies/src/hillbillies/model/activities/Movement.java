package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.UnitIllegalLocation;
import ogp.framework.util.Util;

import java.util.Arrays;
import java.util.Collections;

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

//TODO Units shall gain 1 experience point for every completed movement step.
//No experience points shall be awarded if the movement was interrupted.
public class Movement implements IActivity {

    public Movement(Unit unit, int[] destination) throws UnitIllegalLocation {
        this.unit = unit;
        if (!canHaveAsDestination(destination))
            throw new UnitIllegalLocation();
        Cube destincube = new Cube(destination);
        this.destination = destincube;
        this.pathing = new Astar(unit);
    }

    private final Astar pathing;
    private final Unit unit;
    private final Cube destination;
    private Cube nextStop;

	/**
	 * No documentation required.
	 */
    @Override
    public void advanceActivityTime(double dt) {
        if (nextStop == null) {
            boolean foundpath = setNextStop();
            if (! foundpath) {
                unit.activityFinished();
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

        if(speed[0]!=0){timeleft=(nextStopFine[0]-currLockFine[0])/speed[0];}
        else if(speed[1]!=0){timeleft=(nextStopFine[1]-currLockFine[1])/speed[1];}
        else if(speed[2]!=0){timeleft=(nextStopFine[2]-currLockFine[2])/speed[2];}
        else {throw new RuntimeException("the next stop is equal to this stop (Movement)");}

        if (Util.fuzzyGreaterThanOrEqualTo(dt,timeleft)){
            unit.setLocation(nextStopFine);
            this.nextStop=null;
            dt=timeleft;
        }
        else {
            double[] newloc=new double[3];
            newloc[0]=currLockFine[0]+speed[0]*dt;
            newloc[1]=currLockFine[1]+speed[1]*dt;
            newloc[2]=currLockFine[2]+speed[2]*dt;
            this.unit.setLocation(newloc);
        }
        if (unit.isSprinting()){
            int usedStamPoints=(int) Math.ceil(dt*10);
            int newStamPoints=unit.getCurrentStaminaPoints()-usedStamPoints;
            unit.setCurrentStaminaPoints(newStamPoints);
        }


    }
    public double getTimeLeftSprinting(){
        int stamPoints=unit.getCurrentStaminaPoints();
        if (stamPoints==0) return 0;
        return 10*(double) stamPoints;
    }

    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        throw new IllegalArgumentException("A movement does not have a SimpleTimeLeft attribute.");
    }

    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return true;
    }

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
     * @param destination The destination to check.
     * @return True if and only if the destination is an array containing
     * 3 coordinates and if the Unit can have the given
     * destination as a cube location.
     */
    @Raw
    public boolean canHaveAsDestination(int[] destination) {
        if (destination.length != 3)
            return false;
        return this.unit.getWorld().canHaveAsCubeLocation(destination, unit);
    }

    public boolean setNextStop() {
        Cube[] path = pathing.FindPath(new Cube(unit.getLocation().getCubeLocation()), destination);
        if (Arrays.equals(path, new Cube[]{new Cube(new int[]{-1, -1, -1})})) {
            unit.activityFinished();
            return false;
        }
        this.nextStop = path[0];
        return true;

    }

    public double getSpeed() {
        double basespeed = 0.75 * ((double) unit.getStrength() + unit.getAgility()) / ((double) unit.getWeight());
        double realSpeed;
        double zDiff = (unit.getLocation().getZLocation() - (((double) nextStop.locArray[2]) + 0.5));
        if (zDiff > 0) {
            realSpeed = basespeed * 1.2;
        } else if (zDiff < 0) {
            realSpeed = basespeed * 0.5;
        } else {
            realSpeed = basespeed;
        }
        if (unit.isSprinting()) {
            realSpeed = realSpeed * 2;
        }
        return realSpeed;
    }

    private static double[] normalizeSpeed(double[] startArray, double[] destArray) {
        double dx = destArray[0] - startArray[0];
        double dy = destArray[1] - startArray[1];
        double dz = destArray[2] - startArray[2];
        double norm = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
        double newX = dx / norm;
        double newY = dy / norm;
        double newZ = dy / norm;
        assert (Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2) == 1);
        return new double[]{newX, newY, newZ};
    }
    

}