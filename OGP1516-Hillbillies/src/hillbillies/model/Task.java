package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hillbillies.model.EsotERICScript.Statement;

/**
 * A class of Tasks.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Task {
	
	/**
	 * Initialize this new Task with given name, given priority and given scheduler.
     *
     * @param  name
     *         The name for this new Task.
     * @param  priority
     * 		   The priority for this new Task.
     * @param  scheduler
     * 		   The scheduler for this new Task.
     * @effect The given scheduler is added to this new Task.
     * @post   The name of this new Task is equal to the given name.
     * @post   The priority of this new Task is equal to the given priority.
     */
	public Task(String name, int priority, Scheduler scheduler) {
		this.name = name;
		this.priority = priority;
		this.ActivityList = new ArrayList<>();
		this.SchedulerSet = new HashSet<>();
		addScheduler(scheduler);
	}
	
	/**
	 * Initialize this new Task with given name, given priority and given scheduler.
     *
     * @param  name
     *         The name for this new Task.
     * @param  priority
     * 		   The priority for this new Task.
     * @param  schedulerList
     * 		   The list of schedulers for this new Task.
     * @effect The given list of schedulers is added to this new Task.
     * @post   The name of this new Task is equal to the given name.
	 * @effect The priority of this new Task is set to the given priority.
	 *       | this.setPriority(priority)
     */
	public Task(String name, int priority, List<Scheduler> schedulerList) {
		this.name = name;
		this.priority = priority;
		this.ActivityList = new ArrayList<>();
		this.SchedulerSet = new HashSet<>();
		addScheduler(schedulerList);
	}
	
	/**
	 * Variable registering the name of this Task.
	 */
	private String name;
	
	/**
	 * Variable registering the priority of this Task.
	 */
	private int priority;
	
	/**
	 * Variable registering whether the of this Task.
	 */
	private boolean isBeingExecuted = false;
	
	/**
	 * Variable registering the executing unit of this Task.
	 */
	private Unit executor;
	
	/**
	 * List collecting references to activities belonging to this Task.
	 * 
	 * @invar The list of activities is effective.
	 * @invar Each element in the list of activities references an activity that is an acceptable activity for this Task.
	 * @invar Each activity in the ActivityList references this Task as the Task to which it is attached.
	 */
	private List<Statement> ActivityList;
	
	/**
	 * List collecting references to schedulers belonging to this Task.
	 * 
	 * @invar The list of schedulers is effective.
	 * @invar Each element in the list of schedulers references a scheduler that is an acceptable scheduler for this Task.
	 */
	private Set<Scheduler> SchedulerSet;
	
	/**
	 * Return the name of this Task.
	 */
	@Basic
	@Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Return the priority of this Task.
	 */
	@Basic
	@Raw
	public int getPriority() {
		return this.priority;
	}

	/**
	 * Check whether the given priority is a valid priority for any Task.
	 *  
	 * @param  priority
	 *         The priority to check.
	 * @return Always true.
	 *       | result == true
	*/
	public static boolean isValidPriority(int priority) {
		return true;
	}

	/**
	 * Set the priority of this Task to the given priority.
	 * 
	 * @param  priority
	 *         The new priority for this Task.
	 * @post   The priority of this new Task is equal to the given priority.
	 *       | new.getPriority() == priority
	 * @throws ExceptionName_Java
	 *         The given priority is not a valid priority for any Task.
	 *       | ! isValidPriority(getPriority())
	 */
	@Raw
	public void setpriority(int priority) throws IllegalArgumentException {
		if (! isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
	}
	
	public boolean isBeingExecuted() {
		return this.isBeingExecuted;
	}
	
	public void addScheduler(Scheduler scheduler) {
		this.SchedulerSet.add(scheduler);
	}
	
	public void addScheduler(List<Scheduler> schedulerList) {
		this.SchedulerList.addAll(schedulerList);
	}
	
    /**
     * Set the state of execution of this Task according to the given flag.
     *
     * @param  flag
     * 		   The execution state to be registered.
     * @post   The new execution state of this Task is equal to the given flag.
     * 		 | this.isBeingExecuted == flag
     */
    private void setExecution(boolean flag) {
        this.isBeingExecuted = flag;
    }
	
	public void startExecution(Unit unit) {
		this.setExecutor(unit);
		setExecution(true);
	}
	
	public void stopExecution() {
		this.setExecutor(null);
		setExecution(false);
	}

	public Unit getExecutor() {
		return executor;
	}

	public void setExecutor(Unit executor) {
		this.executor = executor;
	}
	
	
	
	

}
