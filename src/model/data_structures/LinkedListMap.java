package model.data_structures;

public class LinkedListMap<Key, Value> implements ILinkedListMap<Key, Value>{

	private int n;
	
	private Node first;
	
	public class Node {
		
		private Key key;
		
		private Value value;
		
		private Node next;
		
		public Node(Key key, Value val, Node next){
			this.key = key;
			this.value = val;
			this.next = next;
		}
		
	}
	
	public LinkedListMap(){
		
	}
	
	public int size() {
		return n;
	}

	
	public boolean isEmpty() {
		return size() <= 0;
	}

	
	public boolean contains(Key key) {
		if( key == null )
			throw new IllegalArgumentException("key can't be null in contains");
		
		return get(key) != null;
	}

	
	public Value get(Key key) {
		if( key == null )
			throw new IllegalArgumentException("key can't be null in get");
		
		for( Node x = first; x != null; x = x.next ){
			if( key.equals( x.key ) )
				return x.value;
		}
		
		return null;
	}

	
	public void put(Key key, Value value) {
		if( key == null )
			throw new IllegalArgumentException("key can't be null in put");
		
		if( value == null ){
			delete(key);
			return;
		}
		
		for( Node x = first; x != null; x = x.next ){
			if( key.equals( x.key ) ){
				x.value = value;
				return;
			}
		}
		
		first = new Node(key, value, first);
		n++;
	}

	
	public void delete(Key key) {

		if( first != null && first.key.equals(key) ){
			first = first.next;
			return;
		}
		
		Node actual = first;
		
		while( actual.next != null && !actual.next.key.equals(key) )
			actual = actual.next;
		
		if( actual.next != null )
			actual.next = actual.next.next;
		
	}

	
	public Iterable<Key> keys() {
		
		Queue<Key> queueKey = new Queue<Key>();
		
		for( Node x = first; x != null; x = x.next )
			queueKey.enqueue( x.key );
			
		return queueKey;
	}

}
