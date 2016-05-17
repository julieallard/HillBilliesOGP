package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.EsotERICScript.Statements.Statement;

import java.util.*;

/**
 * A class of Tasks.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Task implements Comparable {

	/**
	 * Initialize this new Task with given name, given priority and given scheduler.
     *
     * @param  name
     *         The name for this new Task.
     * @param  priority
     * 		   The priority for this new Task.
     * @param  scheduler
     * 		   The scheduler for this new Task.
     * @effect The priority of this new Task is set to the given priority.
     *       | this.setPriority(priority)
     * @effect The given scheduler is added to this new Task.
     * 		 | this.addScheduler(scheduler)
     * @post   The name of this new Task is equal to the given name.
     * 		 | new.getName == name
     */
	public Task(String name, int priority, Scheduler scheduler) {
		this.name = name;
		this.setPriority(priority);
		this.SchedulerSet = new HashSet<>();
		this.addScheduler(scheduler);
		this.positionGlabalMap = new HashMap<>();
        this.booleanGlobalMap = new HashMap<>();
		this.unitGlobalMap = new HashMap<>();
	}

	public boolean isLegaltask(){
		return ProgramExecutor.checkBreakLegality(this);
	}

    public Map<String, int[]> positionGlabalMap;
    public Map<String, Boolean> booleanGlobalMap;
    public Map<String, Unit> unitGlobalMap;
	
	/**
	 * Initialize this new Task with given name, given priority and given scheduler.
     *
     * @param  name
     *         The name for this new Task.
     * @param  priority
     * 		   The priority for this new Task.
     * @param  schedulerList
     * 		   The list of schedulers for this new Task.
	 * @effect The priority of this new Task is set to the given priority.
	 *       | this.setPriority(priority)
     * @effect The given list of schedulers is added to this new Task.
     * 		 | this.addScheduler(schedulerList)
     * @post   The name of this new Task is equal to the given name.
     * 		 | new.getName == name
     */
	public Task(String name, int priority, List<Scheduler> schedulerList) {
		this.name = name;
		this.setPriority(priority);
		this.SchedulerSet = new HashSet<>();
		this.addScheduler(schedulerList);
	}
	public Statement getRootStatement() {
		return rootStatement;
	}
	public void setRootStatement(Statement rootStatement) {
		//Todo must not be a break statement
		this.rootStatement = rootStatement;
	}

	private Statement rootStatement;
	private boolean isBeingExecuted;
	private boolean isExecuted;
		
	public boolean isBeingExecuted() {
		return isBeingExecuted;
	}

	public void setBeingExecuted(boolean beingExecuted) {
		isBeingExecuted = beingExecuted;
	}

	public boolean isExecuted() {
		return isExecuted;
	}

	public void setExecuted(boolean executed) {
		isExecuted = executed;
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
	 * Variable registering the unit executing this Task.
	 */
	private Unit executor;

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
	 * @throws IllegalArgumentException
	 *         The given priority is not a valid priority for any Task.
	 *       | ! isValidPriority(getPriority())
	 */
	@Raw
	public void setPriority(int priority) throws IllegalArgumentException {
		if (! isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
	}
	
	/**
	 * Add the given scheduler to this Task.
	 * 
	 * @param  scheduler
	 * 		   The scheduler to add.
	 * @post   The given scheduler is added to this Task.
	 */
	public void addScheduler(Scheduler scheduler) {
		this.SchedulerSet.add(scheduler);
	}
	
	/**
	 * Add the given scheduler to this Task.
	 * 
	 * @param  schedulerList
	 * 		   The list of schedulers to add.
	 * @post   The given list of schedulers is added to this Task.
	 */
	public void addScheduler(List<Scheduler> schedulerList) {
		this.SchedulerSet.addAll(schedulerList);
	}
	
	public void startExecution(Unit unit) {
		this.setExecutor(unit);
	}
	
	public void stopExecution() {
		this.setExecutor(null);
	}
	
	public Unit getExecutor() {
		return executor;
	}
	
	public void setExecutor(Unit executor) {
		this.executor = executor;
	}
	
	public World world;

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 * <p>
	 * <p>The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
	 * all <tt>z</tt>.
	 * <p>
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 * <p>
	 * <p>In the foregoing description, the notation
	 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
	 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
	 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
	 * <i>expression</i> is negative, zero or positive.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */




	@Override
	public int compareTo(Object o) {
		if (o == null || ! (o instanceof Task))
			throw new IllegalArgumentException("An invalid task was compared");
		if (this.getPriority() == ((Task) o).getPriority())
			return 0;
		return this.getPriority() > ((Task) o).getPriority() ? 1: -1;
	}

	public void advanceTime(double dt) {

    }
    public void forceExcecuteComplete(){
        while (!this.isExecuted()){
            this.advanceTime(0.2);
            try{Thread.sleep(500);}
            catch (InterruptedException exception){
                System.out.println("Somehow The application was multithreading "+"\n"+
                        "this really really really shouldn't happen");
                throw new RuntimeException(exception);
            }
        }
    }



}