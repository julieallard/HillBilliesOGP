package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

/**
 * A class of Defend activities involving a defender and an attacker.
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
public class Defend implements IActivity {

	/**
	 * Initialize this new Defend with given defender and given attacker.
     *
     * @param  defender
     *         The defender for this new Defend.
     * @post   The defender of this new Defend is equal to the given defender.
     * @post   The attacker of this new Defend is equal to the given attacker.
     */
    public Defend(Unit defender) {
        this.defender = defender;
        this.setTimeLeft(1);

    }

    /**
     * Variable registering the defender of this Defend.
     */
    private final Unit defender;

    /**
     * Variable registering the time left until finishing this Defend.
     */
    private double timeLeft;

    @Override
    public boolean isDictatedByStatement() {
        return false;
    }

    @Override
    public Statement getControllingStatement() {
        throw new IllegalArgumentException("Defend can't be directly dictated by statement");

    }

    /**
	 * No documentation required.
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
     * Return the time left until finishing this Defend.
     */
    @Basic
    @Raw
    @Override
    public double returnSimpleTimeLeft() {
    	return this.timeLeft;
    }

    /**
     * Check whether this Defend can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    /**
     * Return the ID of this Defend.
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
