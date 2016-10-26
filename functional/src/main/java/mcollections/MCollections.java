package mcollections;

import com.xosmig.functional.*;

import java.util.Iterator;

public final class MCollections {
    public static <T, R> LazyList<R> map(Function1<T, ? extends R> f, Iterable<? extends T> a) {
        return new MapList<T, R>(f, a.iterator());
    }

    public static <T> LazyList<T> filter(Predicate1<T> p, Iterable<? extends T> a) {
        return new FilterList<>(p, a.iterator());
    }

    public static <T> LazyList<T> takeWhile(Predicate1<T> p, Iterable<? extends T> a) {
        return new TakeWhileList<>(p, a.iterator());
    }

    public static <T> LazyList<T> takeUntil(Predicate1<T> p, Iterable<? extends T> a) {
        return takeWhile(p.not(), a);
    }

    public static <T, R> R foldr(Function2<T, R, ? extends R> f, R init, Iterable<? extends T> a) {
        return foldrImpl(f, init, a.iterator());
    }

    public static <T, R> R foldl(Function2<R, T, ? extends R> f, R init, Iterable<? extends T> a) {
        for (T x : a) {
            init = f.apply(init, x);
        }
        return init;
    }

    private static <T, R> R foldrImpl(Function2<T, R, ? extends R> f, R val, Iterator<? extends T> it) {
        if (it.hasNext()) {
            return f.apply(it.next(), val);
        } else {
            return val;
        }
    }
}
