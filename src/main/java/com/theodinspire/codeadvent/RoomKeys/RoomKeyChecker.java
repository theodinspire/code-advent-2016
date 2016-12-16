package com.theodinspire.codeadvent.RoomKeys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

/**
 * Created by ecormack on 12/15/2016.
 */
public class RoomKeyChecker {
    public static void main(String[] args) {
        int sum = 0;
        Stack<RoomKey> keys = new Stack<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("src/resources/RoomKeyChecker/input.txt"))) {
            String line;

            KeyChecker checker = new PartAChecker();

            while ((line = reader.readLine()) != null) {
                RoomKey key = new RoomKey(line);
                if (checker.validate(key)) {
                    sum += key.getSector();
                    keys.push(key);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong in the RoomKeyChecker");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(sum);
        System.out.println();

        int count = 0;

        for (RoomKey key : keys) {
            ++count;
            //System.out.println(decypherKey(key));
            String name = decypherKey(key);
            if (name.contains("north") || name.contains("pole")) {
                System.out.println(String.format("%4d. %s", ++count, name));
                if (count % 10 == 0)
                    try {System.in.read();} catch (Exception e) {System.out.println("Continuing");}
            }
        }
    }

    public static String decypherKey(RoomKey key) {
        StringBuilder cracked = new StringBuilder();
        int sector = key.getSector();
        int aShift = (int) 'a';

        for (char c : key.getKey().toCharArray()) {
            if (c == '-')
                cracked.append(" ");
            else if (c >= 'a' && c <= 'z') {
                cracked.append((char) (((((int) c) + (sector - aShift)) % 26) + aShift));
            }
        }

        cracked.append(": ").append(sector);

        return cracked.toString();
    }
}
