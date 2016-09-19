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
    public void size() throws Exception {
        StringHashMap map = new StringHashMap(2);
        Assert.assertEquals(map.size(), 0);

        map.put("a", "1");
        Assert.assertEquals(map.size(), 1);
        map.put("a", "2");
        Assert.assertEquals(map.size(), 1);

        map.put("b", "1");
        Assert.assertEquals(map.size(), 2);

        map.put("c", "1");
        Assert.assertEquals(map.size(), 3);
    }

    @Test
    public void capacity() throws Exception {
        StringHashMap map = new StringHashMap(2);
        Assert.assertEquals(map.capacity(), 2);

        map.put("a", "1");
        Assert.assertEquals(map.capacity(), 2);
        map.put("a", "2");
        Assert.assertEquals(map.capacity(), 2);

        map.put("b", "1");
        Assert.assertEquals(map.capacity(), 2);

        map.put("c", "1");
        Assert.assertEquals(map.capacity(), 4);
    }

    @Test
    public void contains() throws Exception {
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
    public void clear() throws Exception {
        StringHashMap map = new StringHashMap(10);
        map.put("1", "Cat");
        map.clear();
        assertEquals(map.size(), 0);
        assertEquals(map.capacity(), 10);
    }

    @Test
    public void complex() throws Exception {
        StringHashMap map = new StringHashMap();

        for (int i = 0; i < 10; i++) {
            String s = Integer.toString(i);
            assertFalse(map.contains(s));
            assertEquals(map.size(), i);
            assertEquals(map.put(s, magic1(s)), null);
        }
        assertEquals(map.capacity(), 16);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                String s = Integer.toString(i);
                assertTrue(map.contains(s));
                assertEquals(map.get(s), magic1(s));
            }
        }

        for (int i = 0; i < 10; i += 2) {
            String s = Integer.toString(i);
            assertEquals(map.put(s, magic2(s)), magic1(s));
        }
        assertEquals(map.size(), 10);

        for (int i = 0; i < 10; i++) {
            String s = Integer.toString(i);
            assertTrue(map.contains(s));
            String res = i % 2 == 0 ? magic2(s) : magic1(s);
            assertEquals(map.get(s), res);
            assertEquals(map.remove(s), res);
        }
        assertEquals(map.size(), 0);
        assertEquals(map.capacity(), 16);
    }

}