package com.xosmig.bst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;

/**
 * An associative container that contains a sorted set of unique objects of type Key.
 */
public class SlowSet<T> extends AbstractSet<T> implements MyTreeSet<T> {
    private int size = 0;
    private Node root = null;
    private final Comparator<T> comp;

    /**
     * Constructs an empty set.
     */
    public SlowSet() {
        this((x, y) -> ((Comparable<T>)x).compareTo(y));
    }

    public SlowSet(Comparator<T> comp) {
        this.comp = comp;
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
            root = new Node(e, null, null, null);
            size = 1;
            return true;
        } else if (root.add(e)) {
            size++;
            return true;
        } else {
            return false;
        }
    }

    private static final class Ref<R> {
        public R obj;
        public Ref(R obj) {
            this.obj = obj;
        }
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        END,
    }

    private class Iter implements Iterator<T> {
        private Node node;
        private Direction dir = Direction.LEFT;

        Iter(Node node) {
            this.node = node;
        }

        private void updateDir() {
            if (dir == Direction.LEFT && node.left.obj == null) {
                dir = Direction.RIGHT;
            }
            if (dir == Direction.RIGHT && node.right.obj == null) {
                dir = Direction.UP;
            }
            if (dir == Direction.UP && node.prev == null) {
                dir = Direction.END;
            }
        }

        @Override
        public boolean hasNext() {
            updateDir();
            if
        }

        @Override
        public T next() {
            return null;
        }
    }

    private class Node {
        Ref<Node> left;
        Ref<Node> right;
        Node prev;
        T key;

        @Nullable
        Ref<Node> getChild(T val) {
            int comp = SlowSet.this.comp.compare(val, key);
            if (comp < 0) {
                return left;
            } else if (comp > 0) {
                return right;
            } else {
                return null;
            }
        }

        public Node(T key, Node left, Node right, Node prev) {
            this.key = key;
            this.left = new Ref<>(left);
            this.right = new Ref<>(right);
            this.prev = prev;
        }

        public boolean contains(@NotNull T val) {
            Ref<Node> next = getChild(val);

            return next == null || (next.obj != null && next.obj.contains(val));
        }

        public boolean add(@NotNull T val) {
            Ref<Node> next = getChild(val);
            if (next == null) {
                return false;
            } else {
                if (next.obj == null) {
                    next.obj = new Node(val);
                    return true;
                } else {
                    return next.obj.add(val);
                }
            }
        }
    }
}
