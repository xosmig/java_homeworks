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
            .outerRule(this.expectedException)
            .around(this.threadsCheckRule);


    @Test
    public void testOk() throws Exception {
        final int n = 5;
        Thread[] th = new Thread[n];
        for (int i = 0; i < n; i++) {
            th[i] = new Thread(this::doSomething);
            th[i].start();
            this.threadsCheckRule.register(th[i]);
        }
        for (int i = 0; i < n; i++) {
            th[i].join();
        }
    }

    @Test
    public void testFail() throws Exception {
        this.expectedException.expect(AssertionError.class);
        this.expectedException.handleAssertionErrors();  // note: deprecated since JUnit 4.12

        final int n = 5;
        Thread[] th = new Thread[n];
        for (int i = 0; i < n; i++) {
            th[i] = new Thread(this::doSomething);
            th[i].start();
            this.threadsCheckRule.register(th[i]);
        }
    }

    @Test
    public void testEmpty() throws Exception {
        this.doSomething();
    }


    volatile long r;

    private void doSomething() {
        for (int i = 0; i < 20_000; i++) {
            r += 1;
        }
    }
}
