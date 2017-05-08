import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by zhzhong on 2017/1/21.
 */
public class BruteCollinearPoints {
    private LinkedList<LineSegment> SegmentList = new LinkedList<LineSegment>();
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException ();
        int N = points.length;
        Arrays.sort(points);
        for (int i=0; i<N; i++){
            if(points[i] == null) throw new NullPointerException ();
            for (int j=i+1; j<N; j++){
                if(points[i].compareTo(points[j])==0) throw new IllegalArgumentException();
                for (int k=j+1; k<N; k++){
                    for (int l=k+1; l<N; l++){
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])){
                            LineSegment lineSegment = new LineSegment(points[i],points[l]);
                            SegmentList.add(lineSegment);
                        }
                    }
                }
            }
        }
    }
       // finds all line segments containing 4 points
    public int numberOfSegments() {
        return SegmentList.size();
    }       // the number of line segments
    public LineSegment[] segments() {
        return SegmentList.toArray(new LineSegment[SegmentList.size()]);
    }               // the line segments
    public static void main(String[] args){
        Point p1 = new Point(0,1);
        Point p2 = new Point(1,1);
        Point p3 = new Point(2,2);
        Point p4 = new Point(3,7);
        Point p5 = new Point(4,4);
        Point p6 = new Point(5,5);
        Point p7 = new Point(6,6);
        Point p8 = new Point(7,7);
        Point[] points = { new Point(0,1),new Point(2,2),new Point(3,7),new Point(4,4),new Point(0,3),new Point(0,13),
                new Point(5,5),new Point(6,6),new Point(0,123)};
        BruteCollinearPoints fp = new BruteCollinearPoints(points);
        System.out.println(fp.numberOfSegments());
    }
}
