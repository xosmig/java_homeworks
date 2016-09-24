package com.xosmig.hashmap;

import org.junit.Assert;
import org.omg.CORBA.Object;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created by Andrey Tonkikh on 19.09.16.
 */
public class StringHashMapTest {
    @Test
    public void sizeTest() throws Exception {
        StringHashMap map = new StringHashMap(2);
        Assert.assertEquals(0, map.size());

        map.put("a", "1");
        Assert.assertEquals(1, map.size());
        map.put("a", "2");
        Assert.assertEquals(1, map.size());

        map.put("b", "1");
        Assert.assertEquals(2, map.size());

        map.put("c", "1");
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void capacityTest() throws Exception {
        StringHashMap map = new StringHashMap(2);
        Assert.assertEquals(2, map.capacity());

        map.put("a", "1");
        Assert.assertEquals(2, map.capacity());
        map.put("a", "2");
        Assert.assertEquals(2, map.capacity());

        map.put("b", "1");
        Assert.assertEquals(2, map.capacity());

        map.put("c", "1");
        Assert.assertEquals(4, map.capacity());
    }

    @Test
    public void containsTest() throws Exception {
        StringHashMap map = new StringHashMap(2);
        assertFalse(map.contains("a"));

        map.put("a", "1");
        assertTrue(map.contains("a"));
        map.put("a", "2");
        assertTrue(map.contains("a"));
        map.remove("a");
        assertFalse(map.contains("a"));

        map.put("b", "1");
        assertTrue(map.contains("b"));
        assertFalse(map.contains("a"));
    }

    private String magic1(String s) {
        return Integer.toString((s.hashCode() * 7765 + 2313) ^ 5800);
    }

    private static String magic2(String s) {
        return Integer.toString((s.hashCode() * 3119 + 2164) ^ 7391);
    }

    @Test
    public void clearTest() throws Exception {
        StringHashMap map = new StringHashMap(10);
        map.put("1", "Cat");
        map.put("2", "Dog");
        map.put("3", "Frog");
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void complexTest() throws Exception {
        StringHashMap map = new StringHashMap();

        for (int i = 0; i < 10; i++) {
            String s = Integer.toString(i);
            assertFalse(map.contains(s));
            assertEquals(i, map.size());
            assertEquals(null, map.put(s, magic1(s)));
        }
        assertEquals(16, map.capacity());
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                String s = Integer.toString(i);
                assertTrue(map.contains(s));
                assertEquals(magic1(s), map.get(s));
            }
        }

        for (int i = 0; i < 10; i += 2) {
            String s = Integer.toString(i);
            assertEquals(magic1(s), map.put(s, magic2(s)));
        }
        assertEquals(10, map.size());

        for (int i = 0; i < 10; i++) {
            String s = Integer.toString(i);
            assertTrue(map.contains(s));
            String res = i % 2 == 0 ? magic2(s) : magic1(s);
            assertEquals(res, map.get(s));
            assertEquals(res, map.remove(s));
        }
        assertEquals(0, map.size(), 0);
        assertEquals(16, map.capacity());
    }
}
