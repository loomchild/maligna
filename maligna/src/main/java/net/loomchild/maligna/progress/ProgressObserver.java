package net.loomchild.maligna.progress;

/**
 * Represents progress observer that listens to progress events.
 * 
 * @author loomchild
 */
public interface ProgressObserver {

	/**
	 * Occurs when {@link ProgressManager#registerProgressMeter(ProgressMeter)} 
	 * method is called, at the beginning of a process.
	 * @param progressMeter
	 */
	public void registerProgressMeter(ProgressMeter progressMeter);
	
	/**
	 * Occurs when {@link ProgressMeter#completeTask()} or
	 * {@link ProgressMeter#completeTasks(int)} methods are called. 
	 * Not every completed task may trigger this event, 
	 * number of notifications can be lower than the number of actual tasks.
	 * @param progressMeter
	 */
	public void completeTask(ProgressMeter progressMeter);

	/**
	 * Occurs when {@link ProgressManager#unregisterProgressMeter(ProgressMeter)} 
	 * method is called, at the end of a process.
	 * @param progressMeter
	 */
	public void unregisterProgressMeter(ProgressMeter progressMeter);

}
