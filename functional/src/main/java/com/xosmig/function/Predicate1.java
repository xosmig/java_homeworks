package com.xosmig.function;

public interface Predicate1<T> {
    Predicate1 ALWAYS_TRUE = x -> true;
    Predicate1 ALWAYS_FALSE = x -> false;

    /**
     * Returns predicate tht is always true.
     */
    static <U>
    Predicate1<U> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    /**
     * Returns predicate tht is always false.
     */
    static <U>
    Predicate1<U> alwaysFalse() {
        return ALWAYS_FALSE;
    }

    /**
     * Applies predicate to the given argument.
     */
    boolean apply(T x);

    /**
     * this :: T -> bool
     * other :: T -> bool
     * return :: T -> bool
     * return x = this x || other x
     */
    default <ET extends T>
    Predicate1<ET> or(Predicate1<? super T> other) {
        return x -> this.apply(x) || other.apply(x);
    }

    /**
     * this :: T -> bool
     * other :: T -> bool
     * return :: T -> bool
     * return x = this x && other x
     */
    default <ET extends T> Predicate1<ET>
    and(Predicate1<? super T> other) {
        return x -> this.apply(x) && other.apply(x);
    }

    /**
     * this :: T -> bool
     * return :: T -> bool
     * return x = not (this x)
     */
    default <ET extends T>
    Predicate1<ET> not() {
        return x -> !this.apply(x);
    }
}
