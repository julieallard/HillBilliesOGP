package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

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
    
    /**
     * Variable registering whether this state of not conducting any activity is finished.
     */
    private boolean isFinished = false;
    
    /* Methods */
    
    /**
     * Return whether this state of non conducting any activity has been dictated by a statement.
     */
    @Override
	@Basic
	@Raw
    public boolean isDictatedByStatement() {
        return this.dictatedByStatement;
    }
    
	/**
	 * Set the state of being dictated by a statement of this noActivity according to the given flag.
	 * 
	 * @param	flag
	 * 			The new state of being dictated by a statement for this NoActivity.
	 */
	@Override
	public void setDictatedByStatement(boolean flag) {
		this.dictatedByStatement = flag;
	}
    
    /**
     * Update this state of not conducting any activity according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     */
    @Override
    public void advanceActivityTime(double dt) {

    }

    /**
     * Check whether this state of not conducting any activity can be interrupted by the given activity.
     * 
     * @param	activity
     *			The activity to check.
     * @return	Always true.
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

    /**
     * Return whether this state of not conducting any activity is finished.
     */
    @Override
	@Basic
	@Raw
    public boolean isFinished() {
        return this.isFinished;
    }

    /**
     * Finish this state of not conducting any activity.
     */
    @Override
    public void finishActivity() {

    }

}
