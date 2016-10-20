package com.xosmig.hashmultiset;

import com.xosmig.multiset.Multiset;

import java.util.*;

public class HashMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
    LinkedHashMap<E, Integer> data;
    int size;

    public HashMultiset() {
        data = new LinkedHashMap<>();
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        return data.containsKey(o);
    }

    @Override
    public int count(Object element) {
        Integer cnt = data.get(element);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public Set<E> elementSet() {
        return data.keySet();
    }

    @Override
    public Set<Entry<E>> entrySet() {
        return new MyEntrySet(data.entrySet());
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator(data.keySet().iterator());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        Integer count = data.get(e);
        data.put(e, (count == null ? 0 : count) + 1);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (data.containsKey(o)) {
            E element = (E)o;

            int count = data.get(element);
            if (count == 1) {
                data.remove(element);
            } else {
                data.put(element, count - 1);
            }

            size--;
            return true;
        } else {
            return false;
        }
    }

    public static class MyEntry<E> implements Entry<E> {
        private E element;
        private int count;

        public MyEntry(E element, int count) {
            this.element = element;
            this.count = count;
        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public int getCount() {
            return count;
        }
    }

    public class MyEntrySet extends AbstractSet<Entry<E>> {
        private Set<Map.Entry<E, Integer>> data;

        public MyEntrySet(Set<Map.Entry<E, Integer>> data) {
            this.data = data;
        }

        @Override
        public Iterator<Entry<E>> iterator() {
            return new MyIterator(data.iterator());
        }

        @Override
        public int size() {
            return data.size();
        }

        public class MyIterator implements Iterator<Entry<E>> {
            Iterator<Map.Entry<E, Integer>> it;
            int lastSize = 0;

            public MyIterator(Iterator<Map.Entry<E, Integer>> it) {
                this.it = it;
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public MyEntry<E> next() {
                Map.Entry<E, Integer> entry = it.next();
                lastSize = entry.getValue();
                return new MyEntry<>(entry.getKey(), entry.getValue());
            }

            @Override
            public void remove() {
                HashMultiset.this.size -= lastSize;
                it.remove();
            }
        }
    }

    public class MyIterator implements Iterator<E> {
        Iterator<E> it;
        E element;
        int num;

        public MyIterator(Iterator<E> it) {
            this.it = it;
            num = 0;
            element = null;
        }

        @Override
        public void remove() {
            HashMultiset.this.remove(element);
        }

        @Override
        public boolean hasNext() {
            return it.hasNext() || num + 1 < max();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }
            if (num + 1 >= max()) {
                element = it.next();
                num = 0;
            } else {
                num++;
            }
            return element;
        }

        private int max() {
            if (element == null) {
                return 0;
            } else {
                return HashMultiset.this.data.get(element);
            }
        }
    }
}


