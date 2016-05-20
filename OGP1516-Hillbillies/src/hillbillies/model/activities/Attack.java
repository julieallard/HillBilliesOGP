package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

/**
 * A class of attack activities involving an attacker and a defender.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Attack implements IActivity {

	/**
	 * Initialize this new attack with given attacker and given defender.
     *
     * @param	attacker
     *			The attacker for this new attack.
     * @param	defender
     *			The defender for this new attack.
     * @post	The attacker of this new attack is equal to the given attacker.
     * @post	The defender of this new attack is equal to the given defender.
     * @effect	The time left for this new attack is set to 1.
     */
    public Attack(Unit attacker, Unit defender) throws IllegalTimeException {
        this.attacker = attacker;
        this.defender = defender;
        this.setTimeLeft(1);
    }

	/**
	 * Initialize this new attack with given attacker, given defender, given controlling statement and given dictated state of the statement.
     *
     * @param	attacker
     *			The attacker for this attack.
     * @param	defender
     *			The defender for this attack.
     * @param	controllingStatement
     * 			The controlling statement for this attack.
     * @param	dictatedByStatement
     * 			The dictated state of the statement for this attack.
     * @post	The controlling statement of this new attack is equal to the given controlling statement.
     * @post	The dictated state of the statement of this new attack is equal to the given dictated state of the statement.
     */
    public Attack(Unit attacker, Unit defender, Statement controllingStatement, boolean dictatedByStatement) {
        this(attacker, defender);
        this.controllingStatement = controllingStatement;
        this.dictatedByStatement = dictatedByStatement;
    }
    
	/**
	 * Initialize this new attack with given controlling statement, given attacker and given defender.
	 * 
	 * @param	controllingStatement
	 * 			The controlling statement for this attack.
	 * @param	attacker
	 * 			The attacker for this attack.
	 * @param	defender
	 * 			The defender for this attack.
	 */
    public Attack(Statement controllingStatement, Unit attacker, Unit defender) {
        this(attacker, defender);
        this.controllingStatement = controllingStatement;
    }

    /* Variables */
    
    /**
     * Variable registering whether the statement of this attack has been dictated.
     */
    private boolean dictatedByStatement;
    
    /**
     * Variable registering the controlling statement of this attack.
     */
    private Statement controllingStatement;
    
    /**
     * Variable registering the attacker of this attack.
     */
    private final Unit attacker;
    
    /**
     * Variable registering the defender of this attack.
     */
    private final Unit defender;
    
    /**
     * Variable registering the time left for this attack.
     */
    private double timeLeft;
    
    /**
     * Variable registering whether this attack is finished.
     */
    private boolean isFinished;

    /* Methods */
    
    /**
     * Return whether the statement of this attack has been dictated.
     */
    @Override
    public boolean isDictatedByStatement() {
        return dictatedByStatement;
    }

    /**
     * Return the controlling statement of this attack.
     */
    @Override
    public Statement getControllingStatement() {
        return controllingStatement;
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
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.returnSimpleTimeLeft()))
            this.conductAttack();
        else {
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
     * Check whether this attack can be interrupted by the given activity.
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
     * Return whether this attack is finished.
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Finish this attack.
     */
    @Override
    public void finishActivity() {

    }

    /**
     * Check whether the given time left is a valid time left for any attack.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return True if and only if the given time left is positive and equal or lower than 1.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
    	return (Util.fuzzyGreaterThanOrEqualTo(timeLeft, 0) && Util.fuzzyLessThanOrEqualTo(timeLeft, 1));
    }

    /**
     * Set the time left for this attack to the given time left.
     *
     * @param  timeLeft
     *         The new time left for this attack.
     * @post   The time left of this new attack is equal to the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any Attack.
     */
    @Raw
    private void setTimeLeft(double timeLeft) throws IllegalTimeException {
      if (! isValidTimeLeft(timeLeft))
        throw new IllegalTimeException("Invalid time left");
      this.timeLeft = timeLeft;
    }

    /**
     * Let the attacker and the defender of this attack conduct the attack.
     * 
     * @effect	The attacker's and the defender's orientation is set to an orientation in function of their x and y coordinate.
     * @effect	With a certain probability in function of the defender's and the attacker's agility, the defender will dodge this attack.
     * 			If the defender fails in dodging this attack, with a certain probability in function of the defender's and the attacker's strength and agility,
     * 			the defender will block the attack.
     *			If the defender fails in blocking this attack, the attempt at attacking is succesful.
     * @effect	This attack is finished.
     */
	private void conductAttack() {
		attacker.setOrientation((float) Math.atan2(defender.getLocation().getYLocation() - attacker.getLocation().getYLocation(), defender.getLocation().getXLocation() - attacker.getLocation().getXLocation()));
		defender.setOrientation((float) Math.atan2(attacker.getLocation().getYLocation() - defender.getLocation().getYLocation(), attacker.getLocation().getXLocation() - defender.getLocation().getXLocation()));
		if (Math.random() <= 0.25 * (defender.getAgility() / attacker.getAgility()))
			this.dodge(attacker, defender);		
		else if (Math.random() <= 0.25 * (defender.getStrength() + defender.getAgility()) / (attacker.getStrength() + attacker.getAgility()))
			this.block(defender);
		else
			this.successfulAttack(attacker, defender);
	    attacker.activityFinished();
	    //TODO hello
	}
	
	/**
	 * Let the given defender dodge the attack.
	 * 
	 * @param	attacker
	 * 			The unit who fails to bring damage.
	 * @param	defender
	 * 			The unit who dodges the attack.
	 * @effect	The given defender's location is set to a random location in the same or neigboring cube on the same z-level.
	 * @effect	The given defender gains 20 experience points.
	 */
	private void dodge(Unit attacker, Unit defender) {
		double oldX = defender.getLocation().getXLocation();
		double oldY = defender.getLocation().getYLocation();
		double oldZ = defender.getLocation().getZLocation();
		VLocation dodgeLoc;
		while (true) {
			double newX = oldX + 2*Math.random() - 1;
			double newY = oldY + 2*Math.random() - 1;
			dodgeLoc = new VLocation(newX, newY, oldZ, defender);
			if (dodgeLoc.isValidLocation())
				break;
		}
		defender.setLocation(dodgeLoc.getArray());
		defender.addXP(20);
	}
	
	/**
	 * Let the given defender block the attack.
	 * 
	 * @param	defender
	 * 			The unit who block the attack.
	 * @effect	The given defender gains 20 experience points.
	 */
	private void block(Unit defender) {
		defender.addXP(20);
	}
	
	/**
	 * Let the attacker successfully conduct its attack.
	 * 
	 * @param	attacker
	 * 			The unit who brings damage.
	 * @param	defender
	 * 			The unit who suffers damage.
	 * @effect	The given defender suffers damage, calculated by the given attacker's strength divided by 10.
	 * @effect	The given attacker gains 20 experience points.
	 */
	private void successfulAttack(Unit attacker, Unit defender) {
		double damage = attacker.getStrength() / 10;
		defender.dealDamage(damage);
		attacker.addXP(20);
	}
    
}