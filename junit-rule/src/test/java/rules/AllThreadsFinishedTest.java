package rules;

import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.Rule;
import org.junit.Test;


public class AllThreadsFinishedTest {

    public AllThreadsFinished threadsCheckRule = new AllThreadsFinished();
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public RuleChain rules = RuleChain
            .outerRule(expectedException)
            .around(threadsCheckRule);

    @Test
    public void testOk() throws Exception {
        final int n = 5;
        Thread[] th = new Thread[n];
        for (int i = 0; i < n; i++) {
            th[i] = new Thread(this::doSomething);
            th[i].start();
            threadsCheckRule.register(th[i]);
        }
        for (int i = 0; i < n; i++) {
            th[i].join();
        }
    }

    @Test
    public void testNotFinished() throws Exception {
        expectedException.expect(AssertionError.class);
        expectedException.handleAssertionErrors();  // note: deprecated since JUnit 4.12

        final int n = 5;
        Thread[] th = new Thread[n];
        for (int i = 0; i < n; i++) {
            th[i] = new Thread(this::doSomething);
            th[i].start();
            threadsCheckRule.register(th[i]);
        }
    }

    @Test
    public void testExceptionOccurred() throws Exception {
        expectedException.expect(AssertionError.class);
        expectedException.handleAssertionErrors();

        Thread th = new Thread(() -> {
            doSomething();
            throw new RuntimeException("Hello, World!");
        });
        threadsCheckRule.register(th);
        th.start();
        th.join();
    }

    @Test
    public void testEmpty() throws Exception {
        doSomething();
    }

    volatile long r;

    private void doSomething() {
        for (int i = 0; i < 20_000; i++) {
            r += 1;
        }
    }
}
