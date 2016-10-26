package com.xosmig.functional;

import com.xosmig.functional.LazyObject;

import java.util.AbstractList;

public class LazyList<T> extends AbstractList<T> {
    @Override
    public int size() {
        return 0; // TODO
    }

    @Override
    public T get(int index) {
        return null; // TODO
    }

    private static class Node<T> {
        LazyObject<T> val;
        LazyObject<Node<T>> next;

        public int size() {
            return 1 + (next == null ? 0 : next.value().size());
        }

        public T get(int index) {
            return (index == 0 ? val.value() : next.value().get(index - 1));
        }
    }
}
