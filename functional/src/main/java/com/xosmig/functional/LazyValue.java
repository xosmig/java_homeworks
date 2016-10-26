package com.xosmig.functional;

public class LazyValue<T> implements Lazy<T> {
    T value;

    public LazyValue(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }
}
