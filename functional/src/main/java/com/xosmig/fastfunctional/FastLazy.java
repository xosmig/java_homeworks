package com.xosmig.fastfunctional;

/**
 * Can be used for some kind of laziness, but it calculates its value on every `get`.
 */
public interface FastLazy<T> {
    T get();

    static <T>
    FastLazy<T> value(T x) {
        return () -> x;
    }
}
