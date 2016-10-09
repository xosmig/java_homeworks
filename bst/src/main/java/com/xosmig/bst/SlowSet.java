package com.xosmig.bst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An associative container that contains a sorted set of unique objects of type Key.
 */
public class SlowSet<T extends Comparable<T>> {
    private int size;
    private Node<T> root;

    /**
     * Constructs an empty set.
     */
    public SlowSet() {
        size = 0;
        root = null;
    }

    /**
     * The number of elements.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if element is present in the map.
     */
    public boolean contains(@NotNull Object o) {
        return root != null && root.contains((T)o);
    }

    /**
     * Adds element to the map. Returns true if there was no such an element.
     */
    public boolean add(T e) {
        if (root == null) {
            root = new Node<>(e);
            size = 1;
            return true;
        } else if (root.add(e)) {
            size++;
            return true;
        } else {
            return false;
        }
    }

    private static class Node<T extends Comparable<T>> {
        private static final class Ref<R> {
            public R obj;
            public Ref(R obj) {
                this.obj = obj;
            }
        }

        Ref<Node<T>> left;
        Ref<Node<T>> right;
        T key;

        @Nullable
        Ref<Node<T>> getChild(T val) {
            int comp = val.compareTo(key);
            if (comp < 0) {
                return left;
            } else if (comp > 0) {
                return right;
            } else {
                return null;
            }
        }

        public Node(T key) {
            this(key, null, null);
        }

        public Node(T key, Node<T> left, Node<T> right) {
            this.key = key;
            this.left = new Ref<>(left);
            this.right= new Ref<>(right);
        }

        public boolean contains(@NotNull T val) {
            Ref<Node<T>> next = getChild(val);
            if (next == null) {
                return true;
            } else {
                return next.obj != null && next.obj.contains(val);
            }
        }

        public boolean add(@NotNull T val) {
            Ref<Node<T>> next = getChild(val);
            if (next == null) {
                return false;
            } else {
                if (next.obj == null) {
                    next.obj = new Node<>(val);
                    return true;
                } else {
                    return next.obj.add(val);
                }
            }
        }
    }
}
