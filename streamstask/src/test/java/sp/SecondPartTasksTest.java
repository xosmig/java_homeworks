package sp;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static sp.SecondPartTasks.findPrinter;
import static sp.SecondPartTasks.piDividedBy4;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
//        fail();
    }

    @Test
    public void testPiDividedBy4() {
        assertTrue(Math.abs(piDividedBy4() - Math.PI / 4) < 1e-3);
    }

    @Test
    public void testFindPrinter1() {
        Map<String, List<String>> comp = new HashMap<>();
        comp.put("A", Arrays.asList("qwertyuiop[]", "asdfghjkl;'", "zxcvbnm,./"));
        comp.put("B", Collections.nCopies(100, "b"));
        comp.put("C", Collections.emptyList());
        assertEquals(Optional.of("B"), findPrinter(comp));
    }

    @Test
    public void testFindPrinter2() {
        Map<String, List<String>> comp = new HashMap<>();
        comp.put("A", Arrays.asList("qwertyuiop[]", "asdfghjkl;'", "zxcvbnm,./"));
        comp.put("B", Collections.nCopies(20, "b"));
        comp.put("C", Collections.emptyList());
        assertEquals(Optional.of("A"), findPrinter(comp));
    }

    @Test
    public void testFindPrinterEmptyAuthors() {
        Map<String, List<String>> comp = new HashMap<>();
        comp.put("A", Collections.emptyList());
        comp.put("B", Collections.emptyList());
        comp.put("C", Collections.emptyList());
        Optional<String> res = findPrinter(comp);
        assertTrue(res.isPresent());
        assertTrue(     Optional.of("A").equals(res)
                    ||  Optional.of("B").equals(res)
                    ||  Optional.of("C").equals(res));
    }

    @Test
    public void testFindPrinterNone() {
        Map<String, List<String>> comp = new HashMap<>();
        assertEquals(Optional.empty(), findPrinter(comp));
    }

    @Test
    public void testFindPrinterOneAuthor() {
        Map<String, List<String>> comp = new HashMap<>();
        comp.put("A", Collections.emptyList());
        assertEquals(Optional.of("A"), findPrinter(comp));
    }

    @Test
    public void testCalculateGlobalOrder() {
//        fail();
    }
}
