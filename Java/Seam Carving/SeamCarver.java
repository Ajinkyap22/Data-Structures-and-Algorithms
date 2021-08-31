import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private static final int BORDER_ENERGY = 1000;
    private Picture picture;
    private int width;
    private int height;
    private double[] distTo;
    private int[][] edgeTo;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("Null argument");

        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            throw new IllegalArgumentException("out of range");
        }

        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
            return BORDER_ENERGY;
        }

        // calc rx, gx, bx
        Color x1 = picture.get(x + 1, y);
        Color x2 = picture.get(x - 1, y);

        int rx = x1.getRed() - x2.getRed();
        int gx = x1.getGreen() - x2.getGreen();
        int bx = x1.getBlue() - x2.getBlue();

        // add their squares
        int deltaX = rx * rx + gx * gx + bx * bx;

        // calc ry, gy, by
        Color y1 = picture.get(x, y + 1);
        Color y2 = picture.get(x, y - 1);

        int ry = y1.getRed() - y2.getRed();
        int gy = y1.getGreen() - y2.getGreen();
        int by = y1.getBlue() - y2.getBlue();

        // add their squares
        int deltaY = ry * ry + gy * gy + by * by;

        // get sqrt of additions of their squares
        return Math.sqrt(deltaX + deltaY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        int[] indices = new int[width];
        distTo = new double[height];
        edgeTo = new int[width][height];

        for (int j = 0; j < height; j++) {
            distTo[j] = BORDER_ENERGY;
        }

        double[][] energy = getEnergy();

        for (int i = 1; i < width; i++) {
            double[] lastDist = distTo.clone();

            for (int j = 0; j < height; j++) {
                distTo[j] = Double.POSITIVE_INFINITY;
            }

            for (int j = 1; j < height; j++) {
                relaxHorizontal(j - 1, i, j, lastDist, energy[i][j]);
                relaxHorizontal(j, i, j, lastDist, energy[i][j]);
                relaxHorizontal(j + 1, i, j, lastDist, energy[i][j]);
            }
        }

        double min = Double.POSITIVE_INFINITY;
        int minVertex = 0;

        for (int i = 0; i < height; i++) {
            if (distTo[i] < min) {
                min = distTo[i];
                minVertex = i;
            }
        }

        for (int i = width - 1; i >= 0; i--) {
            indices[i] = minVertex;
            minVertex = edgeTo[i][minVertex];
        }

        return indices;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] indices = new int[height];
        distTo = new double[width];
        edgeTo = new int[width][height];

        for (int j = 0; j < width; j++) {
            distTo[j] = BORDER_ENERGY;
        }

        double[][] energy = getEnergy();

        for (int i = 1; i < height; i++) {
            double[] lastDist = distTo.clone();

            for (int j = 0; j < width; j++) {
                distTo[j] = Double.POSITIVE_INFINITY;
            }
            for (int j = 1; j < width; j++) {
                relaxVertical(j - 1, j, i, lastDist, energy[j][i]);
                relaxVertical(j, j, i, lastDist, energy[j][i]);
                relaxVertical(j + 1, j, i, lastDist, energy[j][i]);

            }
        }

        double min = Double.POSITIVE_INFINITY;
        int minVertex = 0;

        for (int i = 0; i < width; i++) {
            if (distTo[i] < min) {
                min = distTo[i];
                minVertex = i;
            }
        }

        for (int i = height - 1; i >= 0; i--) {
            indices[i] = minVertex;
            minVertex = edgeTo[minVertex][i];
        }

        return indices;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Null argument");

        if (height <= 1 || seam.length != width) throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height - 1)
                throw new IllegalArgumentException("Out of Range");

            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("Invalid Indices");
        }

        Picture pic = new Picture(width, height - 1);
        int k = 0;

        for (int i = 0; i < seam.length; i++) {
            for (int j = 0; j < seam[i]; j++) {
                pic.set(i, k, picture.get(i, j));
                k++;
            }

            for (int j = seam[i] + 1; j < height; j++) {
                pic.set(i, k, picture.get(i, j));
                k++;
            }
            k = 0;
        }

        picture = pic;
        width = picture.width();
        height = picture.height();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Null argument");

        if (width <= 1 || seam.length != height)
            throw new IllegalArgumentException("Invalid Width");

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width - 1)
                throw new IllegalArgumentException("Out of Range");

            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("Invalid Indices");
        }

        Picture pic = new Picture(width - 1, height);
        int k = 0;

        for (int i = 0; i < seam.length; i++) {
            for (int j = 0; j < seam[i]; j++) {
                pic.set(k, i, picture.get(j, i));
                k++;
            }

            for (int j = seam[i] + 1; j < width; j++) {
                pic.set(k, i, picture.get(j, i));
                k++;
            }
            k = 0;
        }

        picture = pic;
        width = picture.width();
        height = picture.height();
    }

    private double[][] getEnergy() {
        double[][] energyArr = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energyArr[i][j] = energy(i, j);
            }
        }
        return energyArr;
    }

    private void relaxHorizontal(int prev, int x, int y, double[] lastDist, double energy) {
        if (prev < 0 || prev > height - 1) {
            return;
        }

        if (distTo[y] > lastDist[prev] + energy) {
            distTo[y] = lastDist[prev] + energy;
            edgeTo[x][y] = prev;
        }
    }

    private void relaxVertical(int prev, int x, int y, double[] lastDist, double energy) {
        if (prev < 0 || prev > width - 1) {
            return;
        }

        if (distTo[x] > lastDist[prev] + energy) {
            distTo[x] = lastDist[prev] + energy;
            edgeTo[x][y] = prev;
        }
    }

    //  unit testing (optional)
    // public static void main(String[] args) {
    //
    // }

}
