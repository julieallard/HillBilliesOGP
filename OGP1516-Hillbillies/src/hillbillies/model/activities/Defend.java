package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

import java.util.Random;

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

//TODO Units shall gain 20 experience points for
//every successful attempt at dodging, blocking or attacking. No experience
//points shall be awarded for unsuccessful attempts.
public class Defend implements IActivity {

    public Defend(Unit defender, Unit attacker) {
        this.attacker = attacker;
        this.defender = defender;
        this.setTimeLeft(1);
        this.random = new Random();
    }

    private final Unit attacker;
    private final Unit defender;
    private double timeLeft;
    private Random random; 
    
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
     * Return the time left for this Defense.
     */
    @Basic
    @Raw
    @Override
    public double returnSimpleTimeLeft() {
    	return this.timeLeft;
    }

    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    @Override
    public int getId() {
        return 2;
    }

    /**
     * Check whether the given time left is a valid time left for
     * any Defense.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return True if and only if the time left is above 0 and below 1.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
        return (Util.fuzzyGreaterThanOrEqualTo(timeLeft, 0) && Util.fuzzyLessThanOrEqualTo(timeLeft, 1));
    }

    /**
     * Set the time left for this Defense to the given timeLeft.
     *
     * @param  timeLeft
     *         The new time left for this Defense.
     * @post   The time left fot this new Defense is equal to
     *         the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any
     *         Defense.
     */
    @Raw
    public void setTimeLeft(double timeLeft) throws IllegalTimeException {
        if (! isValidTimeLeft(timeLeft))
            throw new IllegalTimeException();
        this.timeLeft = timeLeft;
    }

    private void conductDefense() {
        defender.activityFinished();
    }
    
}
