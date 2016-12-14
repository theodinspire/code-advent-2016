package com.theodinspire.codeadvent;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ecormack on 12/13/2016.
 */
public class TaxiCab {
    private Heading heading;
    private int x = 0;  //  Distance moved to the East (negative for West)
    private int y = 0;  //  Distance moved to the North (negative for South)

    public TaxiCab(Heading heading) { this.heading = heading; }
    public TaxiCab() { heading = Heading.NORTH; }

    public Heading getHeading() { return heading; }
    public int getX() { return x; }
    public int getY() { return y; }

    public int getDistanceTraveled() {
        return Math.abs(x) + Math.abs(y);
    }

    public void turnRight() { heading = heading.turnRight(); }
    public void turnLeft()  { heading = heading.turnLeft(); }

    public void move(int dist) {
        switch (heading) {
            case NORTH: y += dist; break;
            case EAST:  x += dist; break;
            case SOUTH: y -= dist; break;
            case WEST:  x -= dist; break;
            default:               break;
        }
    }

    public void makeLeg(String command) {
        String turn = command.substring(0, 1).toLowerCase();

        switch (turn) {
            case "r":   heading = heading.turnRight();  break;
            case "l":   heading = heading.turnLeft();   break;
        }

        move(Integer.parseInt(command.substring(1)));
    }

    public static void main(String[] args) {
        TaxiCab taxi = new TaxiCab();

        String dir = "L5, R1, R3, L4, R3, R1, L3, L2, R3, L5, L1, L2, R5, L1, R5, R1, L4, R1, R3, L4, L1, R2, R5, R3, R1, R1, L1, R1, L1, L2, L1, R2, L5, L188, L4, R1, R4, L3, R47, R1, L1, R77, R5, L2, R1, L2, R4, L5, L1, R3, R187, L4, L3, L3, R2, L3, L5, L4, L4, R1, R5, L4, L3, L3, L3, L2, L5, R1, L2, R5, L3, L4, R4, L5, R3, R4, L2, L1, L4, R1, L3, R1, R3, L2, R1, R4, R5, L3, R5, R3, L3, R4, L2, L5, L1, L1, R3, R1, L4, R3, R3, L2, R5, R4, R1, R3, L4, R3, R3, L2, L4, L5, R1, L4, L5, R4, L2, L1, L3, L3, L5, R3, L4, L3, R5, R4, R2, L4, R2, R3, L3, R4, L1, L3, R2, R1, R5, L4, L5, L5, R4, L5, L2, L4, R4, R4, R1, L3, L2, L4, R3";

        List<String> directions = new LinkedList<>(Arrays.asList(dir.split(", ")));

//        try {
//            BufferedReader input = new BufferedReader(
//                    new InputStreamReader(new URL("http://adventofcode.com/2016/day/1/input").openStream()));
//
//            String line = null;
//            while ((line = input.readLine()) != null)
//                System.out.println(line);
//
//            //directions.addAll(Arrays.asList(input.readLine().split(", ")));
//        } catch (Exception e) {
//            System.out.println("Something went wrong");
//            e.printStackTrace();
//            System.exit(1);
//        }

        for (String direction : directions) {
            System.out.print(direction + ", ");
            taxi.makeLeg(direction.trim());
        }

        System.out.println();
        System.out.println("Easter Bunny Headquarters is " + taxi.getDistanceTraveled() +
            " blocks away");
    }

    private enum Heading {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public Heading turnRight() {
            switch (this) {
                case NORTH: return EAST;
                case EAST:  return SOUTH;
                case SOUTH: return WEST;
                case WEST:  return NORTH;
                default:    return this;
            }
        }

        public Heading turnLeft() {
            switch (this) {
                case NORTH: return WEST;
                case EAST:  return NORTH;
                case SOUTH: return EAST;
                case WEST:  return SOUTH;
                default:    return this;
            }
        }
    }
}
