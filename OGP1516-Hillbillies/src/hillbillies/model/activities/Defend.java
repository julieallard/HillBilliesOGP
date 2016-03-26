package hillbillies.model.activities;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

import java.util.Random;

/**
 * the Id's of the activities are the following:
 * 0: noActivity
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
 */


public class Defend implements IActivity {

    public Defend(Unit defender,Unit attacker){
        this.attacker=attacker;
        this.defender=defender;
        this.settimeLeft(1);
        this.random= new Random();
    }

    @Override
    public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt,this.gettimeLeft())){
            this.conductDefense();
            return;
        }
        this.settimeLeft(gettimeLeft()-dt);
    }

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


    private final Unit attacker;
    private final Unit defender;


    /**
     * Initialize this new Defense with given timeLeft.
     *
     * @param  timeLeft
     *         The timeLeft for this new Defense.
     * @effect The timeLeft of this new Defense is set to
     *         the given timeLeft.
     *       | this.settimeLeft(timeLeft)
     */


    /**
     * Return the timeLeft of this Defense.
     */
    @Basic
    @Raw
    public double gettimeLeft() {
        return this.timeLeft;
    }

    /**
     * Check whether the given timeLeft is a valid timeLeft for
     * any Defense.
     *
     * @param  timeLeft
     *         The timeLeft to check.
     * @return
     *       | result == (0<=timeleft<=1
     */
    public static boolean isValidtimeLeft(double timeLeft) {
        return (Util.fuzzyGreaterThanOrEqualTo(timeLeft,0)&&Util.fuzzyLessThanOrEqualTo(timeLeft,1));
    }

    /**
     * Set the timeLeft of this Defense to the given timeLeft.
     *
     * @param  timeLeft
     *         The new timeLeft for this Defense.
     * @post   The timeLeft of this new Defense is equal to
     *         the given timeLeft.
     *       | new.gettimeLeft() == timeLeft
     * @throws IllegalTimeException
     *         The given timeLeft is not a valid timeLeft for any
     *         Defense.
     *       | ! isValidtimeLeft(gettimeLeft())
     */
    @Raw
    public void settimeLeft(double timeLeft)
            throws IllegalTimeException {
        if (! isValidtimeLeft(timeLeft))
            throw new IllegalTimeException();
        this.timeLeft = timeLeft;
    }

    /**
     * Variable registering the timeLeft of this Defense.
     */
    private double timeLeft;
    /**
     * Variable random is kept alive for performance reasons
     */
    private Random random;

    private void conductDefense() {
        defender.activityFinished();
    }


}
