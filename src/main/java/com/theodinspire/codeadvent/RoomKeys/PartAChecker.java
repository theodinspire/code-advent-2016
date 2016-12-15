package com.theodinspire.codeadvent.RoomKeys;

import java.util.*;

/**
 * Created by ecormack on 12/15/2016.
 */
public class PartAChecker implements KeyChecker {
    Map<Character, LetterOrderCount> map = new HashMap<>();

    @Override
    public boolean validate(RoomKey key) {
        map.clear();
        generateMap(key.getKey().replace("-", ""));

        String checksum = key.getChecksum();
        //if (checksum.length() != 5) return false;
        int[] topFiveCounts = getTopFiveCounts();
        //if (topFiveCounts == null) return false;

        Character prev = key.getChecksum().charAt(0);
        if (!map.containsKey(prev) || map.get(prev).getCount() != topFiveCounts[0]) return false;

        for (int i = 1; i < 5; ++i) {
            Character current = key.getChecksum().charAt(i);
            if (!map.containsKey(current))
                return false;

            if (map.get(current).getCount() != topFiveCounts[i])
                return false;
//            else if (map.get(current).getOrder() < map.get(prev).getOrder())
//                return false;

            prev = current;
        }

        return true;
    }

    private void generateMap(String key) {
        for (int i = 0; i < key.length(); ++i) {
            char c = (key.charAt(i));

            if (map.containsKey(c)) {
                map.get(c).increment();
            } else {
                map.put(c, new LetterOrderCount(c, i));
            }
        }
    }

    private int[] getTopFiveCounts() {
        if (map.size() < 5) return null;
        List<LetterOrderCount> list = new LinkedList<>(map.values());
        list.sort(Comparator.comparingInt(LetterOrderCount::getCount).reversed());

        Iterator<LetterOrderCount> iter = list.iterator();
        int[] counts = {0, 0, 0, 0, 0};

        for (int i = 0; i < 5; ++i) {
            LetterOrderCount item = iter.next();
            counts[i] = item.getCount();
        }

        return counts;
    }

    private static class LetterOrderCount {
        private char letter;
        private int order;
        private int count = 1;

        public LetterOrderCount(char letter, int order) { this.letter = letter; this.order = order; }

        public char getLetter() { return letter; }
        public int  getOrder()  { return order; }
        public int  getCount()  { return count; }

        public void increment() { ++count; }
    }
}
