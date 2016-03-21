package hillbillies.model.activities;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

import java.util.Random;


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
    public boolean hasSimpleTimeLeft() {
        return true;
    }

    @Override
    public double returnSimpleTimeLeft() {
    	return this.timeLeft;

    }

    @Override
    public boolean canBeInterruptedBy(String activity) {
        return false;
    }

    @Override
    public void interrupt()throws IllegalArgumentException {
        throw new IllegalArgumentException("Defense cannot be interrupted");

    }


    private final Unit attacker;
    private final Unit defender;
    /** TO BE ADDED TO CLASS HEADING
     * @invar  The timeLeft of each Defense must be a valid timeLeft for any
     *         Defense.
     *       | isValidtimeLeft(gettimeLeft())
     */


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

    private void conductDefense(){
        int agilAttack=this.attacker.getAgility();
        int agilDef=this.defender.getAgility();
        int strAttack=this.attacker.getCurrentStaminaPoints();
        int strDef=this.defender.getStrength();
        boolean willDodge = (this.random.nextDouble()<=0.2*(((double) agilDef)/agilAttack));
        boolean willBlock = (this.random.nextDouble())<



    }


}
