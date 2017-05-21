import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;

/**
 * Created by zhzhong on 2017/5/15.
 */
public class KdTree {
    private static class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node left, right;  // links to left and right subtrees
        private int size;          // subtree count
        private final int level;      //

        public Node(Point2D point,RectHV rect, int size, int level) {
            if (point == null) throw new NullPointerException();
            this.point = point;
            this.size = size;
            this.level = level;
            this.rect = rect;
        }

        public double compare(Point2D p) {
            if (level % 2 == 0)
                return p.x() - point.x();
            else
                return p.y() - point.y();
        }

        public boolean isLineIntersect(RectHV r) {
            if (level % 2 == 0)
                return r.xmin() <= point.x() && point.x() <= r.xmax();
            else
                return r.ymin() <= point.y() && point.y() <= r.ymax();
        }

        public boolean isRightOf(RectHV r) {
            if (level % 2 == 0)
                return r.xmin() < point.x() && r.xmax() < point.x();
            else
                return r.ymin() <point.y() && r.ymax() < point.y();
        }
    }
    private Node root;
    public KdTree() {

    }
    public boolean isEmpty() {
        return root == null;
    }                     // is the set empty?
    public int size() {
        return size(root);
    }                        // number of points in the set
    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }
    public void insert(Point2D p) {
        if (contains(p)) return;
        root = insert(p, new RectHV(0, 0, 1, 1), root, 0);
    }             // add the point to the set (if it is not already in the set)
    private Node insert(Point2D p, RectHV rect, Node node, int level) {
        if (node == null) return new Node(p, rect, 1, level);
        RectHV rectLeft = null;
        RectHV rectRight = null;
        double cmp = node.compare(p);

        if (cmp < 0 && node.left == null) {
            if (level % 2 == 0)
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            else
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
        } else if (cmp >= 0 && node.right == null) {
            if (level % 2 == 0) {
                rectRight = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            } else {
                rectRight = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
            }
        }

        if (cmp < 0) node.left = insert(p, rectLeft, node.left, level+1);
        else if (cmp > 0) node.right = insert(p, rectRight, node.right, level+1);
        else if (!p.equals(node.point)) node.right = insert(p, rectRight, node.right, level+1);

        node.size =1 + size(node.left) + size(node.right);
        return node;
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();

        return get(root, p, 0) != null;
    }           // does the set contain point p?

    private Point2D get(Node node ,Point2D point, int level) {
        if (node == null) return null;

        double cmp = node.compare(point);
        if (cmp < 0) return get(node.left, point, level+1);
        else if (cmp > 0) return get(node.right, point, level+1);
        else if (!point.equals(node.point)) return get(node.right, point, level+1);
        else return node.point;
    }
    public void draw() {
        draw(root);
    }                        // draw all points to standard draw
    private void draw(Node node) {
        if (node == null) return;
        StdDraw.point(node.point.x(), node.point.y());
        draw(node.left);
        draw(node.right);
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        return range(rect, root);
    }            // all points that are inside the rectangle
    private List<Point2D> range(RectHV rect, Node node) {
        if (node == null) return Collections.emptyList();
        if (node.isLineIntersect(rect)) {
            List<Point2D> points = new ArrayList<>();
            if (rect.contains(node.point)) points.add(node.point);
            points.addAll(range(rect, node.left));
            points.addAll(range(rect, node.right));
            return points;
        } else {
            if (node.isRightOf(rect))
                return  range(rect, node.left);
            else
                return range(rect, node.right);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return null;
        return nearest(p, root, root.point, root.point.distanceTo(p));
    }            // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D nearest(Point2D p, Node node, Point2D CCP, double CCdistance) {
        if (node == null) return null;
        Point2D closestPoint = CCP;
        double closetDistance = CCdistance;

        Point2D currentPoint = node.point;
        double currentDistance = p.distanceTo(currentPoint);
        if (currentDistance < closetDistance) {
            closetDistance = currentDistance;
            closestPoint = currentPoint;
        }

        double cmp = node.compare(p);
        if (cmp < 0)
            currentPoint = nearest(p, node.left, closestPoint, closetDistance);
        else
            currentPoint = nearest(p, node.right, closestPoint, closetDistance);

        if (currentPoint != null) {
            if (currentPoint != closestPoint) {
                closetDistance = currentPoint.distanceTo(p);
                closestPoint = currentPoint;
            }
        }

        double rectDistance = -1;
        if (cmp < 0 && node.right != null) {
            rectDistance = node.right.rect.distanceTo(p);
        } else if(cmp >= 0 && node.left != null){
            rectDistance = node.left.rect.distanceTo(p);
        }

        //if distance to another side rect is less than closetDistance there is a probability
        if (rectDistance != -1 && rectDistance < closetDistance) {
            if (cmp < 0) {
                currentPoint = nearest(p, node.right, closestPoint, closetDistance);
            } else {
                currentPoint = nearest(p, node.left, closestPoint, closetDistance);
            }
        }

        if (currentPoint != null) {
            closestPoint = currentPoint;
        }

        return closestPoint;
    }
    public static void main(String[] args) {

    }
}
