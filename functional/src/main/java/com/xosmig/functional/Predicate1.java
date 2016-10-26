package com.xosmig.functional;

public interface Predicate1<T> {
    Predicate1 ALWAYS_TRUE = x -> true;
    Predicate1 ALWAYS_FALSE = x -> false;

    static <U> Predicate1<U> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    static <U> Predicate1<U> alwaysFalse() {
        return ALWAYS_FALSE;
    }

    boolean apply(T x);

    default Predicate1<T> or(Predicate1<T> other) {
        return x -> this.apply(x) || other.apply(x);
    }

    default Predicate1<T> and(Predicate1<T> other) {
        return x -> this.apply(x) && other.apply(x);
    }

    default Predicate1<T> not() {
        return x -> !this.apply(x);
    }
}
