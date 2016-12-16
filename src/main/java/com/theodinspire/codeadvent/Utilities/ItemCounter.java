package com.theodinspire.codeadvent.Utilities;

import java.util.*;

/**
 * Created by ecormack on 12/16/2016.
 */
public class ItemCounter<E> {
    private Map<E, CountedItem<E>> counts = new HashMap<>();

    public ItemCounter() {}
    public ItemCounter(E item) { add(item); }

    public void add(E item) {
        if (counts.containsKey(item))
            counts.get(item).incrementCount();
        else
            counts.put(item, new CountedItem<>(item));
    }

    public E getHighestCountedItem() {
        List<CountedItem<E>> items = new LinkedList<>(counts.values());
        Collections.sort(items);
        return items.get(0).getItem();
    }

    public E getLeastCountedItem() {
        List<CountedItem<E>> items = new LinkedList<>(counts.values());
        Collections.sort(items);
        Collections.reverse(items);
        return items.get(0).getItem();
    }

    private class CountedItem<E> implements Comparable<CountedItem<E>> {
        private E item;
        private int count;

        public CountedItem(E item) {
            this.item = item;
            count = 1;
        }

        public void incrementCount() { ++count; }
        public int getCount() { return count; }
        public E getItem() { return item; }

        @Override
        public int compareTo(CountedItem<E> o) {
            return o.getCount() - count;
        }
    }
}
