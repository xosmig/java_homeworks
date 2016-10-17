package com.xosmig.bst;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlowSetTest {
    private static String magic(int x) {
        return Integer.toString((x * 77651 + 2313) ^ 5800);
    }

    @Test
    public void testAll() throws Exception {
        final int size = 30;

        SlowSet<String> set = new SlowSet<>();
        for (int i = 0; i < size; i++) {
            assertTrue(set.add(magic(i)));
        }
        assertEquals(size, set.size());
        for (int i = 0; i < size; i += 2) {
            assertFalse(set.add(magic(i)));
        }
        assertEquals(size, set.size());

        for (int i = 0; i < size; i++) {
            assertTrue(set.contains(magic(i)));
        }
    }
}