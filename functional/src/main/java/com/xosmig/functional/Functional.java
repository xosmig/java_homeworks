package com.xosmig.functional;

import com.xosmig.function.Function1;
import com.xosmig.function.Function2;
import com.xosmig.function.Predicate1;

import java.util.Iterator;
import java.util.function.Supplier;

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
    Lazy<R> foldr(Function2<? super T, Lazy<R>, ? extends R> f, R init, Iterable<? extends T> a) {
        final Iterator<? extends T> it = a.iterator();
        Supplier<Lazy<R>> foldrImpl = new Supplier<Lazy<R>>() {
            @Override
            public Lazy<R> get() {
                if (it.hasNext()) {
                    T e = it.next();
                    return Lazy.expr(() -> f.apply(e, this.get()));
                } else {
                    return Lazy.valueOf(init);
                }
            }
        };
        return foldrImpl.get();
    }

//    public static <T, R>
//    Lazy<R> foldl(Function2<?super R, ? super T, ? extends R> f, R init, Iterable<? extends T> a) {
//        final Iterator<? extends T> it = a.iterator();
//        return new LazyObject<>(() -> {
//            R val = init;
//            while (it.hasNext()) {
//                val = f.apply(val, it.next());
//            }
//            return val;
//        });
//    }
}


