package hillbillies.model.activities;

import hillbillies.model.EsotERICScript.Statements.Statement;

/**
 * A class of the states of not conducting any activity.
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
public class NoActivity implements IActivity {

	/**
	 * No documentation required.
	 */
    @Override
    public void advanceActivityTime(double dt) {

    }

    /**
     * Return the time left until finishing the state of not conducting any activity.
     */
    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        return 0;
    }

    /**
     * Check whether the state of not conducting any activity can be interrupted by the given activity.
     * 
     * @param  activity
     * 		   The activity to check.
     */
    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return true;
    }

    /**
     * Return the ID of this state of not conducting any activity.
     */
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void finishActivity() {
    }


    @Override
    public boolean isDictatedByStatement() {
        return false;
    }

    @Override
    public Statement getControllingStatement() {
        throw new IllegalArgumentException("NoActivity can't be directly dictated by statement");

    }
}
