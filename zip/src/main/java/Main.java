import com.xosmig.zip.MZip;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {
    public static final class ExitCode {
        private static final int OK = 0;
        private static final int BAD_ARGUMENTS = 17;
        private static final int EXCEPTION = 23;
    }
    public static final String HELP_MSG =
            "Command line params:\n" +
                "\tfirst - source file or directory\n" +
                "\tsecond - target directory\n" +
                "\tthird - regex pattern\n";


    public static void main(String[] args) {
        if (args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
            System.out.println(HELP_MSG);
            System.exit(ExitCode.OK);
        }

        if (args.length != 3) {
            if (args.length < 3) {
                System.err.println("Too few arguments.");
            } else {
                System.err.println("Too many arguments.");
            }
            System.out.println(HELP_MSG);
            System.exit(ExitCode.BAD_ARGUMENTS);
        }

        Path source;
        Path target;
        Pattern pattern;

        try {
            source = Paths.get(args[0]);
            target = Paths.get(args[1]);
            pattern = Pattern.compile(args[2]);
        } catch(PatternSyntaxException e) {
            System.err.println("Bad pattern:");
            System.err.println(e.getMessage());
            System.exit(ExitCode.BAD_ARGUMENTS);
            throw e;  // unreachable, but necessary to avoid compile errors
        }

        try {
            MZip.extractMatch(source, target, pattern);
        } catch (IOException e) {
            System.err.println("Fail:");
            System.err.println(e.getMessage());
            System.exit(ExitCode.EXCEPTION);
        }

        System.out.println("[^_^]");
    }
}
