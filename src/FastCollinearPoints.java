import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by zhzhong on 2017/1/21.
 */
public class FastCollinearPoints {
    private LinkedList<LineSegment> SegmentList = new LinkedList<LineSegment>();
    private Point p;
    private Point[] aux, excited;
    private LinkedList<Point[]> pointInList = new LinkedList<Point[]>();
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException ();
        int n = points.length;
        aux = new Point[points.length];
        excited = new Point[points.length];
        Arrays.sort(points);
        // remove repeat points
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
            excited[i] = points[i];
        }
        excited[points.length-1] = points[points.length-1];
        for(int i=0; i < n; i++){
            LinkedList<Point> collinearPoints = new LinkedList<Point>();
            p = excited[i];
            mergeSort(points,0,n-1);
            for(int j=0;j<n;j++){
                if(p.compareTo(points[j])==0)continue;
                if(collinearPoints.isEmpty()){collinearPoints.add(points[j]);}
                else if(p.slopeOrder().compare(collinearPoints.getLast(),points[j])==0){collinearPoints.add(points[j]);}
                else if(collinearPoints.size()>=3){
                    collinearPoints.add(p);
                    Point maxPoint = Collections.max(collinearPoints);
                    Point minPoint = Collections.min(collinearPoints);
                    int flag = 0;
                    for(Point[] pointsIn : pointInList){
                        if(pointsIn[0].compareTo(minPoint)==0 && pointsIn[1].compareTo(maxPoint)==0) flag = 1;
                    }
                    if(flag == 0){
                        LineSegment segment = new LineSegment(minPoint,maxPoint);
                        SegmentList.add(segment);
                        Point[] tempPoints = {minPoint,maxPoint};
                        pointInList.add(tempPoints);
                    }
                    collinearPoints.clear();
                    collinearPoints.add(points[j]);
                }
                else {
                    collinearPoints.clear();
                    collinearPoints.add(points[j]);
                }
            }
            if(collinearPoints.size()>=3){
                    collinearPoints.add(p);
                    Point maxPoint = Collections.max(collinearPoints);
                    Point minPoint = Collections.min(collinearPoints);
                    int flag = 0;
                    for(Point[] pointsIn : pointInList){
                        if(pointsIn[0].compareTo(minPoint)==0 && pointsIn[1].compareTo(maxPoint)==0) flag = 1;
                    }
                    if(flag == 0){
                        LineSegment segment = new LineSegment(minPoint,maxPoint);
                        SegmentList.add(segment);
                        Point[] tempPoints = {minPoint,maxPoint};
                        pointInList.add(tempPoints);
                    }
            }
        }
    }    // finds all line segments containing 4 or more points

    private void mergeSort(Point[] points,int lo,int hi) {
        if(hi<=lo) return;
        int mid = lo + (hi - lo)/2;
        mergeSort(points,lo,mid);
        mergeSort(points,mid+1,hi);
        merge(points,lo,mid,hi);
    }

    private void sort(Point[] points){
        for (int n=1; n<points.length; n=n*2) {
            for (int lo=0; lo<points.length; lo=lo+n*2){
                merge(points,lo,lo+n-1,Math.min(lo+2*n-1,points.length-1));
            }
        }
    }
    private void merge(Point[] points,int lo,int mid,int hi){
        int i = lo, j = mid + 1;
        for(int k = lo; k <= hi; k++){
            aux[k] = points[k];
        }
        for(int k = lo; k <= hi; k++) {
            if(i>mid) points[k] = aux[j++];
            else if(j>hi) points[k] = aux[i++];
            else if(p.slopeOrder().compare(aux[i],aux[j])>0) points[k] = aux[j++];
            else points[k] = aux[i++];
        }
    }
    public int numberOfSegments() {
        return SegmentList.size();
    }       // the number of line segments
    public LineSegment[] segments() {
        return SegmentList.toArray(new LineSegment[SegmentList.size()]);
    }               // the line segments
    public static void main(String[] args){
        Point[] points = { new Point(0,1),new Point(2,2),new Point(3,7),new Point(4,4),new Point(0,3),new Point(0,13),
                new Point(5,5),new Point(6,6),new Point(7,7),new Point(0,123),new Point(100,123)};
        FastCollinearPoints fp = new FastCollinearPoints(points);
        System.out.println(fp.numberOfSegments());
    }

}
