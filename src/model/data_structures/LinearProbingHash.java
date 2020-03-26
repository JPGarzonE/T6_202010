package model.data_structures;

public class LinearProbingHash<Key extends Comparable<Key>, Value> implements ILinearProbingHash<Key, Value>{
	
	private int n;
	
	private int m;
	
	private Key[] keys;
	
	private Value[] vals;
	
	public LinearProbingHash( int capacity ){
		m = capacity;
		n = 0;
		keys = (Key[]) new Comparable[m];
		vals = (Value[]) new Object[m];
	}
	
	public void put(Key key, Value value) {
		if(key == null)
			throw new IllegalArgumentException("Key argument in put is null");
		
		if(value == null){
			delete(key);
			return;
		}
		
		if( chargeFactor() >= 0.5 )
			resize(2*m);
		
		int i;
		for(i = hash(key); keys[i] != null; i = (i+1) % m ){
			if( keys[i].equals(key) ){
				vals[i] = value;
				break;
			}
		}
		
		keys[i] = key;
		vals[i] = value;
		n++;
	}

	
	public void resize(int capacity) {
		
		// code for searching a prime number upper than the capacity
		boolean primeFound = false;
		int j = capacity;
		for(; !primeFound; j++){
			primeFound = isPrime( j );
		}
		
		LinearProbingHash<Key, Value> temp = new LinearProbingHash<>( j );
		
		for( int i = 0; i < m; i++ ){
			if( keys[i] != null )
				temp.put(keys[i], vals[i]);
		}
		
		keys = temp.keys;
		vals = temp.vals;
		m = temp.m;
		
	}

	
	public Value get(Key key) {
		
		if( key == null ) throw new IllegalArgumentException("key can't be null in get");

		for( int i = hash(key); keys[i] != null; i = (i+1) % m )
			if( keys[i].equals(key) )
				return vals[i];
		
		return null;
	}

	
	public void delete(Key key) {
		
		if( key == null )
			throw new IllegalArgumentException("the key can't be null in delete");
		
		int i = hash(key);
		int x = 0;
		boolean keyExist = true;
		while( !key.equals( keys[i] ) ){
			if( x >= m ){
				keyExist = false;
				break;
			}
			i = (i+1) % m;
			x++;
		}
		
		if( keyExist ){
			
			keys[i] = null;
			vals[i] = null;
			
			i = (i+1) % m;
			while( keys[i] != null ){
				
				Key keyToRehash = keys[i];
				Value valToRehash = vals[i];
				keys[i] = null;
				vals[i] = null;
				n--;
				put(keyToRehash, valToRehash);
				i = (i+1) % m;
			}
			
			n--;
			if (n > 0 && chargeFactor() <= 0.125 ) 
				resize(m/2);
			
		}
	}

	
	public int size() {
		return n;
	}

	
	public int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}
	
	public double chargeFactor(){
		return (double)n/(double)m;
	}
	
	public boolean isEmpty() {
		
		return size() <= 0;
	}

	
	public boolean contains(Key key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to contains() is null");
        
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
		Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
	}

}
