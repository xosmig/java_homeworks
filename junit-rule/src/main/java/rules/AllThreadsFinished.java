package rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.*;

import static org.junit.Assert.*;


/**
 * This rule will automatically check that all threads, registered via <code>register</code> method call,
 * are finished without exceptions.
 */
public class AllThreadsFinished implements TestRule {

    private final Map<Thread, Throwable> uncaught = new HashMap<>();

    private List<Thread> threads = new ArrayList<>();
    private Thread.UncaughtExceptionHandler exceptionHandler = uncaught::put;

    /**
     * Register new thread.
     */
    public void register(Thread thread) {
        threads.add(thread);
//        if (thread.getUncaughtExceptionHandler() == null) {
            thread.setUncaughtExceptionHandler(exceptionHandler);
//        }
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                for (Thread thread : threads) {
                    Throwable e = uncaught.get(thread);
                    if (e != null) {
                        throw new AssertionError("Uncaught exception in thread \"" + thread.getName() + "\"", e);
                    }

                    assertTrue("Thread \"" + thread.getName() + "\" isn't terminated.",
                                thread.getState() == Thread.State.TERMINATED);
                }
            }
        };
    }
}
