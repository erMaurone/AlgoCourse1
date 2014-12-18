package algoCourse2.lesson2.assignment.seamCarving.testing;

import princeton.stdlib.Picture;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 15/12/2014
 * Time: 15:34
 */
public class SeamCarver {

    private int width = 0, height = 0;
    private Color[][] colors;
    private double [][] energy;
    private static final double BORDER_ENERGY = 195075;
    private enum Direction{VERTICAL, HORIZONTAL}
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)  {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
        colors = new Color[width][height];
        energy = new double[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height;j++)
                colors[i][j] = picture.get(i,j);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height;j++)
                energy[i][j] = calculateEnergy(i, j);
    }

    // current picture
    public Picture picture() {
        Picture currentPic = new Picture(width, height);
        for (int c = 0; c < colors.length; c++)
            for (int r = 0; r < colors[0].length; r++) {
                Color col = colors[c][r];
                if (col != null)
                    currentPic.set(c,r,col);
            }
        return currentPic;
    }

    // width of current picture
    public int width() { return width; }

    // height of current picture
    public int height() { return height;}

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return energy[x][y];
    }

    private double calculateEnergy(int x, int y) {
        if (isBorderPixel(x,y))
            return BORDER_ENERGY;
        Color xPlus = colors[x + 1][y];
        Color xMinus = colors[x - 1][y];
        Color yPlus = colors[x][y + 1];
        Color yMinus = colors[x][y - 1];
        return delta(xPlus, xMinus) + delta(yPlus, yMinus);
    }

    private double delta(Color plus, Color minus) {
        double r, g, b;
        r = plus.getRed() - minus.getRed();
        g = plus.getGreen() - minus.getGreen();
        b = plus.getBlue() - minus.getBlue();
        r = r * r;
        g = g * g;
        b = b * b;
        return r + g + b;
    }

    private boolean isBorderPixel(int x, int y) {
        if (x == 0 || x == (width -1)  ||
                y == 0 || y == (height -1) )
            return true;
        return false;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return findSeam(Direction.HORIZONTAL);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findSeam(Direction.VERTICAL);
    }

    private int[] findSeam(Direction D) {
            double winningCost = Integer.MAX_VALUE;
            int limit = height, size = width;
            if (D == Direction.VERTICAL) {limit = width; size = height;}
            int[] winningSeam = null;

            for (int cr = 0; cr < limit; cr++) {
                int[] currentSeam = new int[size];
                double seamCost = evaluateCost(cr, currentSeam, D);
                if (seamCost < winningCost) {
                    winningCost = seamCost;
                    winningSeam = currentSeam;
                }
            }
            return winningSeam;
    }

    private double evaluateCost(int cr, int[] seam, Direction d) {
        if (Direction.HORIZONTAL == d)
            return evaluateHCost(cr, seam);
        return evaluateVCost(cr, seam);
    }

    private double evaluateHCost (int row, int[] seam) {
        seam[0] = row;
        double cost = energy[0][row];

        for (int col = 1; col < width; col++) {
            double top, center, bottom;
            center = energy[col][row];

            if (row == 0) { top = Double.MAX_VALUE; }
            else { top = energy[col][row - 1]; }

            if (row == height -1) { bottom = Double.MAX_VALUE; }
            else { bottom = energy[col][row + 1]; }

            if ((center < top && center < bottom) ||
                    (top == bottom && center == top)) {
                cost += center;
                seam[col] = row;
            } else if (top < bottom) {
                cost += top;
                seam[col]= --row;
            } else if (bottom < top) {
                cost += bottom;
                seam[col] = ++row;
            } else if (bottom == top || center == bottom) {
                cost += bottom;
                seam[col] = ++row;
            }

        }
        return cost;
    }

    private double evaluateVCost (int col, int[] seam) {
        seam[0] = col;
        double cost = energy[col][0];

        for (int row = 1; row < height; row++) {
            double left, right, center;
            center = energy[col][row];

            if (col == 0) { left = Double.MAX_VALUE; }
            else { left = energy[col - 1][row]; }

            if (col == width -1) { right = Double.MAX_VALUE; }
            else { right = energy[col + 1][row]; }

            if ((center < left && center < right) ||
                (right == left && left == right)) {
                    cost += center;
                    seam[row] = col;
            } else if (left < right) {
                cost += left;
                seam[row]= --col;
            } else if (right < left) {
                cost += right;
                seam[row] = ++col;
            } else if (right == left || center == right) {
                cost += right;
                seam[row] = ++col;
            }
        }
        return cost;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new NullPointerException();
        if (! isValidSeam(seam, Direction.HORIZONTAL)) throw new IllegalArgumentException("Invalid seam");
        if (height <= 2 ) throw new IllegalArgumentException("Invalid height size (not greater than 1");
        if (width <= 2) throw new IllegalArgumentException("Invalid width size (not greater than 1");

        Color[][] updatedColorMatrix = new Color[width][height -1];
        double[][] updatedEnergy = new double[width][height -1];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width;j++) {
                if (i < seam[j]) updatedColorMatrix[j][i] = picture.get(j,i);
                if (i > seam[j]) updatedColorMatrix[j][i -1] = picture.get(j,i);
            }
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width;j++) {
                if (i < seam[j]) updatedEnergy[j][i] = energy[j][i];
                else if (i > seam[j]) updatedEnergy[j][i - 1] = energy[j][i];
            }

        height--;
        colors = updatedColorMatrix;
        for(int j = 0; j < seam.length;j++) {
            int i = seam[j];
            updatedEnergy[j][i] = calculateEnergy(j,i);
            if (i > 0) updatedEnergy[j][i - 1] = calculateEnergy(j,i-1);
            if (i < (height -1)) updatedEnergy[j][i + 1] = calculateEnergy(j,i+1);
        }
        energy = updatedEnergy;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new NullPointerException();
        if (! isValidSeam(seam, Direction.VERTICAL)) throw new IllegalArgumentException("Invalid seam");
        if (height <= 2 ) throw new IllegalArgumentException("Invalid height size (not greater than 1");
        if (width <= 2) throw new IllegalArgumentException("Invalid width size (not greater than 1");

        Color[][] updatedColorMatrix = new Color[width - 1][height];
        double[][] updatedEnergy = new double[width - 1][height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width;j++) {
                if (j < seam[i]) updatedColorMatrix[j][i] = picture.get(j,i);
                if (j > seam[i]) updatedColorMatrix[j - 1][i] = picture.get(j,i);
            }
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width;j++) {
                if (j < seam[i]) updatedEnergy[j][i] = energy[j][i];
                if (j > seam[i] ) updatedEnergy[j - 1][i] = energy[j][i];
            }

        width--;
        colors = updatedColorMatrix;
        for(int i = 0; i < seam.length;i++) {
            int j = seam[i];
            updatedEnergy[j][i] = calculateEnergy(j,i);
            if (j > 0) updatedEnergy[j - 1][i] = calculateEnergy(j-1,i);
            if (j < (width -1)) updatedEnergy[j + 1][i] = calculateEnergy(j+1,i);
        }
        energy = updatedEnergy;
    }

    private boolean isValidSeam(int[] seam, Direction d) {
        int limit = height, valueLimit = width;
        if (Direction.HORIZONTAL == d) {
            limit = width;
            valueLimit = height;
        }

        if (seam.length != limit) return false;
        boolean isValid = true;
        int delta = 1;
        for (int i=0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= valueLimit) {
                isValid = false;
                break;
            }
            if (i < (seam.length -1)) {
                int difference = seam[i] - seam[i+1];
                if (difference != 0 && delta != Math.abs(difference)) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }
}
