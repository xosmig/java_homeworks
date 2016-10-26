package com.xosmig.function;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Function2Test {
    @Test
    public void applyTest() throws Exception {

    }

    @Test
    public void bind1Test() throws Exception {
        Function2<Boolean, Object, String> f = (b, o) -> Integer.toString(b ? 7 : 2);
        Function1<Integer, String> g = f.bind1(Boolean.TRUE);
        assertThat(g.apply(123), is("7"));
    }

    @Test
    public void bind2Test() throws Exception {

    }

    @Test
    public void curryTest() throws Exception {

    }

}