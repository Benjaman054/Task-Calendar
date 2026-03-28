import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyPriorityQueue<T> implements Iterable<T> {
	private final ArrayList<T>[] nodes ;
	private int size;
	private final int max_priority;
	
	
	public MyPriorityQueue(int max_priority) {
		this.max_priority = max_priority;
		nodes = new ArrayList[max_priority + 1];
		for(int i=0 ; i < this.max_priority + 1 ; i++) {
			nodes[i] = new ArrayList<>();
		}
		this.size = 0;
	}
	
	public void add(T item , int priority) {
		if(priority > max_priority || priority < 1) {
			nodes[max_priority].add(item);
		}
		else {
			nodes[priority].add(item);
		}
		size ++;
	}
	
	public T poll() {
		for(int i=1 ; i <= max_priority ; i++) {
			if(!(nodes[i].isEmpty())){
				size--;
				return nodes[i].remove(0);
			}
		}
		return null;
	}
	
	public boolean contains(T item) {
		for(int i=1 ; i <= max_priority ; i++) {
			if(nodes[i].contains(item)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean remove(T item) {
		for(int i=1 ; i <= max_priority ; i++) {
			if(nodes[i].contains(item)) {
				nodes[i].remove(item);
				size--;
				return true;
			}
		}
		return false;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public Iterator<T> iterator(){
		return new New_Iterator();
	}
	

	public class New_Iterator implements Iterator<T>{
		private int curr_priority = 1;
		private int curr_index = 0;
		
		@Override
		public boolean hasNext() {
			while(curr_priority <= max_priority) {
				if(curr_index < nodes[curr_priority].size()) {
					return true;
				}
				curr_priority++;
				curr_index = 0;
			}
			return false;
		}

		public int getActualPriority() {
			return curr_priority;
		}
		
		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException("Nם more items");
			}
			T item = nodes[curr_priority].get(curr_index);
			curr_index++;
			return item;
		}
	}
	
	
	
}
