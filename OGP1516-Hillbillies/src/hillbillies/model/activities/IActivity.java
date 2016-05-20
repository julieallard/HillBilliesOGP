package hillbillies.model.activities;

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

public interface IActivity {

    /**
     * Return whether this activity has been dictated by a statement.
     */
    boolean isDictatedByStatement();

	/**
	 * Set the state of being dictated by a statement of this activity according to the given flag.
	 * 
	 * @param	flag
	 * 			The new state of being dictated by a statement for this activity.
	 */
    void setDictatedByStatement(boolean flag);
    
    /**
     * Update this activity according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     */
    void advanceActivityTime(double dt);
    
    /**
     * Check whether this activity can be interrupted by the given activity.
     * 
     * @param	activity
     *			The activity to check.
     */
    boolean canBeInterruptedBy(IActivity activity);

    /**
     * Return the ID of this activity.
     */
    int getId();

    /**
     * Return whether this activity is finished.
     */
    boolean isFinished();

    /**
     * Finish this activity.
     */
    void finishActivity();
    
}

