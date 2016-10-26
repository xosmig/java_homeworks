package com.xosmig.functional;

import com.xosmig.function.Function1;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Function1Test {
    @Test
    public void applyTest() throws Exception {
        Function1<Integer, String> f = x -> Integer.toString(x * 2);
        assertThat(f.apply(7), is("14"));
    }

    @Test
    public void compose() throws Exception {
        Function1<Integer, String> x5toString = x -> Integer.toString(x * 5);
        Function1<Object, Integer> f = x -> 5;

        Function1<Boolean, String> g = x5toString.compose(f);

        assertThat(g.apply(true), is("25"));
    }
}
