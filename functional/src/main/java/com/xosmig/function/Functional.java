package com.xosmig.function;

import java.util.Iterator;

public final class Functional {
    public static <T, R>
    LazyList<R> map(Function1<? super T, ? extends R> f, Iterable<? extends T> a) {
        return new MapList<T, R>(f, a.iterator());
    }

    public static <T>
    LazyList<T> filter(Predicate1<? super T> p, Iterable<? extends T> a) {
        return new FilterList<>(p, a.iterator());
    }

    public static <T>
    LazyList<T> takeWhile(Predicate1<? super T> p, Iterable<? extends T> a) {
        return new TakeWhileList<>(p, a.iterator());
    }

    public static <T>
    LazyList<T> takeUntil(Predicate1<? super T> p, Iterable<? extends T> a) {
        return takeWhile(p.not(), a);
    }

    public static <T, R>
    Lazy<R> foldr(Function2<? super T, ?super R, ? extends R> f, R init, Iterable<? extends T> a) {
        final Iterator<? extends T> it = a.iterator();
        return new Lazy<R>() {
            @Override
            public R get() {
                return foldrImpl();
            }

            private R foldrImpl() {
                if (it.hasNext()) {
                    T e = it.next();
                    return f.apply(e, foldrImpl());
                } else {
                    return init;
                }
            }
        };
    }

    public static <T, R>
    Lazy<R> foldl(Function2<?super R, ? super T, ? extends R> f, R init, Iterable<? extends T> a) {
        final Iterator<? extends T> it = a.iterator();
        return () -> {
            R val = init;
            while (it.hasNext()) {
                val = f.apply(val, it.next());
            }
            return val;
        };
    }
}
