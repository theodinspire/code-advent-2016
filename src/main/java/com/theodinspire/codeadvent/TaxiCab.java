package com.theodinspire.codeadvent;

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

    public static void main(String[] args) {
        TaxiCab taxi = new TaxiCab();

        System.out.println("Taxi is facing: " + taxi.getHeading());

        System.out.println("Taxi turns left.");
        taxi.turnLeft();
        System.out.println("Taxi is now facing: " + taxi.getHeading());

        System.out.println("Taxi turns right, twice.");
        taxi.turnRight();
        taxi.turnRight();
        System.out.println("Taxi is now facing: " + taxi.getHeading());
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
