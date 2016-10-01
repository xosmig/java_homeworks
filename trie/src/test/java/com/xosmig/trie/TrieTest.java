package com.xosmig.trie;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.function.Function;

import static org.junit.Assert.*;

public class TrieTest {

    private static String magic1(int x) {
        return Integer.toString((25681 * x + 30192) ^ 26743);
    }

    private static String magic2(int x) {
        return Integer.toString((31366 * x + 20688) ^ 11086);
    }

    private Trie generateTrie(int size, Function<Integer, String> generator) {
        Trie trie = new Trie();
        for (int i = 0; i < size; i++) {
            trie.add(generator.apply(i));
        }
        return trie;
    }

    @Test
    public void serializeAndDeserializeTest() throws Exception {
        final int size1 = 100;
        final int size2 = 200;

        String serialized1;
        {
            Trie trie = generateTrie(size1, TrieTest::magic1);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            trie.serialize(out);
            serialized1 = out.toString();
        }

        String serialized2;
        {
            ByteArrayInputStream in = new ByteArrayInputStream(serialized1.getBytes());

            // fill it with other data
            Trie trie = generateTrie(size2, TrieTest::magic2);
            trie.deserialize(in);

            assertEquals(size1, trie.size());
            for (int i = 0; i < size1; i++) {
                assertTrue(trie.contains(magic1(i)));
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            trie.serialize(out);
            serialized2 = out.toString();
        }

        assertEquals(serialized1, serialized2);
    }

    // It is pointless to test methods independently.
    @Test
    public void complexTest() throws Exception {
        Trie trie = new Trie();

        assertTrue(trie.add("Hello"));
        assertTrue(trie.add("Halo"));
        assertEquals(2, trie.size());
        assertFalse(trie.add("Halo"));
        assertEquals(2, trie.size());

        assertEquals(2, trie.howManyStartsWithPrefix("H"));
        assertEquals(1, trie.howManyStartsWithPrefix("He"));
        assertEquals(1, trie.howManyStartsWithPrefix("Ha"));
        assertEquals(1, trie.howManyStartsWithPrefix("Hal"));
        assertEquals(1, trie.howManyStartsWithPrefix("Hello"));
        assertEquals(0, trie.howManyStartsWithPrefix("Hi"));

        assertTrue(trie.contains("Hello"));
        assertTrue(trie.contains("Halo"));

        assertTrue(trie.remove("Hello"));
        assertEquals(1, trie.size());

        assertEquals(1, trie.howManyStartsWithPrefix("H"));
        assertEquals(1, trie.howManyStartsWithPrefix("Ha"));
        assertEquals(0, trie.howManyStartsWithPrefix("He"));
        assertEquals(0, trie.howManyStartsWithPrefix("Hel"));

        assertFalse(trie.contains("Hello"));
        assertTrue(trie.contains("Halo"));
    }
}