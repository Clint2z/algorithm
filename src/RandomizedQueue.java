import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;
/**
 * Created by zhzhong on 2017/1/12.
 */
public class RandomizedQueue <Item> implements Iterable<Item> {
    private Item[] elementData;
    private int size;

    private class RIterator<Item> implements Iterator<Item>{
        int cursor;       // index of next element to return
        int[] random;
        public RIterator(){
            cursor = 0;
            random = new int[size];
            for(int i=0; i<size; i++){
                random[i] = i;
            }
            StdRandom.shuffle(random);
        }
        public boolean hasNext() {
            return cursor < size;
        }
        public Item next() {
            if(!hasNext())throw new NoSuchElementException();
            return (Item)elementData[random[cursor++]];
        }
    }

    public RandomizedQueue() {
        elementData = (Item[])new Object[2];
        size = 0;
    }                // construct an empty randomized queue
    public boolean isEmpty() {
        return size == 0;
    }                // is the queue empty?
    public int size() {
        return size;
    }                       // return the number of items on the queue
    public void enqueue(Item item) {
        if(item == null) throw new NullPointerException();
        checkCapacity(size+1);
        elementData[size++] = item;
    }          // add the item

    private void checkCapacity(int minCapacity){
        Item[] newData;
        if(minCapacity >= elementData.length){
            newData = (Item[])new Object[elementData.length<<1];
            for(int i=0; i<size; i++){
                newData[i] = elementData[i];
            }
            elementData = newData;
        }
        if(minCapacity < (elementData.length>>2)){
           newData = (Item[])new Object[elementData.length>>1];
            for(int i=0; i<size; i++){
                newData[i] = elementData[i];
            }
            elementData = newData;
        }
    }
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();
        int num = StdRandom.uniform(size);
        Item item = elementData[num];
        for(int i=num; i<size;){
            elementData[i] = elementData[++i];
        }
        elementData[--size] = null;
        return item;
    }                   // remove and return a random item

    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        int num = StdRandom.uniform(size);
        Item item = elementData[num];
        return item;
    }                    // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new RIterator<Item>();
    }        // return an independent iterator over items in random order
    public static void main(String[] args) {
        RandomizedQueue<Integer> RQueue = new RandomizedQueue<Integer>();
        RQueue.enqueue(1);
        RQueue.enqueue(2);
        RQueue.enqueue(3);
        RQueue.enqueue(4);
        RQueue.enqueue(5);
        RQueue.enqueue(6);
        RQueue.enqueue(7);
        Iterator iter = RQueue.iterator();
        while(iter.hasNext())
            System.out.print(iter.next());

    }  // unit testing (optional)
}
