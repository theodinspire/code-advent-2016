package com.theodinspire.codeadvent.RoomKeys;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ecormack on 12/15/2016.
 */
public class RoomKeyChecker {
    public static void main(String[] args) {
        int sum = 0;
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("src/resources/RoomKeyChecker/input.txt"))) {
            String line;

            KeyChecker checker = new PartAChecker();

            while ((line = reader.readLine()) != null) {
                RoomKey key = new RoomKey(line);
                boolean isTrue = checker.validate(key);
                //System.out.println(isTrue);
                sum += (isTrue ?key.getSector() : 0);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong in the RoomKeyChecker");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(sum);
    }
}
