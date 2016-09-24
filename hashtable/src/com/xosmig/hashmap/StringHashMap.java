package com.xosmig.hashmap;

import java.util.ArrayList;

/**
 * @author Andrey Tonkikh
 * StringHashMap is an associative container that contains string key-value pairs with unique keys.
 * Search, insertion, and removal of elements have average constant-time complexity.
 */
public class StringHashMap {
    private int size;
    private ArrayList<List> data;

    /**
     * Returns the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Returns the number of elements that can be held in currently allocated storage.
     */
    public int capacity() {
        return data.size();
    }

    /**
     * Constructs an empty StringHashMap with minimum capacity.
     */
    public StringHashMap() {
        this(0);
    }

    private void ini(int capacity) {
        size = 0;
        capacity = Math.max(capacity, 1);
        data = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            data.add(new List());
        }
    }

    /**
     * Constructs an empty StringHashMap with specified capacity.
     * @param capacity - an initial capacity.
     */
    public StringHashMap(int capacity) {
        ini(capacity);
    }

    private int getBucket(String key) {
        int ret = key.hashCode() % data.size();
        if (ret < 0) {
            ret += data.size();
        }
        return ret;
    }

    private List.Node find(String key) {
        return data.get(getBucket(key)).find(key);
    }

    /**
     * Returns true if the map contains a value for the specified key.
     */
    public boolean contains(String key) {
        return find(key) != null;
    }

    /**
     * Returns the value corresponding to the key.
     */
    public String get(String key) {
        List.Node node = find(key);
        if (node == null) {
            return null;
        }
        return node.getValue();
    }

    private void assign(StringHashMap other) {
        data = other.data;
        size = other.size;
    }

    /**
     * Reallocates memory. Makes capacity() := size() * 2.
     * Time = O(capacity() + size())
     */
    private void extend() {
        StringHashMap newMap = new StringHashMap(2 * size());
        for (List list: data) {
            // FIXME: m.b. it's better to use foreach. But it takes more code.
            for (List.Node node = list.first(); node != null; node = node.getNext()) {
                newMap.pushUnchecked(node.getKey(), node.getValue());
            }
        }
        assign(newMap);
    }

    /**
     * pushes the given key-value.
     * doesn't check if this key is already in the map
     * or if the map is full.
     */
    private void pushUnchecked(String key, String value) {
        data.get(getBucket(key)).push(key, value);
        size++;
    }

    /**
     * Inserts a key-value pair into the map or changes the value for the key if it is already in the map.
     * Returns an old value if the given key was previously in the map. null otherwise.
     */
    public String put(String key, String value) {
        List.Node node = find(key);
        if (node == null) {
            if (size == data.size()) {
                extend();
            }
            pushUnchecked(key, value);
            return null;
        } else {
            String ret = node.getValue();
            node.setValue(value);
            return ret;
        }
    }

    /**
     * Removes a key from the map.
     * Returns the value at the key if the key was previously in the map.
     */
    public String remove(String key) {
        List.Node node = data.get(getBucket(key)).remove(key);
        if (node != null) {
            size--;
            return node.getValue();
        }
        return null;
    }

    /**
     * Clears the map, removing all key-value pairs.
     */
    public void clear() {
        ini(0);
    }

    private static class List {
        private Node first = null;

        public void push(String key, String value) {
            first = new Node(first, key, value);
        }

        public Node first() {
            return first;
        }

        public Node find(String key) {
            Node node = first;
            while (node != null && !node.getKey().equals(key)) {
                node = node.getNext();
            }
            return node;
        }

        public Node remove(String key) {
            Node prev = null;
            Node node = first;
            while (node != null && !node.getKey().equals(key)) {
                prev = node;
                node = node.getNext();
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
            private String key;
            private String value;

            public Node(Node next, String key, String value) {
                this.next = next;
                this.key = key;
                this.value = value;
            }

            public Node getNext() {
                return next;
            }

            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
