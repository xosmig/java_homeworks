package com.xosmig.function;

import com.xosmig.functional.Lazy;

/**
 * Wrapper around function :: T -> R
 */
public interface Function1<T, R> {
    /**
     * Apply the function to the given argument.
     */
    R apply(T t);

    /**
     * Apply the function to the given argument lazily.
     */
    default Lazy<R> lazy(T t) {
        return Lazy.expr(() -> this.apply(t));
    }

    /**
     * Compose the function (T -> R) with another one (G -> T).
     * Return a function :: G -> R.
     */
    default <G>
    Function1<G, R> compose(Function1<? super G, ? extends T> g) {
        return x -> this.apply(g.apply(x));
    }
}
