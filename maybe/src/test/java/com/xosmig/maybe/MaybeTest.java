package com.xosmig.maybe;

import com.xomig.maybe.Maybe;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void justTest() throws Exception {
        Maybe<Integer> opt = Maybe.just(15);
        assertTrue(opt.isPresent());
        assertThat(opt.get(), is(15));
    }

    @Test(expected = Maybe.UnwrapException.class)
    public void nothingTest() throws Exception {
        Maybe<Integer> opt = Maybe.nothing();
        assertTrue(opt.isNothing());
        opt.get();
    }

    @Test
    public void mapTest() throws Exception {
        Maybe<Integer> mb15 = Maybe.just(15);
        Maybe<Double> mb225 = mb15.map((Integer x) -> (double)x * (double)x);
        assertThat(mb225.get(), is(225.0));
    }

    @Test
    public void mapOnNothingTest() throws Exception {
        Maybe<Integer> nothing = Maybe.nothing();
        assertTrue(nothing.map((Integer x) -> (double)x * (double)x).isNothing());
    }
}