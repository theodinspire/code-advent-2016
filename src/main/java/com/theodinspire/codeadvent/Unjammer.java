package com.theodinspire.codeadvent;

import com.theodinspire.codeadvent.Utilities.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecormack on 12/16/2016.
 */
public class Unjammer {
    public static void main(String[] args) {
        List<ItemCounter<Character>> counters = new ArrayList<>(8);

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("src/resources/WeekOne/Unjammer/input.txt"))) {
            String line = reader.readLine();

            for (char c : line.toCharArray())
                counters.add(new ItemCounter<>(c));

            while ((line = reader.readLine()) != null) {
                char[] characters = line.toCharArray();
                for (int i = 0; i < counters.size(); ++i)
                    counters.get(i).add(characters[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("IO error");
            e.printStackTrace();
            System.exit(2);
        }

        for (ItemCounter counter : counters)
            System.out.print(counter.getHighestCountedItem());
        System.out.println();

        for (ItemCounter counter : counters)
            System.out.print(counter.getLeastCountedItem());
    }
}
