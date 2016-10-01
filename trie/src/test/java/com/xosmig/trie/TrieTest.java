package com.xosmig.trie;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrieTest {
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
        assertEquals(1, trie.howManyStartsWithPrefix("Hel"));
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