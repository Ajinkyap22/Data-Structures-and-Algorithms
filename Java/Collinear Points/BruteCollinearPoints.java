import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        // Clone to avoid mutation
        Point[] clones = points.clone();
        LineSegment[] lineSegments = new LineSegment[clones.length * clones.length];
        int count = 0;
        Point p, q, r, s;

        Arrays.sort(clones);

        // Check for duplication
        for (int i = 0; i < clones.length - 1; i++) {
            if (clones[i].equals(clones[i + 1])) throw new IllegalArgumentException();
        }
        for (int a = 0; a < clones.length; a++)
            for (int b = a + 1; b < clones.length; b++)
                for (int c = b + 1; c < clones.length; c++)
                    for (int d = c + 1; d < clones.length; d++) {
                        p = clones[a];
                        q = clones[b];
                        r = clones[c];
                        s = clones[d];

                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            lineSegments[count++] = new LineSegment(p, s);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
