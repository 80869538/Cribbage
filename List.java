/** This class is used to build a List data structure and provides necessary 
 *  function related to card in this program.
 * 
 *  a List data structure is like a linked list with all the object in the list unsorted.
 *  a List can be used to hold any type of object. 
 * 
 * @author Dongsheng Jiang
 * @login_id DONGSHENGJ
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<Item> implements Iterable<Item>{
	
	// the head of a list.
	private Node<Item> head;
	// total number of elements in a list. 
	private int length;
	
	// the data stored in the list.
	private static class Node<Item>{
		private Item item;
		private Node<Item> next;
	}
	
	/** defult constructor*/
	public List() {
		head = null;
		length = 0;
	}
	
	/** @return length */
	public int length() {
		return length;
	}
	
	/** @return wether list is empty */
	public boolean isEmpty() {
		return head == null;
	}
	
	/** insert an object to the list */
	public void add(Item item) {
		Node<Item> oldHead = head;
		head = new Node<Item>();
		head.item = item;
		head.next = oldHead;
		length++;
	}
	
	/** implement Iterator interface for List */
	public Iterator<Item> iterator(){
		return new ListIterator<Item> (head);
	}
	
	private class ListIterator<T> implements Iterator<T> {
        private Node<T> pointer;

        public ListIterator(Node<T> head) {
            pointer = head;
        }

        public boolean hasNext()  { 
        		return pointer != null;
        	}
        
        public void remove(){
        		System.out.println("Use unsupported function.");
        		System.exit(1);  
        	}
        
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = pointer.item;
            pointer = pointer.next; 
            return item;
        }
    }
}
