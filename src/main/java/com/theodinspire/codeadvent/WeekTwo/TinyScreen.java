package com.theodinspire.codeadvent.WeekTwo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eric on 17 Dec 2016.
 */
public class TinyScreen {
    static final int ROWS = 6;
    static final int COLS = 50;
    static final String ON  = "#";
    static final String OFF = " ";

    boolean[][] screen = new boolean[ROWS][COLS];

    public TinyScreen() {}
    public TinyScreen(String filename) {
        this();
        runInstructions(filename);
    }

    public void rect(int cols, int rows) {
        for (int x = 0; x < rows; ++x)
            for (int y = 0; y < cols; ++y)
                screen[x][y] = true;
    }

    public void rotateRow(int row, int offset) {
        boolean[] tmp = new boolean[COLS];
        for (int j = 0; j < COLS; ++j)
            tmp[j] = screen[row][j];
        for (int j = 0; j < COLS; ++j)
            screen[row][(j + offset) % COLS] = tmp[j];
    }

    public void rotateCol(int col, int offset) {
        boolean[] tmp = new boolean[ROWS];
        for (int i = 0; i < ROWS; ++i)
            tmp[i] = screen[i][col];
        for (int i = 0; i < ROWS; ++i)
            screen[(i + offset) % ROWS][col] = tmp[i];
    }

    public void parse(String instruction) {
        if (instruction.startsWith("rect")) {
            Matcher matcher = Pattern.compile("\\d+x\\d+").matcher(instruction);
            if (matcher.find()) {
                String[] numbers = matcher.group().split("x");
                rect(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            }
        } else if (instruction.startsWith("rotate")) {
            Pattern digitPattern = Pattern.compile("\\d+");
            Pattern offPattern = Pattern.compile("by \\d+");
            Pattern xPattern = Pattern.compile("column x=\\d+");
            Pattern yPattern = Pattern.compile("row y=\\d+");

            Matcher xMatcher = xPattern.matcher(instruction);
            Matcher yMatcher = yPattern.matcher(instruction);

            Matcher tmpMatcher = offPattern.matcher(instruction);
            tmpMatcher.find();

            Matcher offMatcher = digitPattern.matcher(tmpMatcher.group());
            offMatcher.find();

            int offset = Integer.parseInt(offMatcher.group());

            if (xMatcher.find()) {
                Matcher colMatcher = digitPattern.matcher(xMatcher.group());
                colMatcher.find();
                int col = Integer.parseInt(colMatcher.group());

                rotateCol(col, offset);
            } else if (yMatcher.find()) {
                Matcher rowMatcher = digitPattern.matcher(yMatcher.group());
                rowMatcher.find();
                int row = Integer.parseInt(rowMatcher.group());

                rotateRow(row, offset);
            }
        }
    }

    public void runInstructions(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parse(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public int countOnBits() {
        int count = 0;
        for (int i = 0; i < ROWS; ++i)
            for (int j = 0; j < COLS; ++j)
                if (screen[i][j]) ++count;
        return count;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = -1; row <= ROWS; ++row) {
            for (int col = -1; col <= COLS; ++ col) {
                if ((row == -1 || row == ROWS) && (col == -1 || col == COLS))
                    builder.append("+");
                else if (row == -1 || row == ROWS)
                    builder.append("-");
                else if (col == -1 || col == COLS)
                    builder.append("|");
                else
                    builder.append(screen[row][col] ? ON : OFF);
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        TinyScreen test = new TinyScreen("src/resources/WeekTwo/TinyScreen/test.txt");
        System.out.println("Test: \n" + test);

        TinyScreen it = new TinyScreen("src/resources/WeekTwo/TinyScreen/input.txt");
        System.out.print("Final: \n" + it);
        System.out.print("Bits turned on: " + it.countOnBits());
    }
}
