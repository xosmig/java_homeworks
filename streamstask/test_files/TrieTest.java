package com.xosmig.trie;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.function.Function;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void addAndRemoveAndContainsAndSizeTest() throws Exception {
        final int size = 100;
        Function<Integer, String> magic = TrieTest::magic1;

        Trie trie = new Trie();

        for (int i = 0; i < size; i++) {
            assertTrue(trie.add(magic.apply(i)));
        }
        assertEquals(size, trie.size());

        for (int i = 0; i < size; i += 2) {
            assertTrue(trie.contains(magic.apply(i)));
            assertFalse(trie.add(magic.apply(i)));
        }

        int removed = 0;
        for (int i = 0; i < size; i += 3) {
            assertTrue(trie.remove(magic.apply(i)));
            assertFalse(trie.contains(magic.apply(i)));
            removed++;
        }
        assertEquals(size - removed, trie.size());

        for (int i = 0; i < size; i+= 3) {
            assertTrue(trie.add(magic.apply(i)));
            assertTrue(trie.contains(magic.apply(i)));
        }
        assertEquals(size, trie.size());
    }


    @Test
    public void howManyStartsWithPrefixTest() throws Exception {
        Trie trie = new Trie();

        trie.add("Hello");
        trie.add("Halo");

        assertEquals(2, trie.howManyStartsWithPrefix("H"));
        assertEquals(1, trie.howManyStartsWithPrefix("He"));
        assertEquals(1, trie.howManyStartsWithPrefix("Ha"));
        assertEquals(1, trie.howManyStartsWithPrefix("Hal"));
        assertEquals(1, trie.howManyStartsWithPrefix("Hello"));
        assertEquals(0, trie.howManyStartsWithPrefix("Hi"));

        trie.remove("Hello");

        assertEquals(1, trie.howManyStartsWithPrefix("H"));
        assertEquals(1, trie.howManyStartsWithPrefix("Ha"));
        assertEquals(0, trie.howManyStartsWithPrefix("He"));
        assertEquals(0, trie.howManyStartsWithPrefix("Hel"));
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
}