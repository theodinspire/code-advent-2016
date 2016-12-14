package com.theodinspire.codeadvent;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ecormack on 12/14/2016.
 */
public class BathroomKeyPad {
    private static String[][] PAD = {{ "1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
    private static int MIN_INDEX = 0;
    private static int MAX_INDEX = 2;
    private static int DEFAULT_INDEX = 1;

    private int horzIndex;
    private int vertIndex;

    public BathroomKeyPad(int horzIndex, int vertIndex) {
        this.horzIndex = horzIndex;
        this.vertIndex = vertIndex;
    }
    public BathroomKeyPad() { this(DEFAULT_INDEX, DEFAULT_INDEX); }

    private void up()       { vertIndex = Math.max(vertIndex - 1, MIN_INDEX); }
    private void down()     { vertIndex = Math.min(vertIndex + 1, MAX_INDEX); }
    private void left()     { horzIndex = Math.max(horzIndex - 1, MIN_INDEX); }
    private void right()    { horzIndex = Math.min(horzIndex + 1, MAX_INDEX); }

    public void reset()     { horzIndex = DEFAULT_INDEX; vertIndex = DEFAULT_INDEX; }

    public String getDigit(String instructions) {
        for (char instruction : instructions.toUpperCase().toCharArray()) {
            switch (instruction) {
                case 'U': up();     break;
                case 'D': down();   break;
                case 'L': left();   break;
                case 'R': right();  break;
            }
        }

        return PAD[vertIndex][horzIndex];
    }

    public String getCode(String filepath) {
        StringBuilder stringy = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            while ((line = reader.readLine()) != null) {
                stringy.append(getDigit(line));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
            System.exit(1);
        }

        return stringy.toString();
    }

    public static void main(String[] args) {
        BathroomKeyPad pad = new BathroomKeyPad();
        System.out.println("Key code is: " + pad.getCode("src/resources/BathroomKeyPad/instructions.txt"));
    }
}
