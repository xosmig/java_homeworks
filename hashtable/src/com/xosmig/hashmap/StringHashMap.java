package com.xosmig.hashmap;

import java.util.ArrayList;

/**
 * Created by Andrey Tonkikh on 11.09.16.
 */
public class StringHashMap {
    private int size;
    private ArrayList<List> data;

    public int size() {
        return size;
    }

    public int capacity() {
        return data.size();
    }

    public StringHashMap() {
        this(0);
    }

    private void ini(int buckets) {
        size = 0;
        buckets = Math.max(buckets, 1);
        data = new ArrayList<>(buckets);
        for (int i = 0; i < buckets; i++) {
            data.add(new List());
        }
    }

    public StringHashMap(int buckets) {
        ini(buckets);
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

    public boolean contains(String key) {
        return find(key) != null;
    }

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

    private void extend() {
        StringHashMap newMap = new StringHashMap(2 * size);
        for (List list: data) {
            // FIXME: m.b. it's better to use foreach. But it takes more code.
            for (List.Node node = list.first(); node != null; node = node.getNext()) {
                newMap.pushUnchecked(node.getKey(), node.getValue());
            }
        }
        assign(newMap);
    }

    private void pushUnchecked(String key, String value) {
        data.get(getBucket(key)).push(key, value);
        size++;
    }

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

    public String remove(String key) {
        List.Node node = data.get(getBucket(key)).remove(key);
        if (node != null) {
            size--;
            return node.getValue();
        }
        return null;
    }

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
