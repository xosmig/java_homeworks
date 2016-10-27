package com.xosmig.function;

import com.xosmig.functional.Lazy;

public interface Function2<T, U, R> {
    R apply(T t, U u);

    default Lazy<R> lazy(T t, U u) {
        return Lazy.expr(() -> this.apply(t, u));
    }

    default <ET extends T, EU extends U, V>
    Function2<ET, EU, V> compose(Function1<? super R, V> after) {
        return (x, y) -> after.apply(this.apply(x, y));
    }

    default <EU extends U>
    Function1<EU, R> bind1(T x) {
        return y -> this.apply(x, y);
    }

    default <ET extends T>
    Function1<ET, R> bind2(U y) {
        return x -> this.apply(x, y);
    }

    default <EU extends U>
    Function1<EU, R> curry(T x) {
        return bind1(x);
    }
}
