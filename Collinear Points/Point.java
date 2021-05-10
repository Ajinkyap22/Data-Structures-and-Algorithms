import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point


    //  Initializes a new point.
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // Draws this point to standard draw.
    public void draw() {
        StdDraw.point(x, y);
    }


    // Draws the line segment between this point and the specified point

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     */
    public double slopeTo(Point that) {
        double slopeY = that.y - this.y;
        double slopeX = that.x - this.x;

        // If horizontal
        if (slopeY == 0) {
            return +0.0;
        }

        // If vertical
        if (slopeX == 0) {
            return Double.POSITIVE_INFINITY;
        }

        // If equal
        if (slopeY == slopeX) {
            return Double.NEGATIVE_INFINITY;
        }

        return (slopeY / slopeX);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        // If equal
        if (that.y == this.y) {
            return Integer.compare(this.x, that.x);
        }

        if (that.y > this.y) return -1;
        else return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {

        public int compare(Point p1, Point p2) {
            double slopeP1 = slopeTo(p1);
            double slopeP2 = slopeTo(p2);

            return Double.compare(slopeP1, slopeP2);
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    public static void main(String[] args) {
        Point x = new Point(1, 2);
        Point y = new Point(2, 1);
        Point z = new Point(2, 3);

        StdOut.println("Compare To : " + x.compareTo(y));
        StdOut.println("Slope To : " + z.slopeTo(x));
        StdOut.println("Slope To : " + z.slopeTo(y));

        StdOut.println("Slope Order : " + z.slopeOrder().compare(x, y));
    }
}
