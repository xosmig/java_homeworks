package com.xosmig.fastfunctional;

import com.xosmig.function.Predicate1;
import com.xosmig.function.Function1;
import com.xosmig.function.Function2;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

public final class FastFunctional {
    public static <T, R>
    FastLazyList<R> map(Function1<? super T, ? extends R> f, Iterable<? extends T> a) {
        return new FastMapList<T, R>(f, a.iterator());
    }

    public static <T>
    FastLazyList<T> filter(Predicate1<? super T> p, Iterable<? extends T> a) {
        return new FastFilterList<>(p, a.iterator());
    }

    public static <T>
    FastLazyList<T> takeWhile(Predicate1<? super T> p, Iterable<? extends T> a) {
        return new FastTakeWhileList<>(p, a.iterator());
    }

    public static <T>
    FastLazyList<T> takeUntil(Predicate1<? super T> p, Iterable<? extends T> a) {
        return takeWhile(p.not(), a);
    }

    public static <T, R>
    FastLazy<R> foldr(Function2<? super T, ?super R, ? extends R> f, R init, Iterable<? extends T> a) {
        final Iterator<? extends T> it = a.iterator();
        return new FastLazy<R>() {
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
    FastLazy<R> foldl(Function2<?super R, ? super T, ? extends R> f, R init, Iterable<? extends T> a) {
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


/*package*/ class FastMapList<T, R> extends AbstractFastLazyList<T, R> {
    private Function1<? super T, ? extends R> f;

    public FastMapList(Function1<? super T, ? extends R> f, Iterator<? extends T> it) {
        super(it);
        this.f = f;
    }

    @Override
    public Optional<R> getNext() {
        if (it.hasNext()) {
            return Optional.of(f.apply(it.next()));
        } else {
            return Optional.empty();
        }
    }
}


/*package*/ class FastFilterList<T> extends AbstractFastLazyList<T, T> {
    private Predicate1<? super T> p;

    public FastFilterList(Predicate1<? super T> p, Iterator<? extends T> it) {
        super(it);
        this.p = p;
    }

    @Override
    public Optional<T> getNext() {
        while (it.hasNext()) {
            T ret = it.next();
            if (p.apply(ret)) {
                return Optional.of(ret);
            }
        }
        return Optional.empty();
    }
}

/*package*/ class FastTakeWhileList<T> extends AbstractFastLazyList<T, T> {
    private Predicate1<?super T> p;

    public FastTakeWhileList(Predicate1<?super T> p, Iterator<? extends T> it) {
        super(it);
        this.p = p;
    }

    @Override
    public Optional<T> getNext() {
        if (it.hasNext()) {
            T x = it.next();
            if (p.apply(x)) {
                it = Collections.emptyIterator();
                return Optional.empty();
            }
            return Optional.of(x);
        } else {
            return Optional.empty();
        }
    }
}
