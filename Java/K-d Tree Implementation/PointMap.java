/**
 * A simple, but inefficient implemetation for a map with 2d points as the keys
 * Useful as reference when implementing KdTree
 * CS 201, Lab 6
 * (c) 2021 Aaron Bauer
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayList;

public class PointMap<Value> {
    private RedBlackBST<Point2D, Value> tree; // internal balanced search tree

    // construct an empty map of points 
    public PointMap() {
        tree = new RedBlackBST<Point2D, Value>();
    }

    // is the map empty? 
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    // number of points
    public int size() {
        return tree.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        tree.put(p, val);
    }

    // value associated with point p 
    public Value get(Point2D p) {
        return tree.get(p);
    }

    // does the map contain point p? 
    public boolean contains(Point2D p) {
        return tree.contains(p);
    }

    // all points in the map
    public Iterable<Point2D> points() {
        return tree.keys();
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> ps = new ArrayList<>();
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                ps.add(p);
            }
        }
        return ps;
    }

    // a nearest neighbor of point p; null if the map is empty 
    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        for (Point2D neighbor : tree.keys()) {
            if (nearest == null || p.distanceSquaredTo(nearest) > p.distanceSquaredTo(neighbor)) {
                nearest = neighbor;
            }
        }
        return nearest;
    }
}