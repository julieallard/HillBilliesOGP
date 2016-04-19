package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

public class Scheduler {
	
	public Scheduler(Faction faction) {
		this.faction = faction;
		this.TaskList = new ArrayList<>();
	}
	
	private final Faction faction;
	
	public Faction getFaction() {
		return this.faction;
	}
	
	public void addTask(Task task) {
		TaskList.add(task);
	}
	
	public void addTask(List<Task> addList) {
		TaskList.addAll(addList);
	}

	public void addTask(Task ... addList) {
		for (Task task :
				addList) {
			this.TaskList.add(task);
		}
	}
	
	public void removeTask(Task task) {
		TaskList.remove(task);
	}
	
	public void removeTask(List<Task> removeList) {
		TaskList.removeAll(removeList);		
	}
	
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
		
	}
	
	public void markAsScheduled(Task task) {
		
	}
	
	public void resetMarking(Task task) {
		
	}
	
	/**
	 * List collecting references to Tasks belonging to this Scheduler.
	 * 
	 * @invar The list of Tasks is effective.
	 * @invar Each element in the list of Tasks references a Task that is an acceptable Task for this Scheduler.
	 * @invar Each Task in the TaskList references this Scheduler as the Scheduler to which it is attached.
	 */
	private List<Task> TaskList;
	
	
	private ListIterator<Task> TaskIterator;
	
}
