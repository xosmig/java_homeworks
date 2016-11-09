import java.util.function.Function;

/**
 * TODO: TRY: git clean -dfx
 */


public class Main {
    public static String HELP_MESSAGE = "HELP!";

    public static void main(String[] args) {
        Function<Integer, Integer> foo = x -> x + 1;
        System.out.println(foo.apply(5));
    }
}
