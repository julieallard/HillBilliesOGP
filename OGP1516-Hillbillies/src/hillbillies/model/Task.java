package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.EsotERICScript.Statements.ExecutionStatus;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.model.exceptions.SyntaxError;

import java.util.*;

/**
 * A class of tasks involving a name and a priority.
 * 
 * @version 2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Task implements Comparable {

	/**
	 * Initialize this new task with given name, given priority and given scheduler.
     *
     * @param  name
     *         The name for this new task.
     * @param  priority
     * 		   The priority for this new task.
     * @effect The priority of this new task is set to the given priority.
     *       | this.setPriority(priority)
     * @effect The given scheduler is added to this new task.
     * 		 | this.addScheduler(scheduler)
     * @post   The name of this new task is equal to the given name.
     * 		 | new.getName == name
     */
	public Task(String name, int priority) {
		this.name = name;
		this.setPriority(priority);
		this.SchedulerSet = new HashSet<>();
	}

	/* Variables */
	
	/**
	 * Variable registering the name of this task.
	 */
	private String name;
	
	/**
	 * Variable registering the priority of this task.
	 */
	private int priority;
	
	/**
	 * Variable registering the unit executing this task.
	 */
	private Unit executor;

	/**
	 * Set collecting references to schedulers belonging to this task.
	 */
	private Set<Scheduler> SchedulerSet;
	
	/**
	 * Variable registering the root statement of this task.
	 */
	private Statement rootStatement;
	
	/**
	 * Variable registering the world of this task.
	 */
	public World world;
	
	/**
	 * Variable registering a cube location selected by the user when making this task.
	 */
    private int[] selected;
    
    /**
     * Variable registering the execution status of this task.
     */
    private ExecutionStatus status;

    /**
     * Hashmap registering position values with string keys used in the assign and read statements.
     */
    public Map<String, int[]> positionGlobalMap = new HashMap<>();
    
    /**
     * Hashmap registering boolean values with string keys used in the assign and read statements.
     */
    public Map<String, Boolean> booleanGlobalMap = new HashMap<>();
    
    /**
     * Hashmap registering unit values with string keys used in the assign and read statements.
     */
    public Map<String, Unit> unitGlobalMap = new HashMap<>();

    /* Methods */
    
    /**
     * Check whether this task can accept a break statement.
     * 
     * @return	True if and only if this task satisfies break legality.
     * 		  |	result == ProgramExecutor.checkBreakLegality(this)
     */
	public boolean isLegaltask() {
		return ProgramExecutor.checkBreakLegality(this);
	}
	
	/**
	 * Return the set of schedulers of this task.
	 */
	public Set<Scheduler> getSchedulerSet() {
		return this.SchedulerSet;
	}

	/**
	 * Return the root statement of this task.
	 */
	@Basic
	@Raw
	public Statement getRootStatement() {
		return this.rootStatement;
	}

	/**
	 * Set the root statement of this task to the given root statement.
	 * 
	 * @param	rootStatement
	 * 			The new root statement for this task.
	 * @post	The root statement of this task is equal to the given root statement.
	 * 		  |	new.getRootStatement() = rootStatement
	 * @throws	IllegalArgumentException
	 * 			A break statement can't be a root statement.
	 * 		  |	rootStatement.getPartStatement() instanceof Statement.BreakPartStatement
	 */
	public void setRootStatement(Statement rootStatement) {
		if (rootStatement.getPartStatement() instanceof Statement.BreakPartStatement)
			throw new IllegalArgumentException("A break statement can't be a root statement.");
		this.rootStatement = rootStatement;
	}
		
	/**
	 * Return whether this task is being executed.
	 * 
	 * @return	True if and only if the execution status of this task is equal to BEINGEXECUTED.
	 * 		  |	result == this.getStatus().equals(ExecutionStatus.BEINGEXECUTED)
	 */
	public boolean isBeingExecuted() {
		return this.getStatus().equals(ExecutionStatus.BEINGEXECUTED);
	}
	
	/**
	 * Return whether this task is finished.
	 * 
	 * @return	True if and only if the execution status of this task is equal to FINISHED.
	 * 		  |	result == this.getStatus().equals(ExecutionStatus.FINISHED)
	 */
	public boolean isExecuted() {
		return this.getStatus().equals(ExecutionStatus.FINISHED);
	}

	/**
	 * Return the name of this task.
	 */
	@Basic
	@Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Return the priority of this task.
	 */
	@Basic
	@Raw
	public int getPriority() {
		return this.priority;
	}

	/**
	 * Check whether the given priority is a valid priority for any task.
	 *  
	 * @param	priority
	 *			The priority to check.
	 * @return	Always true.
	 *		  |	result == true
	 */
	private static boolean isValidPriority(int priority) {
		return true;
	}

	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param	priority
	 *			The new priority for this task.
	 * @post	The priority of this new task is equal to the given priority.
	 *		  |	new.getPriority() == priority
	 * @throws	IllegalArgumentException
	 *			The given priority is not a valid priority for any task.
	 *		  |	! isValidPriority(getPriority())
	 */
	@Raw
	public void setPriority(int priority) throws IllegalArgumentException {
		if (! isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
	}
	
	/**
	 * Add the given scheduler to this task.
	 * 
	 * @param	scheduler
	 *			The scheduler to add.
	 * @post	This task's set of schedulers contains the given scheduler.
	 * 		  |	new.SchedulerSet.contains(scheduler)
	 */
	public void addScheduler(Scheduler scheduler) {
		this.SchedulerSet.add(scheduler);
	}
	
	/**
	 * Add the schedulers supplied by the given list to this task.
	 * 
	 * @param	schedulerList
	 *			The list supplying the schedulers to add.
	 * @post	This task's set of schedulers contains the schedulers supplied by the given list.
	 * 		  |	new.SchedulerSet.containsAll(schedulerList)
	 */
	public void addScheduler(List<Scheduler> schedulerList) {
        this.SchedulerSet.addAll(schedulerList);
	}

	/**
	 * Start the execution of this task.
	 * 
	 * @param	unit
	 * 			The unit to let this task execute.
	 * @post	The executor of this task is equal to the given unit.
	 * 		  |	new.getExecutor == unit
	 */
	public void startExecution(Unit unit) {
		this.setExecutor(unit);
	}

	/**
	 * Stop the executrion of this task.
	 * 
	 * @post	The executor of this task is equal to null.
	 * 		  |	new.getExecutor == null
	 */
	public void stopExecution() {
		this.setExecutor(null);
	}

	/**
	 * Return the unit executing this task.
	 */
	@Basic
	@Raw
	public Unit getExecutor() {
		return this.executor;
	}

	/**
	 * Set the executor of this task to the given executor.
	 * 
	 * @param	executor
	 * 			The new executor for this task.
	 * @post	The executor of this task is equal to the given executor.
	 */
	public void setExecutor(Unit executor) {
		this.executor = executor;
	}

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
	 * @param	object
	 * 			The object to compare.
	 * @return	If this task's priority is equal to the given project's priority, 0.
	 * 			If this task's priority is higher than the given object's priority, 1.
	 * 			If this task's priority is lower than the given object's priority, -1.
	 * @throws	NullPointerException
	 * 			The given object is null.
	 * @throws	ClassCastException
	 * 			The given object's type prevents it from being compared to this object.
	 */
	@Override
	public int compareTo(Object object) {
		if (object == null || !(object instanceof Task))
			throw new IllegalArgumentException("Invalid task");
		if (this.getPriority() == ((Task) object).getPriority())
			return 0;
		return (this.getPriority() > ((Task) object).getPriority() ? 1: -1);
	}

    /**
     * Update this task according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     * @effect	The time of ths program executor is set to the given time.
     * @effect	The program executor is executed.
     */
	public void advanceTime(double dt) throws SyntaxError {
        ProgramExecutor executor = new ProgramExecutor(getExecutor(), this);
        executor.setDeltat(dt);
        executor.execute();
    }

    /**
     * Return the selected cube position of this task.
     */
    public int[] getSelected() {
        return this.selected;
    }
    
    /**
     * Set the selected cube position of this task to the given selected cube position.
     * 
     * @param	selected
     * 			The new selected cube position for this task.
     * @post	The selected cube position for this new task is equal to the given selected cube position.
     * 		  |	new.getSelected() == selected
     */
    public void setSelected(int[] selected) {
    	this.selected = selected;
    }

    /**
     * Return the execution status of this task.
     */
    public ExecutionStatus getStatus() {
        return this.status;
    }

    /**
     * Set the execution status of this task to the given execution status.
     * 
     * @param	status
     * 			The new execution status for this task.
     * @post	The execution status of this new task is equal to the given execution status.
     * 		  |	new.getStatus() == status
     */
    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

}