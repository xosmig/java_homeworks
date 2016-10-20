package com.xosmig.bst;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public interface MyTreeSet<E> extends Set<E> {

    /** {@link TreeSet#descendingIterator()} **/
    Iterator<E> descendingIterator();

    /** {@link TreeSet#descendingSet()} **/
    MyTreeSet<E> descendingSet();


    /** {@link TreeSet#first()} **/
    default E first() {
        return iterator().next();
    }

    /** {@link TreeSet#last()} **/
    default E last() {
        return descendingIterator().next();
    }


    /** {@link TreeSet#lower(E)} **/
    E lower(E e);

    /** {@link TreeSet#floor(E)} **/
    E floor(E e);


    /** {@link TreeSet#ceiling(E)} **/
    E ceiling(E e);

    /** {@link TreeSet#higher(E)} **/
    E higher(E e);
}
