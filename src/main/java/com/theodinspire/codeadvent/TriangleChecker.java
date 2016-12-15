package com.theodinspire.codeadvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.rmi.server.ExportException;
import java.util.*;

/**
 * Created by Eric on 14 Dec 2016.
 */
public class TriangleChecker {
    private static int[] parseString(String input) {
        int[] numbers = {0, 0, 0};
        String[] sNumbers = input.trim().split("\\s+");

        for (int i = 0; i < 3; ++i) numbers[i] = Integer.parseInt(sNumbers[i]);

        Arrays.sort(numbers);

        return numbers;
    }

    private static boolean checkTrio(int[] trio) {
        return trio[0] + trio[1] > trio[2];
    }

    private static boolean parseAndCheck(String input) {
        return checkTrio(parseString(input));
    }

    public static int batchParse(String filepath) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                count += (parseAndCheck(line) ? 1 : 0);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
            System.exit(1);
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(String.format("Input has %d valid triangles",
                batchParse("src/resources/TriangleChecker/input.txt")));
    }
}
