package lesson5.assignment.kdTrees;

import princeton.algs4.Point2D;
import princeton.stdlib.StdDraw;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * User: mauro
 * Date: 02/12/2014
 * Time: 10:13
 */

public class KdTree {
    private enum Coordinate {X, Y}
    private static final int ROOT_LEVEL = 0;
    private Node root = null;
    private int size = 0;

    // is the set empty?
    public boolean isEmpty() { return root == null; }

    // number of points in the set
    public int size() {return size;}

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D val) { root = insert(root, val, ROOT_LEVEL); }

    private Node insert(Node h, Point2D val, int level) {
        if (h == null) return new Node(val);
        h.updateCoordinate(selectCoordinate(level++));
        int cmp = h.compareTo(val);
        if (cmp < 0) h.left = insert(h.left, val, level);
        else if (cmp > 0) h.right = insert(h.right, val, level);
        else h.val = val;

        return h;
    }

    private Coordinate selectCoordinate(int level) {
        Coordinate result = Coordinate.X;
        if (level == ROOT_LEVEL) return result;
        int reminder = level% 2;
        if (reminder != ROOT_LEVEL ) return Coordinate.Y;
        return result;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node x = root;
        while (x != null) {
            if (x.val.equals(p)) return true;
            int cmp = x.compareTo(p);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() { drawNode(root, null, ROOT_LEVEL); }

    private void drawNode(Node x, Node parent, int level) {
        if (x == null ) return;

        Point2D val = x.val;
        StdDraw.setPenRadius(.01);
        val.draw();
        StdDraw.setPenRadius();
        if (parent == null) drawLine(val, null, level++);
        else drawLine(val, parent.val, level++);
        if (x.left != null ) drawNode(x.left, x, level);
        if (x.right != null ) drawNode(x.right, x, level);
    }

    private void drawLine(Point2D point, Point2D parent, int level) {
        double startY =0, endY=1, startX=0, endX=1;
        Coordinate coordinate = selectCoordinate(level);
        if (coordinate == Coordinate.X) {
            endX = startX = point.x();
            if (parent != null) {
                double endpoint = parent.y();
                if (endpoint > point.y()) endY = endpoint;
                else startY = endpoint;
            }
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            endY = startY = point.y();
            if (parent != null) {
                double endpoint = parent.x();
                if (endpoint > point.x()) endX = endpoint;
                else startX = endpoint;
            }
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        StdDraw.line(startX, startY, endX, endY);
        StdDraw.setPenColor();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> inRange = new TreeSet<Point2D>();
        findIntersections(rect, root, null, ROOT_LEVEL, inRange);
        return inRange;
    }

    private void findIntersections(RectHV rect, Node x, Node parent, int level, Set inRange) {
       if (x == null) return;
       if (rect.contains(x.val)) inRange.add(x.val);
       x.updateCoordinate(selectCoordinate(level++));
       if(rect.intersects(x.leftRect(parent))) findIntersections(rect, x.left, x, level, inRange);
       if(rect.intersects(x.rightRect(parent))) findIntersections(rect, x.right, x, level, inRange);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;

        Node champ = root;
        int level = ROOT_LEVEL;
        Node x = root;
        while (x != null) {
            if (x.val.equals(p)) return p;
            if (x.val.distanceTo(p) < champ.val.distanceTo(p)) champ = x;

            x.updateCoordinate(selectCoordinate(level++));
            int cmp = x.compareTo(p);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
        }
        return champ.val;
    }

    public void show() {
        System.out.println();
        System.out.println("Size = " + size());
        System.out.println("root " + publish(root, ROOT_LEVEL));
    }

    private String publish(Node start, int level) {
        StringBuilder output = new StringBuilder();
        Node x = start;
        if (x != null) output.append(x);
        output.append(" Level " + level++ + '\n');
        if (x.left != null) output.append(" LEFT " +  publish(x.left, level));
        if (x.right != null) output.append(" RIGHT " + publish(x.right, level));

        return output.toString();
    }


    private static class Node implements Comparable<Point2D>{
        private Coordinate coordinate;
        private Point2D val;
        private Node left, right;

        private Node(Point2D val) { this.val = val; }

        private void updateCoordinate(Coordinate c) { coordinate = c; }

        private RectHV leftRect(Node parent) {
            double minX=0, minY=0, maxX=1, maxY=1;
            switch (coordinate) {
                case X: maxX = val.x();
                    if (parent != null) maxY = parent.val.y();
                    break;
                case Y: maxY = val.y();
                    if (parent != null) maxX = parent.val.x();
                    break;
            }
            RectHV leftRect = new RectHV(minX,minY,maxX,maxY);
            return leftRect;
        }

        private RectHV rightRect(Node parent) {
            double minX=0, minY=0, maxX=1, maxY=1;
            switch (coordinate) {
                case X: minX = val.x();
                    if (parent != null) minY = parent.val.y();
                    break;
                case Y: minY = val.y();
                    if (parent != null) minX = parent.val.x();
                    break;
            }
            RectHV rightRect = new RectHV(minX,minY,maxX,maxY);
            return rightRect;
        }
        @Override
        public int compareTo(Point2D that) {
            Comparator c = val.Y_ORDER;
            if (coordinate == Coordinate.X)
               c = val.X_ORDER;
            return ( c.compare(val, that)) * -1;
        }

        @Override
        public String toString() { return " x = " + val.x() + ", y = " + val.y(); }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kd = new KdTree();
        Point2D p1 = new Point2D(0.3, 0.3);
        Point2D p2 = new Point2D(0.2, 0.6);
        Point2D p3 = new Point2D(0.7, 0.3);
        Point2D p4 = new Point2D(0.5, 0.2);
        Point2D p5 = new Point2D(0.6, 0.4);

        kd.insert(p1);
        kd.insert(p2);
        System.out.println(kd.contains(p3));
        kd.insert(p3);
        System.out.println(kd.contains(p3));
        kd.insert(p4);
        kd.insert(p5);
        kd.show();

        Point2D searchPoint = new Point2D(0.5,0.4);
        System.out.println("nearest to " + searchPoint + " is " + kd.nearest(searchPoint));

        RectHV searchRect = new RectHV(0.2, 0.2, 0.7, 0.5);
        System.out.println("searching intersections" + searchRect);
        Iterable<Point2D> rangeSearchResults = kd.range(searchRect);
        for (Point2D p : rangeSearchResults) {
            System.out.println(p + " is in range");
        }
    }
}
