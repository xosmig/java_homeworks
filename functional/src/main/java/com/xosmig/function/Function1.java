package com.xosmig.function;

import com.xosmig.functional.Lazy;

public interface Function1<T, R> {
    R apply(T param);

    default Lazy<R> lazy(T t) {
        return Lazy.expr(() -> this.apply(t));
    }

    default <G>
    Function1<G, R> compose(Function1<? super G, ? extends T> g) {
        return x -> this.apply(g.apply(x));
    }
}
