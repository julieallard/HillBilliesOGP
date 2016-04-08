package hillbillies.model.activities;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;
import ogp.framework.util.Util;

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
public class Attack implements IActivity {

    public Attack(Unit attacker, Unit defender) throws IllegalTimeException {
        this.attacker = attacker;
        this.defender = defender;
        this.setTimeLeft(1);
    }
    
    private final Unit attacker;
    private final Unit defender;
    private double timeLeft;
    
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
     * Return the time left for this Attack.
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
        return 1;
    }

    /**
     * Check whether the given time left is a valid time left for
     * any Attack.
     *
     * @param  timeLeft
     *         The time left to check.
     * @return True if and only if the time left is above 0 and below 1.
     */
    public static boolean isValidTimeLeft(double timeLeft) {
    	return (Util.fuzzyGreaterThanOrEqualTo(timeLeft, 0) && Util.fuzzyLessThanOrEqualTo(timeLeft, 1));
    }

    /**
     * Set the time left for this Attack to the given time left.
     *
     * @param  timeLeft
     *         The new time left for this Attack.
     * @post   The time left of this new Attack is equal to
     *         the given time left.
     * @throws IllegalTimeException
     *         The given time left is not a valid time left for any
     *         Attack.
     */
    @Raw
    public void setTimeLeft(double timeLeft) throws IllegalTimeException {
      if (! isValidTimeLeft(timeLeft))
        throw new IllegalTimeException();
      this.timeLeft = timeLeft;
    }

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
		//blocking
		} else if (Math.random() <= 0.25 * (defender.getStrength() + defender.getAgility()) / (attacker.getStrength() + attacker.getAgility())) {
			return;
		//taking damage
		} else {
			double damage = attacker.getStrength() / 10;
			defender.dealDamage(damage);
		}
	    attacker.activityFinished();
	}
	
}