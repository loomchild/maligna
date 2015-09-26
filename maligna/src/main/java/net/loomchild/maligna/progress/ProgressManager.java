package net.loomchild.maligna.progress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents manager for all tasks in progress and their observers. Singleton.
 * 
 * @author loomchild
 */
public class ProgressManager {
	
	private static ProgressManager instance = new ProgressManager();

	private Map<String, ProgressMeter> progressMeterMap;
	
	private List<ProgressObserver> progressObserverList;
	
	/**
	 * @return singleton manager instance
	 */
	public static ProgressManager getInstance() {
		return instance;
	}
	
	private ProgressManager() {
		this.progressMeterMap = new HashMap<String, ProgressMeter>();
		this.progressObserverList = new ArrayList<ProgressObserver>();
	}

	/**
	 * @param name progress meter name
	 * @return progress meter instance with given name
	 */
	public ProgressMeter getProgressMeter(String name) {
		ProgressMeter progressMeter = progressMeterMap.get(name);
		
		if (progressMeter == null) {
			throw new IllegalArgumentException("Progress meter: " + name + " does not exist.");
		}
		
		return progressMeter;
	}

	/**
	 * Registers progress meter in the manager, fires register event to the
	 * observers.
	 * @param progressMeter
	 */
	public void registerProgressMeter(ProgressMeter progressMeter) {
		if (progressMeterMap.get(progressMeter.getName()) != null) {
			throw new IllegalArgumentException("Progress meter: " + progressMeter.getName() + " already exists.");
		}
		
		progressMeterMap.put(progressMeter.getName(), progressMeter);

		for(ProgressObserver progressObserver : progressObserverList) {
			progressObserver.registerProgressMeter(progressMeter);
		}
	}

	/**
	 * Unregisters progress meter from the manager, fires unregister event
	 * to the observers.
	 * @param progressMeter
	 */
	public void unregisterProgressMeter(ProgressMeter progressMeter) {
		if (progressMeterMap.remove(progressMeter.getName()) == null) {
			throw new IllegalArgumentException("Progress meter: " + progressMeter.getName() + " does not exist.");
		}

		for(ProgressObserver progressObserver : progressObserverList) {
			progressObserver.unregisterProgressMeter(progressMeter);
		}
	}
	
	/**
	 * Fires complete task event to the observers.
	 * @param progressMeter
	 */
	void completeTask(ProgressMeter progressMeter) {
		for(ProgressObserver progressObserver : progressObserverList) {
			progressObserver.completeTask(progressMeter);
		}
	}
	
	/**
	 * Registers progress observer, it will be notified of the future events.
	 * @param progressObserver
	 */
	public void registerProgressObserver(ProgressObserver progressObserver) {
		this.progressObserverList.add(progressObserver);
	}
	
	/**
	 * Unregisters progress observer. It will not be notified of the future
	 * events.
	 * @param progressObserver
	 */
	public void unregisterProgressObserver(ProgressObserver progressObserver) {
		if (!progressObserverList.remove(progressObserver)) {
			throw new IllegalArgumentException("Unknown observer " + progressObserver.toString());
		}
	}
	
	/**
	 * @return list of progress observers.
	 */
	public List<ProgressObserver> getProgressObserverList() {
		return progressObserverList;
	}

}
