import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by zhzhong on 2017/1/13.
 */
public class Permutation {
    public static void main(String[] args) throws Exception{
        Integer k = Integer.valueOf(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()){
            queue.enqueue(StdIn.readString());
        }
        for(int i=0;i<k;i++){
            StdOut.println(queue.dequeue());
        }
    }
}
