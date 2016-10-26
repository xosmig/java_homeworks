package com.xosmig.functional;

import java.util.AbstractList;
import java.util.Iterator;

public class LazyList<T> extends AbstractList<T> {
    private static LazyList EMPTY = new LazyList(null, null);

    final T head;
    final Lazy<LazyList<T>> tail;

    private LazyList(T head, Lazy<LazyList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T>
    LazyList<T> empty() {
        return EMPTY;
    }

    public static <T>
    LazyList<T> cons(T head, Lazy<LazyList<T>> tail) {
        return new LazyList<>(head, tail);
    }

    @Override
    public int size() {
        return 1 + (tail == null ? 0 : tail.value().size());
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
            return node != null;
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
