package rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * This rule will automatically check that all threads, registered via <code>register</code> method call, are finished.
 */
public class AllThreadsFinished implements TestRule {

    private List<Thread> threads = new ArrayList<>();


    /**
     * Register new thread.
     */
    public void register(Thread thread) {
        this.threads.add(thread);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                for (Thread thread : threads) {
                    assertTrue("Thread \"" + thread.getName() + "\" isn't terminated.",
                                thread.getState() == Thread.State.TERMINATED);
                }
            }
        };
    }
}
