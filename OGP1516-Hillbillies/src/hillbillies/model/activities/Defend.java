package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

/**
 * A class of defenses involving a defender.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Defend implements IActivity {

	/**
	 * Initialize this new defense with given defender and given attacker.
     *
     * @param	defender
     *			The defender for this new defense.
     * @post	The defender of this new defense is equal to the given defender.
     * @effect	The time left for this new attack is set to 1.
     */
    public Defend(Unit defender) {
        this.defender = defender;
        this.setTimeLeft(1);
    }

    /* Variables */
    
    /**
     * Variable registering whether this defense has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering the defender of this defense.
     */
    private final Unit defender;

    /**
     * Variable registering the time left until this defense is finished.
     */
    private double timeLeft;

    /* Methods */
    
    /**
     * Return whether this defense has been dictated by a statement.
     */
    @Override
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this defense.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}

    /**
     * Update this attack according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     * @effect	If the given time is equal of higher than the time left until finishing this attack, conduct this attack.
     * @effect	If the given time is lower than the time left until finishing this attack, the given amount of time is subtracted from the time left for this attack.
     */
    @Override
    public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.returnSimpleTimeLeft())) {
            this.conductDefense();
        } else {
        	double time = returnSimpleTimeLeft() - dt;
            this.setTimeLeft(time);
        }
    }

    /**
     * Return the time left until this defense is finished.
     */
    @Basic
    @Raw
    @Override
    public double returnSimpleTimeLeft() {
    	return this.timeLeft;
    }

    /**
     * Check whether this defense can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    /**
     * Return the ID of this defense.
     */
    @Override
    public int getId() {
        return 2;
    }

    private Boolean isFinished;

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void finishActivity() {

    }

    /**
     * Check whether the given time left is a valid time left for any Defense.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return True if and only if the time left is greater or equal to 0 and less or equal to 1.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
        return (Util.fuzzyGreaterThanOrEqualTo(timeLeft, 0) && Util.fuzzyLessThanOrEqualTo(timeLeft, 1));
    }

    /**
     * Set the time left for this Defense to the given timeLeft.
     *
     * @param  timeLeft
     *         The new time left for this Defense.
     * @post   The time left fot this new Defense is equal to the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any Defense.
     */
    @Raw
    public void setTimeLeft(double timeLeft) throws IllegalTimeException {
        if (! isValidTimeLeft(timeLeft))
            throw new IllegalTimeException("thrown by advanceTime from Defend timeleft duration was not valid");
        this.timeLeft = timeLeft;
    }

    /**
     * Let the defender and the attacker of this Attack conduct the Attack.
     * 
     * @effect This Attack is finished.
     */
    private void conductDefense() {
        defender.activityFinished();
    }
    
}
