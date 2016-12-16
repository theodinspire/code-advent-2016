package com.theodinspire.codeadvent.WeekOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.Predicate;

/**
 * Created by ecormack on 12/14/2016.
 */
public class BathroomKeyPad {
    private static String[][] DEFAULT_PAD = {{ "1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};

    private String[][] pad = DEFAULT_PAD;
    private int horzIndex = 1;
    private int vertIndex = 1;

    private Predicate<int[]> moveCondition = p -> {
        return Math.abs(p[0] - 1) < 2 && Math.abs(p[1] - 1) < 2;
    };

    public void setPad(String[][] pad) {
        this.pad = pad;
    }

    public void setMoveCondition(Predicate<int[]> moveCondition) {
        this.moveCondition = moveCondition;
    }

    public void setHorzIndex(int horzIndex) { this.horzIndex = horzIndex; }
    public void setVertIndex(int vertIndex) { this.vertIndex = vertIndex; }

    public void setFinger(int x, int y) { setHorzIndex(x); setVertIndex(y); }
    public void setFinger(int[] a)      { setFinger(a[0], a[1]); }

    public BathroomKeyPad(int horzIndex, int vertIndex) {
        this.horzIndex = horzIndex;
        this.vertIndex = vertIndex;
    }
    public BathroomKeyPad() {}

    private void up()       { tryMove(horzIndex, vertIndex - 1); }
    private void down()     { tryMove(horzIndex, vertIndex + 1); }
    private void left()     { tryMove(horzIndex - 1, vertIndex); }
    private void right()    { tryMove(horzIndex + 1, vertIndex); }

    private void tryMove(int x, int y) {
        int[] tst = {x, y};
        if (moveCondition.test(tst)) { horzIndex = x; vertIndex = y; }
    }

    public String getDigit(String instructions) {
        for (char instruction : instructions.toUpperCase().toCharArray()) {
            switch (instruction) {
                case 'U': up();     break;
                case 'D': down();   break;
                case 'L': left();   break;
                case 'R': right();  break;
            }
        }

        return pad[vertIndex][horzIndex];
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
        System.out.println("9-digit, square pad:");
        System.out.println("Test key code is: " + pad.getCode("src/resources/WeekOne/BathroomKeyPad/test.txt"));
        pad.setFinger(1, 1);
        System.out.println("Actual key code is: " +
                pad.getCode("src/resources/WeekOne/BathroomKeyPad/instructions.txt"));

        System.out.println();

        BathroomKeyPad odd = new BathroomKeyPad(0, 2);
        String[][] padKeys = {{    "",  "", "1",  "",  ""},
                                {  "", "2", "3", "4",  ""},
                                { "5", "6", "7", "8", "9"},
                                {  "", "A", "B", "C",  ""},
                                {  "",  "", "D",  "",  ""}};
        odd.setPad(padKeys);
        odd.setMoveCondition(p -> {
            int x = Math.abs(p[0] - 2);
            int y = Math.abs(p[1] - 2);
            return x + y < 3;
        });

        System.out.println("13-digit, diamond pad:");
        System.out.println("Test key code is: " + odd.getCode("src/resources/WeekOne/BathroomKeyPad/test.txt"));
        odd.setFinger(0, 2);
        System.out.println("Actual key code is: " +
                odd.getCode("src/resources/WeekOne/BathroomKeyPad/instructions.txt"));
    }
}
