package com.xosmig.function;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Function2Test {
    @Test
    public void applyTest() throws Exception {
        Function2<Boolean, Integer, String> f = (b, i) -> Integer.toString(i + (b ? 7 : 2));
        assertThat(f.apply(true, 5), is("12"));
        assertThat(f.apply(false, 2), is("4"));
    }

    @Test
    public void bind1Test() throws Exception {
        Function2<Boolean, Object, String> f = (b, o) -> Integer.toString(b ? 7 : 2);
        Function1<Integer, String> g = f.bind1(Boolean.TRUE);
        assertThat(g.apply(123), is("7"));
    }

    @Test
    public void bind2Test() throws Exception {
        Function2<Boolean, Integer, String> f = (b, i) -> Integer.toString(i + (b ? 7 : 2));
        Function1<Boolean, String> g = f.bind2(5);
        assertThat(g.apply(true), is("12"));
        assertThat(g.apply(false), is("7"));
    }
}