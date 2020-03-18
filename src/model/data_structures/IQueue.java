package model.data_structures;

public interface IQueue<Item> {

	public boolean isEmpty();
	
	public int size();
	
	public Item peek();
	
	public void enqueue( Item item );
	
	public Item dequeue();
}
