package hillbillies.model.activities;

import hillbillies.model.EsotERICScript.Statements.Statement;

/**
 * A class of the states of not conducting any activity.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class NoActivity implements IActivity {

	/* Variables */
	
    /**
     * Variable registering whether this state of non conducting any activity has been dictated by a statement.
     */
    private boolean dictatedByStatement = false;
    
    /* Methods */
    
    /**
     * Return whether this state of non conducting any activity has been dictated by a statement.
     */
    @Override
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this noActivity.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}
    
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
    
    @Override
    public void finishActivity() {
    }

}
