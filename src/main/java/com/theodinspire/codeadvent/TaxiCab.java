package com.theodinspire.codeadvent;

import java.util.*;

/**
 * Created by ecormack on 12/13/2016.
 *
 */
public class TaxiCab {
    private Heading heading;
    private int x = 0;  //  Distance moved to the East (negative for West)
    private int y = 0;  //  Distance moved to the North (negative for South)

    private StreetCorner firstCrossing;

    private Queue<StreetCorner> log = new LinkedList<>();

    public TaxiCab() { this(Heading.NORTH); }
    public TaxiCab(Heading heading) {
        this.heading = heading;
    }

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

        log.add(new StreetCorner(start));

        if (firstCrossing == null) {
            Iterator<StreetCorner> iter = log.iterator();

            StreetCorner a;
            StreetCorner b = iter.next();   //  iter will always have an initial next at this point
            List<StreetCorner> crossings = new LinkedList<>();

            Leg current = new Leg(start, end);

            while (iter.hasNext()) {
                a = b;
                b = iter.next();

                if (iter.hasNext()) {
                    Leg previous = new Leg(a, b);

                    StreetCorner crossing = current.getCrossing(previous);
                    if (crossing != null) crossings.add(crossing);
                }
            }

            crossings.sort(new CornerComparator(heading));

            if (!crossings.isEmpty()) firstCrossing = crossings.get(0);
        }
    }

    public StreetCorner getCurrentCorner() { return new StreetCorner(x, y); }

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

        Iterator<String> iter = directions.iterator();
        int count = 0;

        while (iter.hasNext() && !taxi.hasMadeLoop()) {
            String direction = iter.next();
            taxi.makeLeg(direction.trim());
            ++count;
        }

        System.out.println();
        System.out.println("Location after end of first page is: " + taxi.getCurrentCorner());
        System.out.println("Easter Bunny Headquarters is at: " + taxi.getFirstCrossing());
        System.out.println("Distance after end of first page is " + taxi.getDistanceTraveled() + " blocks away");
        System.out.println("Easter Bunny Headquarters is " + taxi.getFirstCrossing().getDistance() + " blocks away");
        System.out.println("Journey took this many legs: " + count);
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
        private int x1;
        private int x2;
        private int y1;
        private int y2;

        private int minX;
        private int maxX;
        private int minY;
        private int maxY;

        private Heading heading;

        public Leg(int x1, int x2, int y1, int y2) {
            this.x1 = x1;   this.x2 = x2;   this.y1 = y1;   this.y2 = y2;

            minX = Math.min(x1, x2);
            maxX = Math.max(x1, x2);
            minY = Math.min(y1, y2);
            maxY = Math.max(y1, y2);

            if (x1 == x2)
                heading = (y1 > y2 ? Heading.NORTH : Heading.SOUTH);
            else // if (y1 == y2)
                heading = (x1 > x2 ? Heading.EAST : Heading.WEST);
        }
        public Leg(int[] a, int[] b) { this(a[0], b[0], a[1], b[1]); }
        public Leg(StreetCorner a, StreetCorner b) { this(a.getX(), b.getX(), a.getY(), b.getY()); }

        @Override
        public String toString() {
            return String.format("{ X: (%3d -> %3d), Y: (%3d -> %3d) }", x1, x2, y1, y2);
        }

        public StreetCorner getCrossing(Leg that) {
            if (this.intersects(that)) {
                int greaterMinX = Math.max(this.minX, that.minX);
                int lesserMaxX  = Math.min(this.maxX, that.maxX);
                int greaterMinY = Math.max(this.minY, that.minY);
                int lesserMaxY  = Math.min(this.maxY, that.maxY);

                switch (heading) {
                    case NORTH: return new StreetCorner(this.x1, Math.min(greaterMinY, lesserMaxY));
                    case EAST:  return new StreetCorner(this.y1, Math.min(greaterMinX, lesserMaxX));
                    case SOUTH: return new StreetCorner(this.x1, Math.max(greaterMinY, lesserMaxY));
                    case WEST:  return new StreetCorner(this.y1, Math.max(greaterMinX, lesserMaxX));
                }
            }
            return null;
        }

        public boolean intersects(Leg that) {
            return yOverlap(that) && xOverlap(that);
        }

        private boolean yOverlap(Leg that) {
            return Math.max(this.minY, that.minY) <= Math.min(this.maxY, that.maxY);
        }

        private boolean xOverlap(Leg that) {
            return Math.max(this.minX, that.minX) <= Math.min(this.maxX, that.maxX);
        }
    }

    private class CornerComparator implements Comparator<StreetCorner> {
        Heading heading;

        public CornerComparator(Heading heading) { this.heading = heading; }

        @Override
        public int compare(StreetCorner o1, StreetCorner o2) {
            switch (heading) {
                case NORTH: return o1.getY() - o2.getY();
                case EAST:  return o1.getX() - o2.getX();
                case SOUTH: return o2.getY() - o1.getY();
                case WEST:  return o2.getX() - o1.getX();
                default:    return 0;
            }
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }
}
