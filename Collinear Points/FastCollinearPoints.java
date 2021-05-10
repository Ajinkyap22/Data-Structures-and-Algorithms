import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        // Clone to avoid mutation
        Point[] clones = points.clone();
        LineSegment[] lineSegments = new LineSegment[clones.length * clones.length];
        int count = 0;
        Point origin;
        double slope;

        Arrays.sort(clones);

        Point[] fixed = clones.clone();

        // Check for duplication
        for (int i = 0; i < clones.length - 1; i++) {
            if (clones[i].equals(clones[i + 1])) throw new IllegalArgumentException();
        }
        for (int i = 0; i < clones.length; i++) {
            origin = fixed[i];

            Arrays.sort(clones);
            Arrays.sort(clones, origin.slopeOrder());

            int j = 0;
            int extraPoints;

            while (j < clones.length - 2) {
                slope = origin.slopeTo(clones[j]);
                extraPoints = 1;

                while (j + extraPoints < clones.length && slope == origin
                        .slopeTo(clones[j + extraPoints])) {
                    extraPoints++;
                }
                extraPoints--;

                if (extraPoints >= 2) {
                    if (origin.compareTo(clones[j]) < 0 &&
                            origin.compareTo(clones[j + extraPoints]) < 0) {
                        lineSegments[count] = new LineSegment(origin, clones[j + extraPoints]);
                        count++;
                    }
                    j += extraPoints;
                }
                j++;
            }

        }

        // Copy contents to segments array
        segments = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segments[i] = lineSegments[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++) segs[i] = segments[i];

        return segs;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
