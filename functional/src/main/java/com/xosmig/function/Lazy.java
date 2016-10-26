package com.xosmig.function;

public interface Lazy<T> {
    T get();

    static <T>
    Lazy<T> value(T x) {
        return () -> x;
    }
}
