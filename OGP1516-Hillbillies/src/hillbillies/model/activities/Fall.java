package hillbillies.model.activities;

import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.MovableWorldObject;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;

/**
 * A class of Fall activities involving a movable world object.
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
public class Fall implements IActivity {

	/**
	 * Initialize this new Fall with given movable world object.
     *
     * @param  object
     *         The movable world object for this new Fall.
     * @post   The object of this new Fall is equal to the given object.
     */
    public Fall(MovableWorldObject object) {
        this.object = object;
        this.damagetobedone = 0;
    }

    /**
     * Variable registering the object of this Fall.
     */
    private MovableWorldObject object;
    
    /**
     * Variable registering the damage points of this Fall that have to be dealt with.
     */
    private double damagetobedone;
    
    /**
     * Variable registering the distance fallen of this Fall.
     */
    private double distancefallen;

    @Override
    public boolean isDictatedByStatement() {
        return false;
    }

    @Override
    public Statement getControllingStatement() {
        throw new IllegalArgumentException("Fall cannot be directly dictated by statement");
    }

    /**
	 * No documentation required.
	 */
    @Override
    public void advanceActivityTime(double dt) {
        advanceFallLocation(dt);
    }

    /**
     * Return the time left until finishing this Fall.
     */
    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        return 0;
    }

    /**
     * Check whether this Fall can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    /**
     * Return the ID of this Fall.
     */
    @Override
    public int getId() {
        return 6;
    }

    private Boolean isFinished;

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void finishActivity() {
        this.isFinished = true;
        if (this.object instanceof Unit) object.activityFinished();
    }

    /**
     * Let the object continue this Fall for the given amount of time.
     * 
     * @param  dt
     * 		   The amount of time to let the object fall.
     * @effect The object falls during the given amount of time and its z coordinate will proportionally be substracted by
     * 		   three times the amount of time it falls. If the object reaches a position whose underlying cube is solid and the
     * 		   z coordinate is below the centre of the current cube, the object is moved back, up to the centre of this cube.
     * 		   If the object is a Unit, it will need to deal with a certain amount of damage points calculated as
     * 		   ten points per z-level they fall.
     * 		   Lastly, this Fall is finished.
     */
    private void advanceFallLocation(double dt) {
        VLocation oldLoc = this.object.getLocation();
        double newZ = oldLoc.getCubeLocation()[2] - 3*dt;
        if (this.object.getWorld().willBreakFall(oldLoc.getCubeLocation()) && newZ <= oldLoc.getCubeLocation()[2] + 0.5) {
            this.object.setLocation(oldLoc.getXLocation(), oldLoc.getYLocation(), oldLoc.getCubeLocation()[2] + 0.5);
            this.damagetobedone += 10 * (oldLoc.getZLocation() - this.object.getLocation().getZLocation());
            if (this.object instanceof Unit) {
                ((Unit) object).dealDamage(this.damagetobedone);
            this.object.activityFinished();
            return;
            }
        }
        VLocation newPossibleLoc = new VLocation(oldLoc.getXLocation(), oldLoc.getYLocation(), oldLoc.getZLocation() - 3*dt, this.object);
        if (! this.object.getWorld().willBreakFall(newPossibleLoc.getCubeLocation())) {
            this.object.setLocation(newPossibleLoc);
            this.damagetobedone = 3*dt*10;
            return;
        } else if (newPossibleLoc.getZLocation() - Math.floor(newPossibleLoc.getZLocation()) <= 0.5)
            this.object.setLocation(newPossibleLoc.getXLocation(), newPossibleLoc.getYLocation(), Math.floor(newPossibleLoc.getZLocation()) + 0.5);
        this.damagetobedone += 10 * (oldLoc.getZLocation() - this.object.getLocation().getZLocation());
        if (this.object instanceof Unit) {
            ((Unit) object).dealDamage(this.damagetobedone);
        }
        this.object.activityFinished();
    }



}
