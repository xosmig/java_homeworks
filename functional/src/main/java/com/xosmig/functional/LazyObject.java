package com.xosmig.functional;


import java.util.function.Supplier;

/**
 * LazyObject container.
 * Calls `getImpl` only once on the first call of `value`.
 */
public class LazyObject<T> implements Lazy<T> {
    T result;
    Supplier<T> getImpl;
    boolean counted;

    @Override
    public T value() {
        if (!counted) {
            counted = true;
            result = getImpl.get();
        }
        return result;
    }

    public LazyObject(Supplier<T> getImpl) {
        this.result = null;
        this.counted = false;
        this.getImpl = getImpl;
    }
}
