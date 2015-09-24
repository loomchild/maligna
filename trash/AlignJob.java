package align.job;

import java.util.List;

import align.Alignment;
import align.formatter.Formatter;
import align.loader.Loader;
import align.task.Task;

public class AlignJob implements Job {

	private Loader loader;
	private Formatter formatter;
	private Task task;
	
	public AlignJob(Loader loader, Task task, Formatter formatter) {
		this.loader = loader;
		this.formatter = formatter;
		this.task = task;
	}
	
	public void run() {
		String sourceString = loader.loadSourceString();
		String targetString = loader.loadTargetString();
		List<Alignment> alignment = task.run(sourceString, targetString); 
		formatter.format(alignment);
	}

}
