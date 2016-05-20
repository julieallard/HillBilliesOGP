package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

/**
 * A class of rests involving a unit.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Rest implements IActivity {

	/**
	 * Initialize this new rest with given unit.
     *
     * @param  unit
     *         The unit for this new rest.
     * @post   The unit of this new rest is equal to the given unit.
     */
	public Rest(Unit unit) {
		this.unit = unit;
		this.timeLeftTillFirstHP = ((double) 40) / this.unit.getToughness();
		this.gotFirstHP = false;
	}

	/* Variables */
	
    /**
     * Variable registering whether this rest has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering whether this rest is finished.
     */
    private boolean isFinished = false;
	
    /**
     * Variable registering the unit of this rest.
     */
	public final Unit unit;
	
    /**
     * Variable registering the time left until the first hitpoint will be recovered during this rest.
     */
	private double timeLeftTillFirstHP;
	
    /**
     * Variable registering whether the first hitpoint has already been recovered during this rest.
     */
	private boolean gotFirstHP;
	
    /* Methods */
    
    /**
     * Return whether this rest has been dictated by a statement.
     */
    @Override
    @Basic
    @Raw
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this rest.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}
	
    /**
     * Update this rest according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     * @effect	If this rest's unit has an amount of hitpoints which is lower than the highest possible value of hitpoints, it recovers hitpoints, calculated
     * 			by dividing its toughness by 200, per 0,2 seconds of time.
     * @effect	If this rest's unit has an amount of hitpoints equal to the highest possible value of hitpoints, it recovers stamina points, calculated
     * 			by dividing its toughness by 100, per 0,2 seconds of time.
     * @effect	If this rest's unit has an amount of hitpoints and stamina points equal to their highest possible value, respectively, it finishes its activity.
     */
	@Override
	public void advanceActivityTime(double dt) {
		if (! gotFirstHP) {
			if (Util.fuzzyGreaterThanOrEqualTo(dt, timeLeftTillFirstHP)) {
				this.gotFirstHP = true;
				this.timeLeftTillFirstHP = 0;
			} else
				this.timeLeftTillFirstHP = this.timeLeftTillFirstHP - dt;
		}
		if (this.getUnit().getCurrentHitPoints() < this.getUnit().getMaxPoints()) {
			int HPGaining = (int) Math.ceil((dt / 0.2) * (this.getUnit().getToughness() / 200));
			int HPNeeded = this.getUnit().getMaxPoints() - this.getUnit().getCurrentHitPoints();
			double timeForGainingHPNeeded = HPNeeded * 0.2 / (this.getUnit().getToughness() / 200);
			if (timeForGainingHPNeeded < dt) {
				double timeForGainingSP = dt - timeForGainingHPNeeded;
				this.getUnit().setCurrentHitPoints(this.getUnit().getCurrentHitPoints() + HPNeeded);
				advanceActivityTime(timeForGainingSP);
			} else
				this.getUnit().setCurrentHitPoints(this.getUnit().getCurrentHitPoints() + HPGaining);
				this.getUnit().activityFinished();
		} else if (this.getUnit().getCurrentStaminaPoints() < this.getUnit().getMaxPoints()) {
			int SPGaining = (int) Math.ceil((dt / 0.2) * (this.getUnit().getToughness() / 100));
			int SPNeeded = this.getUnit().getMaxPoints() - this.getUnit().getCurrentStaminaPoints();
			double timeForGainingSPNeeded = SPNeeded * 0.2 / (this.getUnit().getToughness() / 100);
			if (timeForGainingSPNeeded < dt) {
				this.getUnit().setCurrentHitPoints(this.getUnit().getCurrentStaminaPoints() + SPNeeded);
				this.getUnit().activityFinished();
			} else
				this.getUnit().setCurrentHitPoints(this.getUnit().getCurrentStaminaPoints() + SPGaining);
				this.getUnit().activityFinished();
			}
		else
			this.getUnit().activityFinished();
	}

    /**
     * Check whether this rest can be interrupted by the given activity.
     * 
     * @param	activity
     *			The activity to check.
     * @return	True if and only if the given activity is a defense or a fall or if this rest's unit already gained its first hitpoint.
     */
	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return activity instanceof Defend || activity instanceof Fall || gotFirstHP;
	}

    /**
     * Return the ID of this rest.
     */
	@Override
	public int getId() {
		return 5;
	}

    /**
     * Return whether this rest is finished.
     */
	@Override
	@Basic
	@Raw
	public boolean isFinished() {
		return this.isFinished;
	}

	/**
	 * Finish this rest.
	 */
	@Override
	public void finishActivity() {
		this.isFinished = true;
		unit.activityFinished();
	}
	
    /**
     * Return the unit of this rest.
     */
	@Basic
	@Raw
    private Unit getUnit() {
    	return this.unit;
    }

}