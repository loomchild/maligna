package net.sourceforge.align.ui.console.command;


import net.sourceforge.align.AlignTestSuite;

import org.junit.internal.runners.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



/**
 * Test suite containing all tests executed using console.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AlignTestSuite.class
})
public class TestCommand implements Command {

	public String getName() {
		return "test";
	}

	public void run(String[] args) {
        JUnitCore core = new JUnitCore();
        core.addListener(new TextListener());
        core.run(TestCommand.class);
	}

}
