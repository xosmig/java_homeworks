package com.xosmig.function;


import java.util.function.Supplier;

public class Lazy2<T> {
    T result;
    boolean counted;
    Supplier<T> getImpl;

    public
    T value() {
        if (!counted) {
            counted = true;
            result = getImpl.get();
        }
        return result;
    }

    private Lazy2(Supplier<T> getImpl) {
        this.getImpl = getImpl;
    }

    public static <T>
    Lazy2<T> create(Supplier<T> getImpl) {
        return new Lazy2<>(getImpl);
    }

    public static <T>
    Lazy2<T> valueOf(T x) {
        return Lazy2.create(() -> x);
    }
}
