package com.xosmig.functional;

import mcollections.LazyList;
import mcollections.MCollections;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MCollectionsTest {
    @Test
    public void filterTest() throws Exception {
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            a.add(i);
        }
        Collections.shuffle(a);
        LazyList<Integer> filtered = MCollections.filter((x -> x < 7), a);
        Collections.sort(filtered);
        assertThat(filtered.size(), is(7));
    }
}