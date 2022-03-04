/**
 * Name: Ben Zhao
 * Email: zhaob@carleton.edu 
 * Description: Implementation of a BST for 2-dimensional data. We are implementing a K-d tree which uses 2D points as keys(k=2)
 * Our programs supports efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point).
 * In addition to typical BST methods like put, get, contains, etc.
 */

import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree<Value> {
    
    private int size;
    public class Node {
        private Point2D p;     // the point
        private Value val;     // the tree maps the point to this value
        private RectHV rect;   // the axis-aligned rectangle corresponding to this node
        private Node lb;       // the left/bottom subtree
        private Node rt;       // the right/top subtree
        private double x;
        private double y;
        

        public Node(Point2D p, Value val, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
            x = p.x();
            y = p.y();
            
        }

     }
    
    private Node root;
    // construct an empty tree
    public KdTree() {
        root = null;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points
    public int size() {
        return size;
    }
  

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        double inf = Double.POSITIVE_INFINITY;
        double negInf = Double.NEGATIVE_INFINITY;
        size++;
        if (isEmpty()) {
            RectHV rootRect = new RectHV(negInf, negInf, inf, inf);
            root = new Node(p, val, rootRect);
        } else {
            put(p, root, val, root.rect, true);
        }
    }

    private void put(Point2D point, Node node, Value val, RectHV rect, boolean isVertical) {
        if (node.p.x() == point.x() && node.p.y() == point.y()) {
            node.val = val;
        }
        if (isVertical) {
            if (point.x() < node.x) {
                if (node.lb == null) {
                    RectHV rectNew = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
                    node.lb = new Node(point, val, rectNew);
                   
                } else {
                    put(point, node.lb, val, rect, !isVertical);
                }
            }
            else if (point.x() >= node.x) {
                if (node.rt == null) {
                    RectHV rect2 = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                    node.rt = new Node(point, val, rect2);
                    
                } else {
                    put(point, node.rt, val, rect, !isVertical);
                }
            }
        } 
        else if (!isVertical) {
            if (point.y() < node.y) {
                if (node.lb == null) {
                    RectHV rect3 = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
                    node.lb = new Node(point, val, rect3);
                    
                } else {
                    put(point, node.lb, val, rect, !isVertical);
                }
            }
            else if (point.y() >= node.y) {
                if (node.rt == null) {
                    RectHV rect4 = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
                    node.rt = new Node(point, val, rect4);
                } else {
                    put(point, node.rt, val, rect, !isVertical);
                }
            }
        } 
    }
    
    

    // value associated with point p
    public Value get(Point2D p) {
        if (root == null) {
            throw new NullPointerException();
        }
        return get(root, p, true);
    }

    private Value get(Node node, Point2D p, boolean isVertical) {
        if (p.equals(node.p)) {
            return node.val;
        }
        if (isVertical) {
            if (p.x() < node.x) {
                return get(node.lb, p, !isVertical);
            }
            else if (p.x() >= node.x) {
                return get(node.rt, p, !isVertical);
            }
        }
        else if (!isVertical) {
            if (p.y() < node.y) {
                return get(node.lb, p, isVertical);
            }
            else if (p.y() >= node.y) {
                return get(node.rt, p, isVertical);
            }
        }
        return null;
    }

    // does the tree contain point p?
    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    // all points in the map in level order
    public Iterable<Point2D> points() {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new Queue<Node>();
        Queue<Point2D> queueTwo = new Queue<Point2D>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node xValue = queue.dequeue();
            if (xValue == null) {
                continue;
            }
            queueTwo.enqueue(xValue.p);
            queue.enqueue(xValue.lb);
            queue.enqueue(xValue.rt);
        }
        return queueTwo;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> iterable = new ArrayList<Point2D>();
        range(root, rect, iterable);
        return iterable;
    }

    // Only search a node and its subtrees if the query rectangle intersects the rectangle of a node
    // subtrees are only searched if it could contain a point in query rectangle
    private void range(Node node, RectHV rect, ArrayList<Point2D> iterable) {
        if (node == null) {
            return;
        }
        if (rect.intersects(node.rect)) {
            if (rect.contains(node.p)) {
                iterable.add(node.p);
            }
            range(node.lb, rect, iterable);
            range(node.rt, rect, iterable);
        }
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        return nearest(p, root.p, root, true);
    }
    private Point2D nearest(Point2D p, Point2D n, Node node, boolean isVertical) {
        Point2D nearest = n;
        if (node == null) {
            return nearest;
        }
        if (node.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            nearest = node.p;
        }
        // Pruning where only if the distance of a node's rectange to the query point is less distance of nearest point 
        // so far in the tree do we need to explore that node's subtrees. Otherwise we will ignore that node and its subtrees
        if (node.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            if (isVertical) {
                if (p.x() < node.x) {
                    nearest = nearest(p, nearest, node.lb, isVertical);
                    nearest = nearest(p, nearest, node.rt, isVertical);
                }
                else if (p.x() >= node.x) {
                    nearest = nearest(p, nearest, node.rt, isVertical);
                    nearest = nearest(p, nearest, node.lb, isVertical);
                }

            }
            else if (!isVertical) {
                if (p.y() < node.y) {
                    nearest = nearest(p, nearest, node.lb, !isVertical);
                    nearest = nearest(p, nearest, node.rt, isVertical);
                }
                else if (p.y() >= node.y) {
                    nearest = nearest(p, nearest, node.rt, !isVertical);
                    nearest = nearest(p, nearest, node.lb, !isVertical);
                }
            }
        }
        return nearest;
    
    }
    

    // the nearest k points to point p (Challenge problem)
    public Iterable<Point2D> nearest(Point2D p, int k) {
        return null;
    }

    // Testing Implementation Not Graded 
    public static void main(String[] args) {
        KdTree<String> kdTree = new KdTree<>();
        Point2D point1 = new Point2D(0.29, 0.27);
        Point2D point2 = new Point2D(0.60, 0.87);
        Point2D point3 = new Point2D(0.37, 0.01);
        Point2D point4 = new Point2D(0.90, 0.61);
        Point2D point5 = new Point2D(0.29, 0.23);
      
       

        kdTree.put(point1, "A");
        kdTree.put(point2, "B");
        kdTree.put(point3, "C");
        kdTree.put(point4, "D");
        kdTree.put(point5, "e");
      

        System.out.println(kdTree.points());
        
    }


}
