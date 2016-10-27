package com.xosmig.functional;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.xosmig.functional.Functional.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FunctionalTest {
    @Test
    public void foldrTest() throws Exception {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(10);
        a.add(7);
        a.add(5);
        Collections.reverse(a);
        // 10 - (7 - (5 - 2)) = 6
        assertThat(foldr((x, r) -> x - r.value(), 2, a).value(), is(6));
    }

    @Test
    public void foldlTest() throws Exception {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(10);
        a.add(7);
        a.add(5);
        Collections.reverse(a);
        // ((2 - 10) - 7) - 5 = -20
        assertThat(foldl((r, x) -> r.value() - x, 2, a).value(), is(-20));
    }

    @Test
    public void mapTest() throws Exception {
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            a.add(i);
        }
        final StringBuilder out = new StringBuilder();

        Lazy<LazyList<Integer>> l1 = Functional.map(
                x -> { out.append(x); out.append(" "); return x * 2 + 1; }
                , a
        );

        out.append("0: ");
        LazyList<Integer> list = l1.value();

        out.append("1..5: ");
        list.get(5);

        out.append("6..9: ");
        list.size();

        assertThat(out.toString(), is("0: 0 1..5: 1 2 3 4 5 6..9: 6 7 8 9 "));
        assertThat(list.size(), is(10));
        assertThat(list.toArray(), is(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}));
    }

    @Test
    public void filterTest() throws Exception {
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            a.add(i);
        }
        final StringBuilder out = new StringBuilder();

        Lazy<LazyList<Integer>> l1 = Functional.filter(
                x -> { out.append(x); out.append(" "); return x < 10; }
                , a
        );

        out.append("0: ");
        LazyList<Integer> list = l1.value();

        out.append("1..5: ");
        list.get(5);

        out.append("6..19: ");
        list.size();

        assertThat(out.toString(), is("0: 0 1..5: 1 2 3 4 5 6..19: 6 7 8 9 10 11 12 13 14 15 16 17 18 19 "));
        assertThat(list.size(), is(10));
        assertThat(list.toArray(), is(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }

    @Test
    public void takeWhileTest() throws Exception {
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            a.add(i);
        }
        final StringBuilder out = new StringBuilder();

        Lazy<LazyList<Integer>> l1 = Functional.takeWhile(
                x -> { out.append(x); out.append(" "); return x != 10; }
                , a
        );

        out.append("0: ");
        LazyList<Integer> list = l1.value();

        out.append("1..5: ");
        list.get(5);

        out.append("6..10: ");
        list.size();
        // it is stopped after reaching 10th element

        assertThat(out.toString(), is("0: 0 1..5: 1 2 3 4 5 6..10: 6 7 8 9 10 "));
        assertThat(list.size(), is(10));
        assertThat(list.toArray(), is(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }

    @Test
    public void takeUntilTest() throws Exception {
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            a.add(i);
        }
        final StringBuilder out = new StringBuilder();

        Lazy<LazyList<Integer>> l1 = Functional.takeUntil(
                x -> { out.append(x); out.append(" "); return x == 10; }
                , a
        );

        out.append("0: ");
        LazyList<Integer> list = l1.value();

        out.append("1..5: ");
        list.get(5);

        out.append("6..10: ");
        list.size();
        // it is stopped after reaching 10th element

        assertThat(out.toString(), is("0: 0 1..5: 1 2 3 4 5 6..10: 6 7 8 9 10 "));
        assertThat(list.size(), is(10));
        assertThat(list.toArray(), is(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }
}