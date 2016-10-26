package com.xosmig.functional;

public interface Function1<T, R> {
    R apply(T param);

    default <G, EG extends G> Function1<EG, R> compose(Function1<G, ? extends T> g) {
        return x -> this.apply(g.apply(x));
    }
}
