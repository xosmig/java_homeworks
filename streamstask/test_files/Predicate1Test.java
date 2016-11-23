package com.xosmig.function;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Predicate1Test {
    @Test
    public void alwaysTrueTest() throws Exception {
        assertThat(Predicate1.alwaysTrue().apply(5), is(true));
    }

    @Test
    public void alwaysFalseTest() throws Exception {
        assertThat(Predicate1.alwaysFalse().apply(5), is(false));
    }

    @Test
    public void applyTest() throws Exception {
        Predicate1<Integer> even = x -> x % 2 == 0;
        assertThat(even.apply(0), is(true));
        assertThat(even.apply(1), is(false));
        assertThat(even.apply(2), is(true));
    }

    /**
     * Tests okA common part of `orTest` and `andTest`.
     */
    @Test
    public void applyTest2() throws Exception {
        FooBar okA = new FooBar();
        FooBar okAB = new FooBar();
        FooBar okB = new FooBar();
        FooBar ok = new FooBar();

        Predicate1<Object> f = x -> x.equals(okA) || x.equals(okAB);
        Predicate1<Object> g = x -> x.equals(okAB) || x.equals(okB);

        assertThat(f.apply(okA), is(true));
        assertThat(f.apply(okAB), is(true));
        assertThat(f.apply(okB), is(false));
        assertThat(f.apply(ok), is(false));

        assertThat(g.apply(okA), is(false));
        assertThat(g.apply(okAB), is(true));
        assertThat(g.apply(okB), is(true));
        assertThat(g.apply(ok), is(false));
    }

    @Test
    public void orTest() throws Exception {
        FooBar okA = new FooBar();
        FooBar okAB = new FooBar();
        FooBar okB = new FooBar();
        FooBar ok = new FooBar();

        Predicate1<Object> f = x -> x.equals(okA) || x.equals(okAB);
        Predicate1<Object> g = x -> x.equals(okAB) || x.equals(okB);

        Predicate1<FooBar> fOrG = f.or(g);

        assertThat(fOrG.apply(okA), is(true));
        assertThat(fOrG.apply(okAB), is(true));
        assertThat(fOrG.apply(okB), is(true));
        assertThat(fOrG.apply(ok), is(false));
    }

    @Test
    public void andTest() throws Exception {       class FooBar {}
        FooBar okA = new FooBar();
        FooBar okAB = new FooBar();
        FooBar okB = new FooBar();
        FooBar ok = new FooBar();

        Predicate1<Object> f = x -> x.equals(okA) || x.equals(okAB);
        Predicate1<Object> g = x -> x.equals(okAB) || x.equals(okB);

        Predicate1<FooBar> fAndG = f.and(g);

        assertThat(fAndG.apply(okA), is(false));
        assertThat(fAndG.apply(okAB), is(true));
        assertThat(fAndG.apply(okB), is(false));
        assertThat(fAndG.apply(ok), is(false));
    }

    @Test
    public void notTest() throws Exception {
        Predicate1<Integer> even = x -> x % 2 == 0;
        assertThat(even.not().apply(0), is(false));
        assertThat(even.not().apply(1), is(true));
        assertThat(even.not().apply(2), is(false));
    }

    private static class FooBar {}
}