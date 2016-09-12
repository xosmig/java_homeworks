import com.xosmig.stringhashmap.StringHashMap;

/**
 * Created by Andrey Tonkikh on 11.09.16.
 */
public class Main { // FIXME: use normal unit tests (JUnit) instead of main function
    private static void assertEq(Object a, Object b) {
        if (a != b && !a.equals(b)) {
            throw new IllegalArgumentException();
        }
    }

    private static String magick1(int x) {
        return Integer.toString((x * 9122 + 8157) ^ 26685);
    }

    private static String magick2(int x) {
        return Integer.toString((x * 3119 + 26164) ^ 7391);
    }

    public static void main(String[] args) {
        StringHashMap map = new StringHashMap();

        for (int i = 0; i < 10; i++) {
            assertEq(map.contains(Integer.toString(i)), false);
            assertEq(map.size(), i);
            assertEq(map.put(Integer.toString(i), magick1(i)), null);
        }
        assertEq(map.capacity(), 16);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                assertEq(map.contains(Integer.toString(i)), true);
                assertEq(map.get(Integer.toString(i)), magick1(i));
            }
        }

        for (int i = 0; i < 10; i += 2) {
            assertEq(map.put(Integer.toString(i), magick2(i)), magick1(i));
        }
        assertEq(map.size(), 10);

        for (int i = 0; i < 10; i++) {
            assertEq(map.contains(Integer.toString(i)), true);
            String res = i % 2 == 0 ? magick2(i) : magick1(i);
            assertEq(map.get(Integer.toString(i)), res);
            assertEq(map.remove(Integer.toString(i)), res);
        }
        assertEq(map.size(), 0);
        assertEq(map.capacity(), 16);

        assertEq(map.put("1", "Cat"), null);
        map.clear();
        assertEq(map.size(), 0);
        assertEq(map.capacity(), 1);

        System.out.println("[^_^]");
    }
}
