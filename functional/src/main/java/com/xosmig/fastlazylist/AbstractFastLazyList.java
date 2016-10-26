package com.xosmig.fastlazylist;

import java.util.*;

/**
 *
 */
public abstract class AbstractFastLazyList<T, R> extends AbstractList<R> implements FastLazyList<R> {
    private List<R> realList = new LinkedList<>();
    protected Iterator<? extends T> it;

    public AbstractFastLazyList(Iterator<? extends T> it) {
        this.it = it;
    }

    @Override
    public int size() {
        takeAll();
        return realList.size();
    }

    @Override
    public R get(int index) {
        takeTo(index);
        return realList.get(index);
    }

    @Override
    public R set(int index, R element) {
        takeTo(index);
        return realList.set(index, element);
    }

    @Override
    public void takeTo(int index) {
        while (realList.size() < index + 1) {
            Optional<R> e = getNext();
            if (e.isPresent()) {
                realList.add(e.get());
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    @Override
    public void takeAll() {
        while (true) {
            Optional<R> x = getNext();
            if (x.isPresent()) {
                realList.add(x.get());
            } else {
                break;
            }
        }
    }

    public abstract Optional<R> getNext();
}

