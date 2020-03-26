package model.data_structures;

public interface ISeparateChainningHash<K extends Comparable<K>, V> {

	public void put(K key ,V value);

	public void resize(int capacity);
	
	public V get(K key);
	
	public void delete(K key);
	
	public int size();
	
	public int hash(K key);
	
	public boolean isEmpty();
	
	public boolean contains(K key);
	
	Iterable<K> keys();
	
}
