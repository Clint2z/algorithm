import java.util.Iterator;

/**
 * Created by zhzhong on 2017/1/12.
 */
public class Deque<Item> implements Iterable<Item>{
    private class DIterator<Item> implements Iterator<Item>{
        private Node<Item> lastReturned;
        private Node next;
        DIterator(){
            next = first;
        }
        public boolean hasNext(){
            return next != null;
        }
        public Item next(){
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            lastReturned = next;
            next = next.next;
            return lastReturned.item;
        }
    }
    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;

        Node(Node<Item> prev, Item element, Node<Item> next) {
            if(element == null) {
                throw new NullPointerException("element cannot be null!");
            }
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
    private int size;
    private Node<Item> first;
    private Node<Item> last;
    private void checkEmpty(){
        if(isEmpty()) throw new java.util.NoSuchElementException();
    }
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }                          // construct an empty deque
    public boolean isEmpty() {
        return size == 0;
    }                // is the deque empty?
    public int size() {
        return size;
    }                       // return the number of items on the deque
    public void addFirst(Item item) {
        final Node<Item> f = first;
        final Node<Item> newNode = new Node<>(null, item, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }         // add the item to the front
    public void addLast(Item item) {
        final Node<Item> l = last;
        final Node<Item> newNode = new Node<>(l, item, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }          // add the item to the end
    public Item removeFirst() {
        checkEmpty();
        final Node<Item> f = first;
        final Node<Item> next = f.next;
        Item item = f.item;
        f.item = null;
        f.next = null;
        first = next;
        if(next == null){
            last = null;
        }else{
            first.prev = null;
        }
        size--;
        return item;
    }               // remove and return the item from the front
    public Item removeLast() {
        checkEmpty();
        final Node<Item> l = last;
        final Node<Item> prev = l.prev;
        last = prev;
        Item item = l.item;
        l.item = null;
        l.prev = null;
        if(prev == null){
            first = null;
        }else{
            last.next = null;
        }
        size--;
        return item;
    }                // remove and return the item from the end
    public Iterator<Item> iterator() {
        return new DIterator<Item>();
    }        // return an iterator over items in order from front to end
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
        deque.addLast(9);
        Iterator iter = deque.iterator();
        while(iter.hasNext()){
            System.out.print(iter.next());
        }
    }  // unit testing (optional)

}
