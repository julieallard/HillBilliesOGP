package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A class of Schedulers.
 * 
 * @version 0.9 alpha
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
		this.TaskList = new ArrayList<>(); 
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
		for (Task inList: TaskList)
			if (task.getPriority() >= inList.getPriority()) {
				int index = TaskList.indexOf(inList);
				TaskList.add(index, task);
				return;
			}
		TaskList.add(task);
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
		for (Task addToList: addList) {
			for (Task inList: TaskList)
				if (addToList.getPriority() >= inList.getPriority()) {
					int index = TaskList.indexOf(inList);
					TaskList.add(index, addToList);
					break;
				}
			TaskList.add(addToList);
		}
	}
	
	/**
	 * Remove the given task from this Scheduler.
	 * 
	 * @param  task
	 * 		   The task to remove.
	 * @post   The given task is removed from this Scheduler.
	 */
	public void removeTask(Task task) {
		TaskList.remove(task);
	}
	
	/**
	 * Remove the given list of tasks from this Scheduler.
	 * 
	 * @param  removeList
	 * 		   The list of tasks to remove.
	 * @post   The given list of tasks is removed from this Scheduler.
	 */
	public void removeTask(List<Task> removeList) {
		TaskList.removeAll(removeList);		
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
	 * @throws This Scheduler does not contain the given task to be replaced.
	 */
	public void replace(Task oldTask, Task newTask) throws IllegalArgumentException {
		int index = TaskList.indexOf(oldTask);
		if (index == -1)
			throw new IllegalArgumentException("This Scheduler doesn't contain the Task to be replaced.");
		if (oldTask.isBeingExecuted())
			oldTask.stop();
		this.TaskList.add(index, newTask);
		removeTask(oldTask);
	}
	
	public void containsTask(Task task) {
		TaskList.contains(task);
	}
	
	public void containsTask(List<Task> taskList) {
		TaskList.containsAll(taskList);
	}
	
	public Task getHPTask() {
		int index = 0;
		Task HPTask = TaskList.get(index);
		while (HPTask.isBeingExecuted()) {
			index += 1;
			HPTask = TaskList.get(index);
		}
		return HPTask;
	}
	
	public List<Task> getAllTasks() {
		return this.TaskList;
	}
	
	public List<Task> getTasksWithCondition(boolean condition) {
		//TODO: CONDITIONS
		//http://stackoverflow.com/questions/10600504/passing-a-condition-as-a-parameter-to-an-iterator
		List<Task> totList = this.TaskList;
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
	private List<Task> TaskList;
	
	
	/**
	 * Iterator collecting references to Tasks belonging to this Scheduler, in descending order of their priority.
	 */
	private ListIterator<Task> TaskIterator;
	
}
