package net.loomchild.maligna.progress;

/**
 * <p>
 * Represents progress meter. Processes inform it about task progress, and 
 * it notifies its observers via {@link ProgressManager}.
 * </p> 
 * 
 * <p>
 * After creating ProgressMeter it needs to be registered via
 * {@link ProgressManager#registerProgressMeter(ProgressMeter)} method. 
 * After task completes it must be unregistered via 
 * {@link ProgressManager#unregisterProgressMeter(ProgressMeter)} method. 
 * </p>
 * 
 * <p>
 * To improve performance observer are not notified of every completed task, 
 * maximum number of notifications is controlled by steps parameter. 
 * </p>
 * 
 * <p>
 * TODO: maybe add parent progress meter to have a hierarchy.
 * </p>
 * 
 * @author loomchild
 */
public class ProgressMeter {
	
	/**
	 * Default maximum number of notifications sent to the observer. 
	 */
	private static final int DEFAULT_STEPS = 1000;
	
	private String name;
	
	private int totalTasks;
	
	private int completedTasks;
	
	/**
	 * Number of steps communicated to the observers.
	 */
	private int reportedSteps;
	
	/**
	 * Number of tasks per single step.
	 */
	private float tasksPerStep;
	

	/**
	 * Creates Progress meter. 
	 * 
	 * @see #ProgressMeter(String, int, int)
	 * 
	 * @param name progress meter name
	 * @param totalTasks total number of tasks in this process
	 */
	public ProgressMeter(String name, int totalTasks) {
		this(name, totalTasks, DEFAULT_STEPS);
	}

	/**
	 * Creates Progress meter. To make it fully functional it must be
	 * registered via 
	 * {@link ProgressManager#registerProgressMeter(ProgressMeter)} method. 
	 * 
	 * @param name progress meter name
	 * @param totalTasks total number of tasks in this process
	 * @param steps maximum number of notifications to the observers this
	 * 		progress meter will generate, regardless of total tasks
	 */
	public ProgressMeter(String name, int totalTasks, int steps) {
		this.name = name;
		this.totalTasks = totalTasks;
		if (steps <= totalTasks) {
			this.tasksPerStep = totalTasks / steps;
		} else {
			this.tasksPerStep = 1;
		}
	}
	
	/**
	 * Complete a single task.
	 */
	public void completeTask() {
		completeTasks(1);
	}
	
	/**
	 * Complete given number of tasks.
	 * 
	 * @param tasks number of tasks to complete
	 */
	public void completeTasks(int tasks) {
		completedTasks += tasks;
		int currentSteps = (int)(completedTasks / tasksPerStep);
		if (currentSteps > reportedSteps || completedTasks >= totalTasks) {
			reportedSteps = currentSteps;
			ProgressManager.getInstance().completeTask(this);
		}
	}
	
	/**
	 * @return number of total tasks for this progress meter
	 */
	public int getTotalTasks() {
		return totalTasks;
	}

	/**
	 * @return number of completed tasks for this meter
	 */
	public int getCompletedTasks() {
		return completedTasks;
	}

	/**
	 * @return ratio of completed tasks / total tasks
	 */
	public float getProgress() {
		float result = 0;
		if (totalTasks > 0) {
			result = (float)completedTasks / totalTasks;
		}
		return result;
	}

	public String getName() {
		return name;
	}
	
}
