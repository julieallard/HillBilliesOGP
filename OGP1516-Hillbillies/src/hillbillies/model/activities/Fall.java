package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.MovableWorldObject;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;

/**
 * A class of falls involving a movable world object.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Fall implements IActivity {

	/**
	 * Initialize this new fall with given movable world object.
     *
     * @param  object
     *         The movable world object for this new fall.
     * @post   The object of this new fall is equal to the given object.
     */
    public Fall(MovableWorldObject object) {
        this.object = object;
        }

    /* Variables */
    
    /**
     * Variable registering whether this fall has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering whether this fall is finished.
     */
    private boolean isFinished = false;
    
    /**
     * Variable registering the object of this fall.
     */
    private final MovableWorldObject object;
    
    /**
     * Variable registering the damage points of this fall that have to be dealt with.
     */
    private double damageToBeDone = 0;

    /* Methods */
    
    /**
     * Return whether this fall has been dictated by a statement.
     */
    @Override
	@Basic
	@Raw
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this fall.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}

	// TODO
    /**
     * Update this fall according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     * @effect	The object falls during the given amount of time and its z coordinate is proportionally reduced by 3 times the amount of time it falls.
     *			If the object reaches a position whose underlying cube is solid and the z coordinate is below the centre of the current cube,
     *			the object is moved back, up to the centre of this cube.
     * @effect	If the object is a unit, it will deal with a damage points calculated as 10 points per z-level they fall.
     * @effect	This fall's object finishes this activity.
     */
    @Override
    public void advanceActivityTime(double dt) {
        VLocation oldLoc = this.getObject().getLocation();
        double newZ = oldLoc.getCubeLocation()[2] - 3*dt;
        if (this.getObject().getWorld().willBreakFall(oldLoc.getCubeLocation()) && newZ <= oldLoc.getCubeLocation()[2] + 0.5) {
            this.getObject().setLocation(oldLoc.getXLocation(), oldLoc.getYLocation(), oldLoc.getCubeLocation()[2] + 0.5);
            this.setDamageToBeDone(this.getDamageToBeDone() + 10 * (oldLoc.getZLocation() - this.getObject().getLocation().getZLocation()));
            if (this.getObject() instanceof Unit) {
                ((Unit) getObject()).dealDamage(this.getDamageToBeDone());
            this.getObject().activityFinished();
            return;
            }
        }
        VLocation newPossibleLoc = new VLocation(oldLoc.getXLocation(), oldLoc.getYLocation(), oldLoc.getZLocation() - 3*dt, this.getObject());
        if (! this.getObject().getWorld().willBreakFall(newPossibleLoc.getCubeLocation())) {
            this.getObject().setLocation(newPossibleLoc);
            this.setDamageToBeDone(3*dt*10);
            return;
        } else if (newPossibleLoc.getZLocation() - Math.floor(newPossibleLoc.getZLocation()) <= 0.5)
            this.getObject().setLocation(newPossibleLoc.getXLocation(), newPossibleLoc.getYLocation(), Math.floor(newPossibleLoc.getZLocation()) + 0.5);
        this.setDamageToBeDone(this.getDamageToBeDone() + 10 * (oldLoc.getZLocation() - this.getObject().getLocation().getZLocation()));
        if (this.getObject() instanceof Unit) {
            ((Unit) getObject()).dealDamage(this.getDamageToBeDone());
        }
        this.getObject().activityFinished();
    }

    /**
     * Check whether this fall can be interrupted by the given activity.
     * 
     * @param	activity
     *			The activity to check.
     * @return	Always false.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    /**
     * Return the ID of this fall.
     */
    @Override
    public int getId() {
        return 6;
    }

    /**
     * Return whether this fall is finished.
     */
    @Override
	@Basic
	@Raw
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Finish this defense.
     */
    @Override
    public void finishActivity() {
        this.isFinished = true;
        if (this.getObject() instanceof Unit)
        	getObject().activityFinished();
    }
    
    /**
     * Return the object of this fall.
     */
    private MovableWorldObject getObject() {
    	return this.object;
    }
    
    /**
     * Return the damage points of this fall that have to be dealt with.
     */
	@Basic
	@Raw
    private double getDamageToBeDone() {
    	return this.damageToBeDone;
    }
    
    /**
     * Set the damage points of this fall that have to be dealt with to the given damage points.
     */
    private void setDamageToBeDone(double damage) {
    	this.damageToBeDone = damage;
    }

}