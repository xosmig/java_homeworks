package com.xosmig.functional;

import java.util.function.Supplier;

public abstract class Lazy<T> {
    abstract T value();

    public static <U>
    Lazy<U> expr(Supplier<U> getValue) {
        return new LazyObject<>(getValue);
    }

    public static <U>
    Lazy<U> valueOf(U x) {
        return new LazyValue<>(x);
    }

    public static <U>
    U nullable(Lazy<U> box) {
        if (box == null) {
            return null;
        } else {
            return box.value();
        }
    }
}
