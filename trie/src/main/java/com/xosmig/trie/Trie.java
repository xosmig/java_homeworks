package com.xosmig.trie;

import com.xosmig.serializable.MySuperSerializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Trie implements MySuperSerializable {
    private Node root = new Node();

    /**
     * Returns true if there was no such element in the trie.
     * Time = O(|element|).
     */
    public boolean add(String element) {
        return root.add(element);
    }

    /**
     * Time = O(|element|)
     */
    public boolean contains(String element) {
        Node node = find(element);
        return node != null && node.getMark();
    }

    /**
     * Returns true in an element was in the trie.
     * Time = O(|element|)
     */
    public boolean remove(String element) {
        return root.remove(element);
    }

    /**
     * Time = constant
     */
    public int size() {
        return root.size();
    }

    /**
     * Time = O(|prefix|)
     */
    public int howManyStartsWithPrefix(String prefix) {
        Node node = find(prefix);
        if (node == null) {
            return 0;
        }
        return node.size();
    }

    @Override
    public void serialize(OutputStream out) throws IOException {
        root.serialize(out);
    }

    @Override
    public void deserialize(InputStream in) throws IOException {
        root.deserialize(in);
    }

    private Node find(String element) {
        Node node = root;
        for (int i = 0; i < element.length() && node != null; i++) {
            node = node.go(element.charAt(i));
        }
        return node;
    }

    private class Node implements MySuperSerializable {
        private static final int BEGIN_CHILD = 2;
        private static final int END_MARKED = 1;
        private static final int END_NOT_MARKED = 0;

        private HashMap<Character, Node> next;
        private boolean mark;
        private int size;

        public Node() {
            initialize();
        }

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
            return addRecursive(str, 0);
        }

        public boolean remove(String str) {
            return removeRecursive(str, 0);
        }

        @Override
        public void serialize(OutputStream out) throws IOException {
            for (Map.Entry<Character, Node> entry : next.entrySet()) {
                out.write(BEGIN_CHILD);
                out.write(entry.getKey());
                entry.getValue().serialize(out);
            }
            out.write(getMark() ? END_MARKED : END_NOT_MARKED);
        }

        @Override
        public void deserialize(InputStream in) throws IOException {
            // restore the default state
            initialize();

            while (true) {
                int flag = in.read();

                // end of Node
                if (flag == END_MARKED) {
                    setMark();
                    size++;
                    break;
                }
                if (flag == END_NOT_MARKED) {
                    break;
                }

                // has a child
                if (flag != BEGIN_CHILD) {
                    throw new IllegalArgumentException();
                }

                int iEdge = in.read();
                if (iEdge > Character.MAX_VALUE) {
                    throw new IllegalArgumentException();
                }

                char edge = (char)iEdge;
                Node child = new Node();
                next.put(edge, child);
                child.deserialize(in);

                size += child.size();
            }
        }

        private void initialize() {
            next = new HashMap<>();
            mark = false;
            size = 0;
        }

        private boolean addRecursive(String str, int index) {
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

            if (node.addRecursive(str, index + 1)) {
                size++;
                return true;
            } else {
                return false;
            }
        }

        private boolean removeRecursive(String str, int index) {
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

            if (node == null || !node.removeRecursive(str, index + 1)) {
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
