package model.data_structures;

public interface ILinkedListMap<Key, Value> {

	public int size();
	
	public boolean isEmpty();
	
	public boolean contains(Key key);
	
	public Value get(Key key);
	
	public void put(Key key, Value value);
	
	public void delete( Key key );
	
	public Iterable<Key> keys();
	
}
