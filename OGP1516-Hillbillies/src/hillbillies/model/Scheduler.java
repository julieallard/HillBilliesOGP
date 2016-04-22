package hillbillies.model;

import java.util.*;

/**
 * A class of Schedulers.
 * 
 * @version 2.0.5
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Scheduler {
	
	/**
	 * Initialize this new Scheduler with given faction.
     *
     * @param  faction
     *         The faction for this new Scheduler.
     * @post   The faction of this new Scheduler is equal to the given faction.
     */
	public Scheduler(Faction faction) {
		this.faction = faction;
		this.TaskQueue = new PriorityQueue<>(Collections.reverseOrder());
	}
	
	/**
	 * Variable registering the faction of this Scheduler.
	 */
	private final Faction faction;
	
	/**
	 * Return the faction of this Scheduler.
	 */
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Add the given task to this Scheduler.
	 * 
	 * @param  task
	 * 		   The task to add.
	 * @post   The given task is added to this Scheduler.
	 * @throws IllegalArgumentException
	 * 		   The given task cannot have this Scheduler as a Scheduler.
	 */
	public void addTask(Task task) {
		TaskQueue.add(task);
	}
	
	/**
	 * Add the given list of tasks to this Scheduler.
	 * 
	 * @param  addList
	 * 		   The list of tasks to add.
	 * @post   The given list of tasks is added to this Scheduler.
	 * @throws IllegalArgumentException
	 * 		   The given list of tasks cannot have this Scheduler as a Scheduler.
	 */
	public void addTask(List<Task> addList) {
		TaskQueue.addAll(addList);
	}
	
	/**
	 * Remove the given task from this Scheduler.
	 * 
	 * @param  task
	 * 		   The task to remove.
	 * @post   The given task is removed from this Scheduler.
	 */
	public void removeTask(Task task) {
		TaskQueue.remove(task);
	}
	
	/**
	 * Remove the given list of tasks from this Scheduler.
	 * 
	 * @param  removeList
	 * 		   The list of tasks to remove.
	 * @post   The given list of tasks is removed from this Scheduler.
	 */
	public void removeTask(List<Task> removeList) {
		TaskQueue.removeAll(removeList);
	}
	
	/**
	 * Replace the given task from this Scheduler by the given new task.
	 * 
	 * @param  oldTask
	 * 		   The task to remove.
	 * @param  newTask
	 * 		   The task to add.
	 * @post   The given new task is added to this Scheduler.
	 * @effect The given old task is removed from this Scheduler.
	 * @throws IllegalArgumentException This Scheduler does not contain the given task to be replaced.
	 */
	public void replace(Task oldTask, Task newTask) throws IllegalArgumentException {
		if (!TaskQueue.contains(oldTask))
			throw new IllegalArgumentException("This Scheduler doesn't contain the Task to be replaced.");
		if (oldTask.isBeingExecuted())
            newTask.startExecution(oldTask.getExecutor());
			oldTask.stopExecution();
		newTask.setpriority(oldTask.getPriority());
		TaskQueue.add(newTask);
		removeTask(oldTask);
    }
	
	public void containsTask(Task task) {
		TaskQueue.contains(task);
	}
	
	public void containsTask(List<Task> taskList) {
		TaskQueue.containsAll(taskList);
	}
	
	public Task getHPTask() {
		return TaskQueue.peek();
	}
	
	public List<Task> getAllTasks() {
		return new ArrayList<>(this.TaskQueue);
	}
	
	public List<Task> getTasksWithCondition(boolean condition) {
		//TODO: CONDITIONS
		//http://stackoverflow.com/questions/10600504/passing-a-condition-as-a-parameter-to-an-iterator
		List<Task> totList = new ArrayList<>(this.TaskQueue);
		List<Task> resultList = new ArrayList<>();
		for (Task task: totList)
			if (task.condition)
				resultList.add(task);
		return resultList;
	}
	
	public ListIterator<Task> getTasksInDescendingPriority() {
		List<Task> list = new ArrayList<>();
        Iterator<Task> TaskIterator = list.iterator();
		return this.TaskIterator;
		
	}
	
	public void markExecution(Task task, Unit executor) {
		task.setExecutor(executor);
	}
	
	public void resetMarking(Task task) {
		task.setExecutor(null);
	}
	
	/**
	 * List collecting references to Tasks belonging to this Scheduler.
	 * 
	 * @invar The list of Tasks is effective.
	 * @invar Each element in the list of Tasks references a Task that is an acceptable Task for this Scheduler.
	 */
	private PriorityQueue<Task> TaskQueue;
	
	
	/**
	 * Iterator collecting references to Tasks belonging to this Scheduler, in descending order of their priority.
	 */
	private ListIterator<Task> TaskIterator;
	
}
