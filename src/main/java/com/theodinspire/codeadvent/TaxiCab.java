package com.theodinspire.codeadvent;

import javafx.collections.transformation.SortedList;

import java.util.*;

/**
 * Created by ecormack on 12/13/2016.
 */
public class TaxiCab {
    private Heading heading;
    private int x = 0;  //  Distance moved to the East (negative for West)
    private int y = 0;  //  Distance moved to the North (negative for South)

    private StreetCorner firstCrossing;

    private List<Leg> log = new LinkedList<>();

    public TaxiCab() { this(Heading.NORTH); }
    public TaxiCab(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading() { return heading; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean hasMadeLoop() { return firstCrossing != null; }
    public StreetCorner getFirstCrossing() { return firstCrossing; }

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
        int[] start = {x, y};

        String turn = command.substring(0, 1).toLowerCase();

        switch (turn) {
            case "r":   turnRight();  break;
            case "l":   turnLeft();   break;
        }

        move(Integer.parseInt(command.substring(1)));

        int[] end = {x, y};
        Leg leg = new Leg(start, end);

        if (firstCrossing == null) {
            LegComparator legComparator = new LegComparator(heading);
            log.sort(legComparator);
            Iterator<Leg> iter = log.iterator();

            while (iter.hasNext() && firstCrossing == null) {
                firstCrossing = leg.getCrossing(iter.next());
            }
        }

        log.add(leg);
    }

    public static void main(String[] args) {
        TaxiCab taxi = new TaxiCab();

        String dir = "L5, R1, R3, L4, R3, R1, L3, L2, R3, L5, L1, L2, R5, L1, R5, R1, L4, R1, R3, L4, L1, R2, R5, " +
                "R3, R1, R1, L1, R1, L1, L2, L1, R2, L5, L188, L4, R1, R4, L3, R47, R1, L1, R77, R5, L2, R1, L2, R4, " +
                "L5, L1, R3, R187, L4, L3, L3, R2, L3, L5, L4, L4, R1, R5, L4, L3, L3, L3, L2, L5, R1, L2, R5, L3, " +
                "L4, R4, L5, R3, R4, L2, L1, L4, R1, L3, R1, R3, L2, R1, R4, R5, L3, R5, R3, L3, R4, L2, L5, L1, L1, " +
                "R3, R1, L4, R3, R3, L2, R5, R4, R1, R3, L4, R3, R3, L2, L4, L5, R1, L4, L5, R4, L2, L1, L3, L3, L5, " +
                "R3, L4, L3, R5, R4, R2, L4, R2, R3, L3, R4, L1, L3, R2, R1, R5, L4, L5, L5, R4, L5, L2, L4, R4, R4, " +
                "R1, L3, L2, L4, R3";
//        String dir = "R8, r4, r4, r8";

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

        Iterator<String> iter = directions.iterator();
        int count = 0;

        while (iter.hasNext() && !taxi.hasMadeLoop()) {
            String direction = iter.next();
            taxi.makeLeg(direction.trim());

            System.out.print(String.format("[ %4d, %4d]", taxi.getX(), taxi.getY()));
            //if (count % 7 == 6) System.out.println();
            ++count;
        }

        System.out.println();
        System.out.println("Easter Bunny Headquarters is at: " + taxi.getFirstCrossing());
//        System.out.println("Easter Bunny Headquarters is " + taxi.getDistanceTraveled() +
//            " blocks away");
        System.out.println("Easter Bunny Headquarters is " + taxi.getFirstCrossing().getDistance() + " blocks away");
        System.out.println("Journey took this many legs: " + count);
        System.out.println("Taxi has made loop: " + taxi.hasMadeLoop());
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

    private class StreetCorner {
        private int x;
        private int y;

        public StreetCorner(int a, int b) { x = a; y = b; }
        public StreetCorner(int[] array) { this(array[0], array[1]); }

        public int getX() { return x; }
        public int getY() { return y; }

        public int getDistance(int a, int b)        { return Math.abs(x - a) + Math.abs(y - b); }
        public int getDistance(StreetCorner that)   { return getDistance(that.getX(), that.getY()); }
        public int getDistance()                    { return getDistance(0, 0); }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StreetCorner that = (StreetCorner) o;

            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return String.format("[ %4d, %4d]", x, y);
        }
    }

    private class Leg {
        private int minX;
        private int maxX;
        private int minY;
        private int maxY;

        private boolean northSouth;

        public Leg(int x1, int x2, int y1, int y2) {
            minX = Math.min(x1, x2);
            maxX = Math.max(x1, x2);
            minY = Math.min(y1, y2);
            maxY = Math.max(y1, y2);

            northSouth = x1 == x2;
        }
        public Leg(int[] a, int[] b) { this(a[0], b[0], a[1], b[1]); }
        public Leg(StreetCorner a, StreetCorner b) { this(a.getX(), b.getX(), a.getY(), b.getY()); }

        public boolean crosses(Leg that) {
            //  Assuming N-S or E-W directions
            if (this.northSouth) {
                return this.minX > that.minX && this.maxX < that.maxX &&
                        this.minY < that.minY && this.maxY > that.minY;
            } else if (that.northSouth){
                return this.minX < that.minX && this.maxX > that.maxX &&
                        this.minY > that.minY && this.maxY < that.minY;
            } else {
                return false;
            }
        }

        public StreetCorner getCrossing(Leg that) {
            if (this.crosses(that)) {
                if  (this.northSouth)   return new StreetCorner(this.minX, that.minY);
                else                    return new StreetCorner(that.minX, this.minY);
            } else return null;
        }
    }

    private class LegComparator implements Comparator<Leg> {
        Heading heading;

        public LegComparator(Heading heading) { this.heading = heading; }

        @Override
        public int compare(Leg o1, Leg o2) {
            switch (heading) {
                case NORTH: return o1.minY - o2.minY;
                case EAST:  return o1.minX - o2.minX;
                case SOUTH: return o2.maxY - o1.maxY;
                case WEST:  return o2.maxX - o1.maxX;
                default:    return 0;
            }
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }
}
