import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
/**
 * Created by zhzhong on 2017/5/15.
 */
public class PointSET {
    private SET<Point2D> set;
    public PointSET() {
        set = new SET<Point2D>();
    }                              // construct an empty set of points
    public boolean isEmpty() {
        return set.isEmpty();
    }                     // is the set empty?
    public int size() {
        return set.size();
    }                        // number of points in the set
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("insert a null point.");
        set.add(p);
    }             // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("param is null.");
        return set.contains(p);
    }           // does the set contain point p?
    public void draw() {
        for (Point2D p : set)
            p.draw();
    }                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        SET<Point2D> inSet = new SET<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) inSet.add(p);
        }
        return inSet;
    }            // all points that are inside the rectangle
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (set.isEmpty()) return null;
        Point2D np = set.min();
        for (Point2D sp : set) {
            if (sp.distanceTo(p) < np.distanceTo(p)) np = sp;
        }
        return np;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
