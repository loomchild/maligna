package net.loomchild.maligna.ui.console.command;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.RunWith;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;



/**
 * Test suite containing all tests executed using console.
 * @author loomchild
 */
@RunWith(ClasspathSuite.class)
@ClasspathSuite.IncludeJars(true)
@ClasspathSuite.ClassnameFilters({"net\\.loomchild\\.maligna\\..*"})
public class TestCommand implements Command {

	public String getName() {
		return "test";
	}

	public void run(String[] args) {
        JUnitCore core = new JUnitCore();
        core.addListener(new TextListener(System.out));
        core.run(TestCommand.class);
	}

}
