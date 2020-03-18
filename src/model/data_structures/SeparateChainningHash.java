package model.data_structures;

import java.util.Iterator;

public class SeparateChainningHash<Key extends Comparable<Key>, Value> implements ISeparateChainningHash<Key, Value> {

	private int n;
	
	private int m;
	
	private ILinkedListMap<Key, Value>[] separateChainningArray;
	
	public SeparateChainningHash( int capacity ){
		this.m = capacity;
		separateChainningArray = (LinkedListMap<Key, Value>[]) new LinkedListMap[ capacity ];
		
		for( int i = 0; i < m; i++ )
			separateChainningArray[i] = new LinkedListMap<>();
	}
	
	public void put(Key key, Value value) {
		if( key == null )
			throw new IllegalArgumentException("key can't be null in put");
		
		if( value == null ){
			delete(key);
			return;
		}
		
		if( chargeFactor() >= 5.0 )
			resize(2*m);
		
		int i = hash(key);
		
		if( !separateChainningArray[i].contains(key) )
			n++;
		
		separateChainningArray[i].put(key, value);
	}

	
	public void resize(int capacity) {
		
		// code for searching a prime number upper than the capacity
		boolean primeFound = false;
		int j = capacity;
		for(; !primeFound; j++){
			primeFound = isPrime( j );
		}
		
		SeparateChainningHash<Key, Value> temp = new SeparateChainningHash<>( j );
		
		for( int i = 0; i < m; i++ )
			for( Key key : separateChainningArray[i].keys() )
				temp.put( key, separateChainningArray[i].get(key) );
		
		this.m = temp.m;
		this.n = temp.n;
		this.separateChainningArray = temp.separateChainningArray;
		
	}

	
	public Value get(Key key) {
		
		if( key == null )
			throw new IllegalArgumentException("Key can't be null in get");
		
		int hash = hash(key);
		
		return separateChainningArray[ hash ].get(key);
	}

	
	public void delete(Key key) {
		if( key == null )
			throw new IllegalArgumentException("Key can't be null in delete");
		
		int hash = hash( key );
		
		if( separateChainningArray[ hash ].contains(key) )
			n--;
		
		separateChainningArray[ hash ].delete(key);
		
		if (n <= 2*m) 
			resize(m/2);
	}

	
	public int size() {
		return n;
	}

	
	public int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}
	
	public double chargeFactor(){
		return n/m;
	}

	
	public boolean isEmpty() {
		return n <= 0;
	}

	
	public boolean contains(Key key) {
		if( key == null )
			throw new IllegalArgumentException("Key can't be null in contains");
		
		return get(key) != null;
	}
	
	
	private boolean isPrime( int number ){
		boolean isPrime = true;
		int x = 2;
		
		while( isPrime && x <= (number/2) ){
			if( number % x == 0 )
				isPrime = false;
			x++;
		}
		
		return isPrime;
	}
	
	
	public Iterable<Key> keys() {
		
		Queue<Key> queueKey = new Queue<>();
		
		for( int i = 0; i < m; i++ ){
			for( Key key : separateChainningArray[i].keys() )
				queueKey.enqueue(key);
		}
		
		return queueKey;
	}

}
