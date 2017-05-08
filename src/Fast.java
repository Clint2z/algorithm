/**
 * Created by zhzhong on 2017/1/22.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Fast {
    private int N;
    private ArrayList<LineSegment> segment = new ArrayList<LineSegment>();
    private Point[] points;

    public Fast(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }
        N = 0;
        Arrays.sort(this.points);
        // remove repeat points
        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].compareTo(this.points[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        Point[] temp = new Point[this.points.length];
        Point[] copy = new Point[this.points.length];
        ArrayList<ArrayList<Point>> end_sets = new ArrayList<>();
        // use a copy to sort
        for (int i = 0; i < this.points.length; i++) {
            temp[i] = copy[i] = this.points[i];
            end_sets.add(new ArrayList<Point>());
        }
        Arrays.sort(copy);

        // to sort by slope
        for (int i = 0; i < copy.length - 1; i++) {
            Arrays.sort(temp, copy[i].slopeOrder());
            // find same slope copy then save them as segment in segment
            // ArrayList
            int count = 1;
            double slope0 = copy[i].slopeTo(temp[0]);
            ArrayList<Point> set = new ArrayList<>();
            for (int j = 0; j < temp.length; j++) {
                // record max point
                double slope1 = copy[i].slopeTo(temp[j]);
                if (slope1 == slope0) {
                    set.add(temp[j]);
                    count++;
                    if (count > 2 && j == temp.length - 1) {
                        set.add(copy[i]);
                        Collections.sort(set);
                        // System.out.println(set);
                        if (set.get(0).compareTo(copy[i]) < 0) {

                        } else {
                            // no key, no set
                            segment.add(new LineSegment(set.get(0), set.get(set.size() - 1)));
                            N++;
                        }
                        count = 1;
                    }
                } else {
                    if (count > 2) {
                        set.add(copy[i]);
                        Collections.sort(set);
                        // System.out.println(set);
                        if (set.get(0).compareTo(copy[i]) < 0) {

                        } else {
                            // no key, no set
                            segment.add(new LineSegment(set.get(0), set.get(set.size() - 1)));
                            N++;
                        }
                    }
                    set = new ArrayList<>();
                    set.add(temp[j]);
                    count = 1;
                }
                slope0 = slope1;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return N;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] results = new LineSegment[N];
        for (int i = 0; i < N; i++) {
            results[i] = segment.get(i);
        }
        return results;
    }

    public static void main(String[] args) {

        // read the N points from a file
        // In in = new In(args[0]);
        In in = new In("./collinear/rs1423.txt");
        int N = in.readInt();
        // System.out.println(N);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            // System.out.println("x:" + x + " y:" + y);
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // // print and draw the line segments
        Fast collinear = new Fast(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
