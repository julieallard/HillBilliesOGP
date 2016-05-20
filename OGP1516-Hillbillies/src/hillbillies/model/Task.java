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
	 * Variable registering the world of this Task.
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
	 * @throws	IllegalArgumentException
	 * 			
	 */
	public void setRootStatement(Statement rootStatement) {
		if (rootStatement.getPartStatement() instanceof Statement.BreakPartStatement)
			throw new IllegalArgumentException("A break statement can't be a root statement");
		this.rootStatement = rootStatement;
	}
		
	public boolean isBeingExecuted() {
		return this.getStatus().equals(ExecutionStatus.BEINGEXECUTED);
	}

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
	 * @param  priority
	 *         The priority to check.
	 * @return Always true.
	 *       | result == true
	*/
	private static boolean isValidPriority(int priority) {
		return true;
	}

	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param  priority
	 *         The new priority for this task.
	 * @post   The priority of this new task is equal to the given priority.
	 *       | new.getPriority() == priority
	 * @throws IllegalArgumentException
	 *         The given priority is not a valid priority for any task.
	 *       | ! isValidPriority(getPriority())
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
	 * @param  scheduler
	 * 		   The scheduler to add.
	 * @post   The given scheduler is added to this task.
	 */
	public void addScheduler(Scheduler scheduler) {
		this.SchedulerSet.add(scheduler);
	}
	
	/**
	 * Add the given scheduler to this task.
	 * 
	 * @param  schedulerList
	 * 		   The list of schedulers to add.
	 * @post   The given list of schedulers is added to this task.
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

	public void advanceTime(double dt) throws SyntaxError {
        ProgramExecutor executor =new ProgramExecutor(getExecutor(),this);
        executor.setDeltat(dt);
        executor.execute();
    }
    public void forceExcecuteComplete(){
        while (!this.isExecuted()){
            try {
                this.advanceTime(0.2);
            } catch (SyntaxError syntaxError) {
                syntaxError.printStackTrace();
                throw new RuntimeException(syntaxError);
            }
            try{Thread.sleep(500);}
            catch (InterruptedException exception){
                System.out.println("Somehow The application was multithreading "+"\n"+
                        "this really really really shouldn't happen");
                throw new RuntimeException(exception);
            }
        }
    }


    public static Collection<Task> createTask(String name,int priority,Statement activity,List<int[]> selected){

        List<Task> taskList=new ArrayList<>();

        for (int[] loc: selected) {
            Task curr=new Task(name,priority);
            curr.selected=loc;
            taskList.add(curr);
        }
        return taskList;
    }

    public int[] getSelected() {
        return selected;
    }
    
    public void setSelected(int[] selected) {
    	this.selected = selected;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

}
