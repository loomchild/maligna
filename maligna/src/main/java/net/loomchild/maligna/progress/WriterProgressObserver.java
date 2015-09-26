package net.loomchild.maligna.progress;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * Represents progress observer that uses {@link Writer} to record the events.
 * </p>
 * 
 * <p>
 * It needs to be registered via 
 * {@link ProgressManager#registerProgressObserver(ProgressObserver)} method
 * before use.
 * </p>
 *  
 * @author loomchild
 */
public class WriterProgressObserver implements ProgressObserver {
	
	/**
	 * Character that will be used to indicate the progress.
	 */
	public static final char PROGRESS_CHAR = '.';
	
	private Writer writer;

	private int size;
	
	private int index;
	
	/**
	 * Creates progress observer.
	 * 
	 * @param writer writer to use to communicate events.
	 * @param size size of progress bar in characters
	 */
	public WriterProgressObserver(Writer writer, int size) {
		this.writer = writer;
		this.size = size;
		reset();
	}
		
	/**
	 * Writes new character if necessary.
	 */
	@Override
	public void completeTask(ProgressMeter progressMeter) {
		int newIndex = (int)(size * progressMeter.getProgress());
		if (newIndex > index) {
			updateIndex(newIndex);
		}
	}
	
	private void updateIndex(int newIndex) {
		assert newIndex > index && newIndex <= size;
		
		for (; index < newIndex; ++index) {
			write(PROGRESS_CHAR);
		}
	}

	/**
	 * Writes task name.
	 */
	@Override
	public void registerProgressMeter(ProgressMeter progressMeter) {
		write(progressMeter.getName() + " [" + progressMeter.getTotalTasks() + " ops] ");
	}

	/**
	 * Resets its index for the next task and writes EOL character. 
	 */
	@Override
	public void unregisterProgressMeter(ProgressMeter progressMeter) {
		reset();
		write('\n');
	}
	
	private void reset() {
		this.index = 0;
	}
	
	private void write(char character) {
		try {
			writer.write(character);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void write(String string) {
		try {
			writer.write(string);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
