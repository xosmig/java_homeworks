package com.xosmig.functional;

import java.util.function.Supplier;

/**
 * A wrapper for lazy computation.
 * You should not override it by yourself.
 */
public abstract class Lazy<T> {
    /**
     * Returns its value. Calculates if necessary.
     */
    abstract T value();

    /**
     * Creates a `Lazy` which will get its value via calling `getValue`
     * after the first call of `value` method.
     * It's guaranteed that `getValue` will be called only once.
     */
    public static <U>
    Lazy<U> expr(Supplier<U> getValue) {
        return new LazyObject<>(getValue);
    }

    /**
     * Creates a `Lazy` with the given value.
     */
    public static <U>
    Lazy<U> valueOf(U x) {
        return new LazyValue<>(x);
    }

    /**
     *  Returns null if `lazy` is null and its value otherwise.
     */
    public static <U>
    U nullable(Lazy<U> lazy) {
        if (lazy == null) {
            return null;
        } else {
            return lazy.value();
        }
    }

    private static class LazyObject<T> extends Lazy<T> {
        T result;
        final Supplier<T> getImpl;
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

    private static class LazyValue<T> extends Lazy<T> {
        T value;

        public LazyValue(T value) {
            this.value = value;
        }

        @Override
        public T value() {
            return value;
        }
    }
}
