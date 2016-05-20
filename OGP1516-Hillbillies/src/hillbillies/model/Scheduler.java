package hillbillies.model;

import hillbillies.model.EsotERICScript.Statements.ExecutionStatus;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A class of schedulers involving a faction.
 * 
 * @version 2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Scheduler {
	
	/**
	 * Initialize this new scheduler with given faction.
     *
     * @param	faction
     *			The faction for this new scheduler.
     * @post	The faction of this new scheduler is equal to the given faction.
     * 		  |	this.getFaction == faction
     */
	public Scheduler(Faction faction) {
		this.faction = faction;
	}
	
	/* Variables */
	
	/**
	 * Variable registering the faction of this scheduler.
	 */
	private final Faction faction;
	
	/**
	 * List collecting references to tasks belonging to this scheduler.
	 */
	private PriorityQueue<Task> TaskQueue = new PriorityQueue<>(Collections.reverseOrder());
	
	/* Methods*/
	
	/**
	 * Return the faction of this scheduler.
	 */
	@Basic
	@Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Add the given task to this scheduler.
	 * 
	 * @param	task
	 *			The task to add.
	 * @post	The given task is added to this scheduler's queue of tasks.
	 * 		  |	this.TaskQueue.add(Task)
	 */
	public void schedule(Task task) {
		this.TaskQueue.add(task);
	}
	
	/**
	 * Add the tasks supplied by the given list to this scheduler.
	 * 
	 * @param	taskList
	 *			The list supplying tasks to add.
	 * @post	The tasks supplied by the given list are added to this scheduler's queue of tasks.
	 * 		  |	this.TaskQueue.addAll(addList)
	 */
	public void schedule(List<Task> taskList) {
		this.TaskQueue.addAll(taskList);
	}
	
	/**
	 * Remove the given task from this scheduler.
	 * 
	 * @param	task
	 *			The task to remove.
	 * @post	The given task is removed from this scheduler's queue of tasks.
	 * 		  |	this.TaskQueue.remove(task)
	 */
	public void removeTask(Task task) {
		this.TaskQueue.remove(task);
	}
	
	/**
	 * Remove the given list of tasks from this scheduler.
	 * 
	 * @param	taskList
	 *			The list supplying tasks to remove.
	 * @post	The tasks supplied by the given list are removed from this scheduler's queue of tasks.
	 *		  |	this.TaskQueue.removeAll(removeList)
	 */
	public void removeTask(List<Task> taskList) {
		this.TaskQueue.removeAll(taskList);
	}
	
	/**
	 * Replace the given task from this scheduler by the given replacement task.
	 * 
	 * @param	original
	 *			The task to remove.
	 * @param	replacement
	 *			The task to add.
	 * @effect	If the given original task is being executed, it is stopped and the given replacement task is started by the executor of the original task.
	 * 		  |	if (original.isBeingExecuted())
	 * 		  |		then original.stopExecution()
	 * 		  |			 replacement.startExecution(original.getExecutor())
	 * @effect	The priority of the given replacement task is set to the original task's priority.
	 * 		  |	replacement.setPriority(original.getPriority())
	 * @effect	The given replacement task is added to this scheduler.
	 * 		  |	this.schedule(replacement)
	 * @effect	The given original task is removed from this scheduler.
	 * 		  |	this.removeTask(original)
	 * @throws	IllegalArgumentException
	 *			This scheduler does not contain the given task to be replaced.
	 *		  |	! TaskQueue.contains(original)
	 */
	public void replace(Task original, Task replacement) throws IllegalArgumentException {
		if (! this.TaskQueue.contains(original))
			throw new IllegalArgumentException("This scheduler doesn't contain the given task to be replaced.");
		if (original.isBeingExecuted()) {
			Unit executor = original.getExecutor();
			original.stopExecution();
			replacement.startExecution(executor);
		}
		replacement.setPriority(original.getPriority());
		this.schedule(replacement);
		this.removeTask(original);
    }
	
	/**
	 * Check whether the given task is part of this scheduler.
	 * 
	 * @param	task
	 * 			The task to check.
	 * @return	True if and only if this scheduler's queue of tasks contains the given task.
	 * 		  |	result == TaskQueue.contains(task)
	 */
	public boolean areTasksPartOf(Task task) {
		return this.TaskQueue.contains(task);
	}
	
	/**
	 * Check whether the tasks supplied by the given list are part of this scheduler.
	 * 
	 * @param	taskList
	 * 			The list supplying tasks to check.
	 * @return	True if and only if this scheduler's queue of tasks contain the tasks supplied by the given list.
	 * 		  |	result == TaskQueue.containsAll(taskList)
	 */
	public boolean areTasksPartOf(List<Task> taskList) {
		return this.TaskQueue.containsAll(taskList);
	}
	
	/**
	 * Return this highest priority task of this scheduler.
	 * 
	 * @return	The head of this scheduler's queue of tasks.
	 * 		  |	result == this.TaskQueue.peek()
	 */
	public Task getHPTask() {
		return this.TaskQueue.peek();
	}
	
	/**
	 * Return all the tasks of this scheduler.
	 * 
	 * @return	An arraylist containing all the tasks of this scheduler.
	 * 		  |	result == new ArrayList<>(this.TaskQueue)
	 */
	public List<Task> getAllTasks() {
		return new ArrayList<>(this.TaskQueue);
	}
	
	/**
	 * Return all the tasks of this scheduler that satisfy the given condition.
	 * 
	 * @param	condition
	 * 			The condition to satisfy.
	 * @return	An arraylist containing all the tasks of this scheduler that satisfy the given condition.
	 * 		  |	result == (new ArrayList<>(this.TaskQueue)).stream().filter(condition).collect(Collectors.toList())
	 */
	public List<Task> getTasksWithCondition(Predicate<Task> condition) {
		List<Task> totList = new ArrayList<>(this.TaskQueue);
		return totList.stream().filter(condition).collect(Collectors.toList());
	}
	
	/**
	 * Return all the tasks of this scheduler in ascending order of their priority.
	 * 
	 * @return	An iterator containing all the tasks of this scheduler in ascending order of their priority.
	 * 		  |	this.TaskQueue.iterator()
	 */
	public Iterator<Task> getTasksInDescendingPriority() {
        return this.TaskQueue.iterator();
	}

	/**
	 * Return the next task to be executed.
	 * 
	 * @return	The head of this scheduler's queue of tasks.
	 * 		  |	this.TaskQueue.poll()
	 * @post	The head of this scheduler's queue of tasks is removed from the queue.
	 * 		  |	this.TaskQueue.poll()
	 * @throws	RuntimeException
	 * 			The head of the task queue is not yet ready to be executed.
	 * 		  |	task.getStatus() != ExecutionStatus.NOTYETEXECUTED
	 */
	public Task offerTask() {
		Task task = this.TaskQueue.poll();
		if (task.getStatus() != ExecutionStatus.NOTYETEXECUTED)
			throw new RuntimeException("The head of the task queue is not yet ready to be executed.");
		return task;
	}
}