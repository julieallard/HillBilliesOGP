package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;
import hillbillies.model.exceptions.IllegalTimeException;
import ogp.framework.util.Util;

/**
 * A class of attacks involving an attacker and a defender.
 * 
 * @invar	The time left until each attack is finished must be a valid time left for any attack.
 *		  | isValidTimeLeft(getTimeLeft())
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
     * @param	dictatedByStatement
     * 			The dictated state of the statement for this attack.
     * @post	The controlling statement of this new attack is equal to the given controlling statement.
     * @post	The dictated state of the statement of this new attack is equal to the given dictated state of the statement.
     */
    public Attack(Unit attacker, Unit defender, boolean dictatedByStatement) {
        this(attacker, defender);
        this.dictatedByStatement = dictatedByStatement;
    }

    /* Variables */
    
    /**
     * Variable registering whether this attack has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /**
     * Variable registering whether this attack is finished.
     */
    private boolean isFinished = false;
    
    /**
     * Variable registering the attacker of this attack.
     */
    private final Unit attacker;
    
    /**
     * Variable registering the defender of this attack.
     */
    private final Unit defender;
    
    /**
     * Variable registering the time left until this attack is finished.
     */
    private double timeLeft;

    /* Methods */
    
    /**
     * Return whether this attack has been dictated by a statement.
     */
    @Override
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this attack according to the given flag.
	 * 
	 * @param	flag
	 * 			The new state of being dictated by a statement for this attack.
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
     * @effect	If the given time is equal or higher than the time left until finishing this attack, conduct this attack.
     * @effect	If the given time is lower than the time left until finishing this attack, the time left for this attack is reduced by the given amount of time.
     */
    @Override
    public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt, this.getTimeLeft()))
            this.conductAttack();
        else {
            double time = getTimeLeft() - dt;
            this.setTimeLeft(time);
        }
    }
    
    /**
     * Return the time left until this attack is finished.
     */
    @Basic
    @Raw
    public double getTimeLeft() {
    	return this.timeLeft;
    }
    
    /**
     * Check whether the given time left is a valid time left for any attack.
     *
     * @param	timeLeft
     *			The time left to check.
     * @return	True if and only if the given time left is positive and equal or lower than 1.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
    	return (Util.fuzzyGreaterThanOrEqualTo(timeLeft, 0) && Util.fuzzyLessThanOrEqualTo(timeLeft, 1));
    }

    /**
     * Set the time left for this attack to the given time left.
     *
     * @param	timeLeft
     *			The new time left for this attack.
     * @post	The time left of this new attack is equal to the given time left.
     * @throws	IllegalTimeException
     *			The given time left is not a valid time left for any attack.
     */
    @Raw
    private void setTimeLeft(double timeLeft) throws IllegalTimeException {
      if (! isValidTimeLeft(timeLeft))
        throw new IllegalTimeException("Invalid time left");
      this.timeLeft = timeLeft;
    }
    
    /**
     * Check whether this attack can be interrupted by the given activity.
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
     * Return the ID of this attack.
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
        return this.isFinished;
    }

    /**
     * Finish this attack.
     */
    @Override
    public void finishActivity() {

    }

    /**
     * Return the attacker of this attack.
     */
    private Unit getAttacker() {
    	return this.attacker;
    }
    
    /**
     * Return the defender of this attack.
     */
    private Unit getDefender() {
    	return this.defender;
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
		this.getAttacker().setOrientation((float) Math.atan2(this.getDefender().getLocation().getYLocation() - this.getAttacker().getLocation().getYLocation(), this.getDefender().getLocation().getXLocation() - this.getAttacker().getLocation().getXLocation()));
		this.getDefender().setOrientation((float) Math.atan2(this.getAttacker().getLocation().getYLocation() - this.getDefender().getLocation().getYLocation(), this.getAttacker().getLocation().getXLocation() - this.getDefender().getLocation().getXLocation()));
		if (Math.random() <= 0.25 * (this.getDefender().getAgility() / this.getAttacker().getAgility()))
			this.dodgeAttack();		
		else if (Math.random() <= 0.25 * (this.getDefender().getStrength() + this.getDefender().getAgility()) / (this.getAttacker().getStrength() + this.getAttacker().getAgility()))
			this.blockAttack();
		else
			this.successfulAttack();
	    this.getAttacker().activityFinished();
	    //TODO hello
	}
	
	/**
	 * Let the given defender dodge the attack.
	 * 
	 * @effect	The defender's location is set to a random location in the same or neigboring cube on the same z-level.
	 * @effect	The defender gains 20 experience points.
	 */
	private void dodgeAttack() {
		double oldX = this.getDefender().getLocation().getXLocation();
		double oldY = this.getDefender().getLocation().getYLocation();
		double oldZ = this.getDefender().getLocation().getZLocation();
		VLocation dodgeLoc;
		while (true) {
			double newX = oldX + 2*Math.random() - 1;
			double newY = oldY + 2*Math.random() - 1;
			dodgeLoc = new VLocation(newX, newY, oldZ, this.getDefender());
			if (dodgeLoc.isValidLocation())
				break;
		}
		this.getDefender().setLocation(dodgeLoc.getArray());
		this.getDefender().addXP(20);
	}
	
	/**
	 * Let the given defender block the attack.
	 * 
	 * @effect	The defender gains 20 experience points.
	 */
	private void blockAttack() {
		this.getDefender().addXP(20);
	}
	
	/**
	 * Let the attacker successfully conduct its attack.
	 * 
	 * @effect	The defender suffers damage, calculated by the given attacker's strength divided by 10.
	 * @effect	The attacker gains 20 experience points.
	 */
	private void successfulAttack() {
		double damage = this.getAttacker().getStrength() / 10;
		this.getDefender().dealDamage(damage);
		this.getAttacker().addXP(20);
	}
    
}