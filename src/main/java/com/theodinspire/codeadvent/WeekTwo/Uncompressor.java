package com.theodinspire.codeadvent.WeekTwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eric on 18 Dec 2016.
 */
public class Uncompressor {
    public static String uncompress(String string) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);

            if (c == '(') {
                StringBuilder operator = new StringBuilder();
                for (i++; string.charAt(i) != ')'; i++)
                    operator.append(string.charAt(i));

                String[] numbers = operator.toString().split("x");
                int length = Integer.parseInt(numbers[0]) + i;
                int times = Integer.parseInt(numbers[1]);
                StringBuilder span = new StringBuilder();

                for (i++; i <= length; i++)
                    span.append(string.charAt(i));
                for (int j = 0; j < times; j++)
                    builder.append(span.toString());

                if (i < string.length())
                    c = string.charAt(i);
            }

            if (i < string.length())
                builder.append(c);
        }

        return builder.toString();
    }

    public static String fullyUncompress(String input) {
        String output = input;

        while (output.matches("[\\S]*\\(\\d+x\\d+\\)[\\S]*"))
            output = uncompress(output);

        return output;
    }

    public static void main(String args[]) {
        int count = 0;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("src/resources/WeekTwo/Uncompressor/input.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String output = fullyUncompress(line);

                System.out.println(String.format("%6d: %s", output.length(), (output.length() < 81 ? output : "")));
                count += output.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Decompressed length is: " + count);
    }
}
