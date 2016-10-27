package com.xosmig.function;

import com.xosmig.functional.Lazy;

public interface Function2<T, U, R> {
    /**
     * Apply the function to the given arguments.
     */
    R apply(T t, U u);

    /**
     * Apply the function to the given arguments lazily.
     */
    default Lazy<R> lazy(T t, U u) {
        return Lazy.expr(() -> this.apply(t, u));
    }

    /**
     * Weird compose.
     * Gives function `after` :: R -> V
     * return :: T -> U -> V
     * return x y = after (this x y)
     */
    default <ET extends T, EU extends U, V>
    Function2<ET, EU, V> compose(Function1<? super R, V> after) {
        return (x, y) -> after.apply(this.apply(x, y));
    }

    /**
     * Bind the first argument.
     * Gives `t` :: T
     * return :: U -> R
     * return u = this t u
     */
    default <EU extends U>
    Function1<EU, R> bind1(T x) {
        return y -> this.apply(x, y);
    }

    /**
     * Bind the second argument
     * Gives `u` :: T
     * return :: T -> R
     * return t = this t u
     */
    default <ET extends T>
    Function1<ET, R> bind2(U y) {
        return x -> this.apply(x, y);
    }

    /**
     * Translating into function :: T -> (U -> R)
     * f.curry().apply(t).apply(u) is equivalent to f.apply(t, u).
     */
    default
    Function1<T, Function1<U, R>> curry() {
        return this::bind1;
    }
}
