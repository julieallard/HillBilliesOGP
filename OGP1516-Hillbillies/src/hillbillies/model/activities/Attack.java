package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.IActivity;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.exceptions.UnitIllegalLocation;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;
import ogp.framework.util.Util;

/**
 * A class of Attack activities involving an attacker and a defender.
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
public class Attack implements IActivity {

	/**
	 * Initialize this new Attack with given attacker and given defender.
     *
     * @param  attacker
     *         The attacker for this new Attack.
     * @param  defender
     *         The defender for this new Attack.
     * @post   The attacker of this new Attack is equal to the given attacker.
     * @post   The defender of this new Attack is equal to the given defender.
     */
    public Attack(Unit attacker, Unit defender) throws IllegalTimeException {
        this.attacker = attacker;
        this.defender = defender;
        this.setTimeLeft(1);
    }

    public Attack(Unit attacker, Unit defender, Statement controllingStatement, boolean dictatedByStatement) {
        this(attacker,defender);
        this.controllingStatement = controllingStatement;
        DictatedByStatement = dictatedByStatement;
    }

    private boolean DictatedByStatement;

    private Statement controllingStatement;
    
    /**
     * Variable registering the attacker of this Attack.
     */
    private final Unit attacker;
    
    /**
     * Variable registering the defender of this Attack.
     */
    private final Unit defender;
    
    /**
     * Variable registering the time left until finishing this Attack.
     */
    private double timeLeft;

    @Override
    public boolean isDictatedByStatement() {
        return DictatedByStatement;
    }

    @Override
    public Statement getControllingStatement() {
        return controllingStatement;
    }

    /**
	 * No documentation required.
	 */
    @Override
    public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.returnSimpleTimeLeft())) {
            this.conductAttack();
        } else {
            double time = returnSimpleTimeLeft() - dt;
            this.setTimeLeft(time);
        }
    }
    
    /**
     * Return the time left until finishing this Attack.
     */
    @Basic
    @Raw
    @Override
    public double returnSimpleTimeLeft() {
        return this.timeLeft;
    }
    
    /**
     * Check whether this Attack can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    /**
     * Return the ID of this Attack.
     */
    @Override
    public int getId() {
        return 1;
    }

    /**
     * Check whether the given time left is a valid time left for any Attack.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return True if and only if the time left is greater or equal to 0 and less or equal to 1.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
    	return (Util.fuzzyGreaterThanOrEqualTo(timeLeft, 0) && Util.fuzzyLessThanOrEqualTo(timeLeft, 1));
    }

    /**
     * Set the time left for this Attack to the given time left.
     *
     * @param  timeLeft
     *         The new time left for this Attack.
     * @post   The time left of this new Attack is equal to the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any Attack.
     */
    @Raw
    private void setTimeLeft(double timeLeft) throws IllegalTimeException {
      if (! isValidTimeLeft(timeLeft))
        throw new IllegalTimeException("thrown by advanceTime from Attack timeleft duration was not valid");
      this.timeLeft = timeLeft;
    }

    /**
     * Let the attacker and the defender of this Attack conduct the Attack.
     * 
     * @effect The attacker's and the defender's orientation is set to an orientation in function of their x and y coordinate.
     * 		   With a certain probability in function of the defender's and the attacker's agility, the defender will dodge
     * 		   this Attack and jump away to a random location in the same or in a neighboring cube on the same z-level and gain
     * 		   20 experience points.
     * 		   If the defender fails in dodging this Attack, with a certain probability in function of the defender's and the
     * 		   attacker's strength and agility, the defender will block the attack and gain 20 experience points.
     * 		   If the defender fails in blocking this Attack, the defender needs to deal with a certain amount of damage points
     * 		   calculated in function of the attacker's agility and the attacker will gain 20 experience points.
     * 		   Lastly, this Attack is finished.
     */
	private void conductAttack() {
		attacker.setOrientation((float) Math.atan2(defender.getLocation().getYLocation() - attacker.getLocation().getYLocation(), defender.getLocation().getXLocation() - attacker.getLocation().getXLocation()));
		defender.setOrientation((float) Math.atan2(attacker.getLocation().getYLocation() - defender.getLocation().getYLocation(), attacker.getLocation().getXLocation() - defender.getLocation().getXLocation()));
		//dodging
		if (Math.random() <= 0.25 * (defender.getAgility() / attacker.getAgility())) {
			double oldX = defender.getLocation().getXLocation();
			double oldY = defender.getLocation().getYLocation();
			double oldZ = defender.getLocation().getZLocation();
			VLocation dodgeLoc;
			while (true) {
				double newX = oldX + 2*Math.random() - 1;
				double newY = oldY + 2*Math.random() - 1;
				dodgeLoc = new VLocation(newX, newY, oldZ, defender);
				if (VLocation.isValidLocation(dodgeLoc))
					break;
			}
			defender.setLocation(dodgeLoc.getArray());
			defender.addXP(20);
		//blocking
		} else if (Math.random() <= 0.25 * (defender.getStrength() + defender.getAgility()) / (attacker.getStrength() + attacker.getAgility())) {
			defender.addXP(20);
			return;
		//taking damage
		} else {
			double damage = attacker.getStrength() / 10;
			defender.dealDamage(damage);
			attacker.addXP(20);
		}
	    attacker.activityFinished();
	}
	
}