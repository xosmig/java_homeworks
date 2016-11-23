package sp;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static org.junit.Assert.*;
import static sp.SecondPartTasks.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws Exception {
        Path testDir = Paths.get("test_files");
        List<String> files = new ArrayList<>();
        Files.walkFileTree(testDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file.toString());
                return FileVisitResult.CONTINUE;
            }
        });
        List<String> publics = findQuotes(files, "public");
        assertTrue(publics.stream().count() > 200);
        assertTrue(publics.contains("    public static void main(String[] args) {"));
        assertTrue(publics.contains("public class Artist {"));

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
        List<Map<String, Integer>> orders = new ArrayList<>();

        {
            Map<String, Integer> m = new HashMap<>();
            m.put("apple", 5);
            m.put("banana", 8);
            orders.add(m);
        }

        {
            Map<String, Integer> m = new HashMap<>();
            m.put("banana", 2);
            m.put("cherry", 3);
            orders.add(m);
        }

        {
            Map<String, Integer> m = new HashMap<>();
            m.put("cherry", 1);
            orders.add(m);
        }

        Map<String, Integer> m = calculateGlobalOrder(orders);
        assertEquals(Integer.valueOf(5), m.get("apple"));
        assertEquals(Integer.valueOf(10), m.get("banana"));
        assertEquals(Integer.valueOf(4), m.get("cherry"));
    }
}
