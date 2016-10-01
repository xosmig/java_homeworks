package com.xosmig.trie;

import java.util.HashMap;

public class Trie {
    private Node root = new Node();

    /**
     * Returns true if there was no such element in the trie.
     * Time = O(|element|).
     */
    boolean add(String element) {
        return root.add(element);
    }

    /**
     * Time = O(|element|)
     */
    boolean contains(String element) {
        Node node = find(element);
        return node != null && node.getMark();
    }

    /**
     * Returns true in an element was in the trie.
     * Time = O(|element|)
     */
    boolean remove(String element) {
        return root.remove(element);
    }

    /**
     * Time = constant
     */
    int size() {
        return root.size();
    }

    /**
     * Time = O(|prefix|)
     */
    int howManyStartsWithPrefix(String prefix) {
        Node node = find(prefix);
        if (node == null) {
            return 0;
        }
        return node.size();
    }

    private Node find(String element) {
        Node node = root;
        for (int i = 0; i < element.length() && node != null; i++) {
            node = node.go(element.charAt(i));
        }
        return node;
    }

    private class Node {
        private HashMap<Character, Node> next = new HashMap<>();
        private boolean mark = false;
        private int size = 0;

        public Node go(char ch) {
            return next.get(ch);
        }

        public int size() {
            return size;
        }

        public void setMark() {
            setMark(true);
        }

        public void setMark(boolean mark) {
            this.mark = mark;
        }

        public boolean getMark() {
            return mark;
        }

        public boolean isEmpty() {
            return !getMark() && next.isEmpty();
        }

        public boolean add(String str) {
            return add(str, 0);
        }

        public boolean add(String str, int index) {
            // the end of str:
            if (index == str.length()) {
                if (getMark()) {
                    return false;
                } else {
                    setMark();
                    size++;
                    return true;
                }
            }

            // internal position
            char edge = str.charAt(index);
            Node node = go(edge);
            if (node == null) {
                node = new Node();
                next.put(edge, node);
            }

            if (node.add(str, index + 1)) {
                size++;
                return true;
            } else {
                return false;
            }
        }

        public boolean remove(String str) {
            return remove(str, 0);
        }

        public boolean remove(String str, int index) {
            // the end of str:
            if (index == str.length()) {
                if (getMark()) {
                    setMark(false);
                    size--;
                    return true;
                } else {
                    return false;
                }
            }

            // internal position:
            char edge = str.charAt(index);
            Node node = go(edge);

            if (node == null || !node.remove(str, index + 1)) {
                return false;
            }
            if (node.isEmpty()) {
                next.remove(edge);
            }
            size--;

            return true;
        }
    }
}
