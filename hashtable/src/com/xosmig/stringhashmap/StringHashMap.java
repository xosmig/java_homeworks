package com.xosmig.stringhashmap;

import java.util.ArrayList;

/**
 * Created by Andrey Tonkikh on 11.09.16.
 */
public class StringHashMap {
    private int size = 0;
    private ArrayList<List> data;

    public int size() {
        return size;
    }

    public int capacity() {
        return data.size();
    }

    public StringHashMap() {
        this(1);
    }

    public StringHashMap(int buckets) {
        buckets = Math.max(buckets, 1);
        data = new ArrayList<>(buckets);
        for (int i = 0; i < buckets; i++) {
            data.add(new List());
        }
    }

    private int getBucket(String key) {
        return key.hashCode() % data.size();
    }

    private List.Node find(String key) {
        return data.get(getBucket(key)).find(key);
    }

    public boolean contains(String key) {
        return find(key) != null;
    }

    public String get(String key) {
        List.Node node = find(key);
        if (node == null) {
            return null;
        }
        return node.value();
    }

    private void assign(StringHashMap other) {
        data = other.data;
        size = other.size;
    }

    private void extend() {
        StringHashMap newMap = new StringHashMap(2 * size);
        for (List list: data) {
            // FIXME: m.b. it's better to use foreach. But it takes more code.
            for (List.Node node = list.first(); node != null; node = node.next()) {
                newMap.push_unchecked(node.key(), node.value());
            }
        }
        assign(newMap);
    }

    private void push_unchecked(String key, String value) {
        data.get(getBucket(key)).push(key, value);
        size++;
    }

    public String put(String key, String value) {
        List.Node node = find(key);
        if (node == null) {
            if (size == data.size()) {
                extend();
            }
            push_unchecked(key, value);
            return null;
        } else {
            String ret = node.value();
            node.setValue(value);
            return ret;
        }
    }

    public String remove(String key) {
        List.Node node = data.get(getBucket(key)).remove(key);
        if (node != null) {
            size--;
            return node.value();
        }
        return null;
    }

    public void clear() {
        StringHashMap newMap = new StringHashMap();
        assign(newMap);
    }

    private static class List {
        private Node first = null;

        void push(String key, String value) {
            first = new Node(first, key, value);
        }

        Node first() {
            return first;
        }

        Node find(String key) {
            Node node = first;
            while (node != null && !node.key().equals(key)) {
                node = node.next();
            }
            return node;
        }

        Node remove(String key) {
            Node prev = null;
            Node node = first;
            while (node != null && !node.key().equals(key)) {
                prev = node;
                node = node.next();
            }
            if (node != null) {
                if (prev == null) {
                    first = node.next;
                } else {
                    prev.next = node.next;
                }
            }
            return node;
        }

        private static class Node {
            private Node next;
            private String key, value;

            Node(Node next, String key, String value) {
                this.next = next;
                this.key = key;
                this.value = value;
            }

            Node next() {
                return next;
            }

            String key() {
                return key;
            }

            String value() {
                return value;
            }

            void setValue(String value) {
                this.value = value;
            }
        }
    }
}
