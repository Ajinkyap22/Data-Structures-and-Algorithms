import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int VERTICAL = 1;

    private static final int HORIZONTAL = 0;

    private Node root;

    private int size;

    private static class Node {
        private final Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int division;

        private Node(Point2D p) {
            this.p = p;
            lb = null;
            rt = null;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (!contains(p)) {
            Node node = new Node(p);

            root = insertNode(root, node);

            size++;
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node current = root;

        while (true) {
            if (current == null) return false;

            if (current.p.equals(p)) return true;

            if (isLess(p, current.p, current.division)) current = current.lb;

            else current = current.rt;
        }
    }

    public void draw() {
        drawNode(root, 0, 1, 0, 1);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> pointsQueue = new Queue<>();

        rangeNode(root, rect, pointsQueue);

        return pointsQueue;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        return getNearest(p, root, root.p);
    }

    private Point2D getNearest(Point2D point, Node node, Point2D closest) {
        if (node == null) return closest;

        if (node.p.distanceSquaredTo(point) < closest.distanceSquaredTo(point)) closest = node.p;

        Point2D closestPossible;
        Node highPriority;
        Node lowPriority;

        if (node.division == VERTICAL) {
            closestPossible = new Point2D(node.p.x(), point.y());

            if (point.x() < node.p.x()) {
                highPriority = node.lb;
                lowPriority = node.rt;
            }
            else {
                highPriority = node.rt;
                lowPriority = node.lb;
            }
        }
        else {
            closestPossible = new Point2D(point.x(), node.p.y());

            if (point.y() < node.p.y()) {
                highPriority = node.lb;
                lowPriority = node.rt;
            }
            else {
                highPriority = node.rt;
                lowPriority = node.lb;
            }
        }

        closest = getNearest(point, highPriority, closest);

        if (closestPossible.distanceSquaredTo(point) < closest.distanceSquaredTo(point))
            closest = getNearest(point, lowPriority, closest);

        return closest;
    }


    private void rangeNode(Node node, RectHV rect, Queue<Point2D> range) {
        if (node == null) return;

        if (rect.contains(node.p)) range.enqueue(node.p);

        if (node.division == VERTICAL) {
            if (node.p.x() > rect.xmin()) {
                rangeNode(node.lb, rect, range);
            }

            if (node.p.x() <= rect.xmax()) {
                rangeNode(node.rt, rect, range);
            }
        }
        else {
            if (node.p.y() > rect.ymin()) {
                rangeNode(node.lb, rect, range);
            }

            if (node.p.y() <= rect.ymax()) {
                rangeNode(node.rt, rect, range);
            }

        }

    }

    private Node insertNode(Node parent, Node node) {
        if (parent == null) {
            node.division = VERTICAL;
            return node;
        }

        if (isLess(node.p, parent.p, parent.division)) {
            if (parent.lb == null) {
                node.division = getDivision(parent);
                parent.lb = node;
            }
            else {
                parent.lb = insertNode(parent.lb, node);
            }
        }
        else {
            if (parent.rt == null) {
                node.division = getDivision(parent);
                parent.rt = node;
            }
            else {
                parent.rt = insertNode(parent.rt, node);
            }
        }
        return parent;
    }

    private void drawNode(Node node, double xMin, double xMax, double yMin, double yMax) {
        if (node == null) return;

        drawPoint(node.p);
        drawDiv(node, xMin, xMax, yMin, yMax);

        if (node.division == VERTICAL) {
            drawNode(node.lb, xMin, node.p.x(), yMin, yMax);
            drawNode(node.rt, node.p.x(), xMax, yMin, yMax);
        }
        else {
            drawNode(node.lb, xMin, xMax, yMin, node.p.y());
            drawNode(node.rt, xMin, xMax, node.p.y(), yMax);
        }
    }

    private void drawPoint(Point2D p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        p.draw();
    }

    private void drawDiv(Node node, double xMin, double xMax, double yMin, double yMax) {
        StdDraw.setPenRadius();

        if (node.division == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), yMin, node.p.x(), yMax);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, node.p.y(), xMax, node.p.y());
        }
    }

    private boolean isLess(Point2D point, Point2D cur, int div) {
        if (div == VERTICAL) {
            return point.x() < cur.x();
        }

        return point.y() < cur.y();
    }


    private int getDivision(Node parent) {
        if (parent.division == VERTICAL) return HORIZONTAL;

        return VERTICAL;
    }


    // public static void main(String[] args) {
    // }
}
