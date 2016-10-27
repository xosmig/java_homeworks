package com.xosmig.functional;

import java.util.AbstractList;
import java.util.Iterator;

/**
 * Immutable list which supports lazy computations.
 * Similar to [T] in Haskell.
 * Sometimes you may need `LazyList<Lazy<T>>`
 */
public final class LazyList<T> extends AbstractList<T> {
    private static final LazyList EMPTY = new LazyList(null, null);

    final private T head;
    final private Lazy<LazyList<T>> tail;

    private LazyList(T head, Lazy<LazyList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    /**
     * Returns an empty list. Similar to [] in Haskell.
     */
    public static <T>
    LazyList<T> empty() {
        return EMPTY;
    }

    /**
     * Returns new list with `head` on the first place and equal to `tail` from the second position.
     */
    public static <T>
    LazyList<T> cons(T head, Lazy<LazyList<T>> tail) {
        return new LazyList<>(head, tail);
    }

    @Override
    public int size() {
        return this == EMPTY ? 0 : 1 + tail.value().size();
    }

    @Override
    public T get(int index) {
        return (index == 0 ? head : tail.value().get(index - 1));
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter<>(this);
    }

    private static class Iter<T> implements Iterator<T> {
        LazyList<T> node;

        public Iter(LazyList<T> node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != EMPTY;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            T ret = node.head;
            node = Lazy.nullable(node.tail);
            return ret;
        }
    }
}
