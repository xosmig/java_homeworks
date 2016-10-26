package com.xosmig.function;

public interface Predicate1<T> {
    Predicate1 ALWAYS_TRUE = x -> true;
    Predicate1 ALWAYS_FALSE = x -> false;

    static <U>
    Predicate1<U> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    static <U>
    Predicate1<U> alwaysFalse() {
        return ALWAYS_FALSE;
    }

    boolean apply(T x);

    default <ET extends T>
    Predicate1<ET> or(Predicate1<? super T> other) {
        return x -> this.apply(x) || other.apply(x);
    }

    default <ET extends T> Predicate1<ET>
    and(Predicate1<? super T> other) {
        return x -> this.apply(x) && other.apply(x);
    }

    default <ET extends T>
    Predicate1<ET> not() {
        return x -> !this.apply(x);
    }
}
