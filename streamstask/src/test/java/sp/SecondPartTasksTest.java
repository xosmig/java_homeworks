package sp;

import org.junit.Test;

import static org.junit.Assert.*;
import static sp.SecondPartTasks.piDividedBy4;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
//        fail();
    }

    @Test
    public void testPiDividedBy4() {
//        System.out.println(piDividedBy4());
        assertTrue(Math.abs(piDividedBy4() - Math.PI / 4) < 1e-3);
    }

    @Test
    public void testFindPrinter() {
//        fail();
    }

    @Test
    public void testCalculateGlobalOrder() {
//        fail();
    }
}
