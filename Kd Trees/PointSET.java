import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> pointsQueue = new Queue<>();

        for (Point2D p : points) {
            if (rect.contains(p))
                pointsQueue.enqueue(p);
        }

        return pointsQueue;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        Point2D nearest = null;

        for (Point2D point : points) {
            if (nearest == null || p.distanceSquaredTo(point) < p.distanceSquaredTo(nearest))
                nearest = point;
        }

        return nearest;
    }

    // public static void main(String[] args) {
    // }
}
