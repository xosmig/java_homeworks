package com.xosmig.functional;

import com.xosmig.function.Function1;
import com.xosmig.function.Function2;
import com.xosmig.function.Predicate1;

import java.util.Iterator;

public final class Functional {
    public static <T, R>
    Lazy<LazyList<R>> map(Function1<? super T, ? extends R> f, Iterable<? extends T> a) {
        return foldr(
                (x, xs) -> LazyList.cons(f.apply(x), xs),
                LazyList.empty(),
                a
        );
    }

    public static <T>
    Lazy<LazyList<T>> filter(Predicate1<? super T> p, Iterable<? extends T> a) {
        return foldr(
                (x, xs) -> p.apply(x) ? LazyList.cons(x, xs) : xs.value(),
                LazyList.empty(),
                a
        );
    }

    public static <T>
    Lazy<LazyList<T>> takeWhile(Predicate1<? super T> p, Iterable<? extends T> a) {
        return foldr(
                (x, xs) -> p.apply(x) ? LazyList.cons(x, xs) : LazyList.empty(),
                LazyList.empty(),
                a
        );
    }

    public static <T>
    Lazy<LazyList<T>> takeUntil(Predicate1<? super T> p, Iterable<? extends T> a) {
        return takeWhile(p.not(), a);
    }

    public static <T, R>
    Lazy<R> foldr(Function2<? super T, Lazy<R>, R> f, R init, Iterable<? extends T> a) {
        return foldrImpl(f, init, a.iterator());
    }

    public static <T, R>
    Lazy<R> foldrImpl(Function2<? super T, Lazy<R>, R> f, R init, Iterator<? extends T> it) {
        if (it.hasNext()) {
            T e = it.next();  // mustn't be inlined!
            return f.lazy(e, foldrImpl(f, init, it));
        } else {
            return Lazy.valueOf(init);
        }
    }

    public static <T, R>
    Lazy<R> foldl(Function2<Lazy<R>, ? super T, R> f, R init, Iterable<? extends T> a) {
        return foldlImpl(f, Lazy.valueOf(init), a.iterator());
    }

    public static <T, R>
    Lazy<R> foldlImpl(Function2<Lazy<R>, ? super T, R> f, Lazy<R> val, Iterator<T> it) {
        if (it.hasNext()) {
            T e = it.next();  // mustn't be inlined!
            return Lazy.expr(() -> foldlImpl(f, f.lazy(val, e), it).value());
        } else {
            return val;
        }
    }
}


