import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MainTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @Before
    public void initStreams() {
        System.setOut(new PrintStream(out));
        System.setOut(new PrintStream(err));
    }

    @Test
    public void mainTestFewArguments() throws Exception {

    }

}