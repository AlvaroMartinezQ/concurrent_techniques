package arraySearch;

import java.util.concurrent.RecursiveTask;

public class ArraySearch extends RecursiveTask<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int startPos;
	private int finishPos;
	
	private int number;
	
	private int[] array;
	
	private final int MAX_POS = 5;
	
	public ArraySearch(int start, int finish, int n, int[] arr) {
		this.startPos = start;
		this.finishPos = finish;
		this.array = arr;
		this.number = n;
	}
	
	@Override
	protected Integer compute() {
		if(this.finishPos - this.startPos > this.MAX_POS) {
			return divideSubTasks();
		} else {
			return computeDirectly();
		}
	}
	
	private int computeDirectly() {
		for (int i = this.startPos; i < this.finishPos; i++) {
			if(this.array[i] == this.number) {
				return i;
			}
		}
		return -1;
	}
	
	private int divideSubTasks() {
		int mid = (this.finishPos + this.startPos) / 2;
		
		ArraySearch left = new ArraySearch(this.startPos, mid, this.number, this.array);
		ArraySearch right = new ArraySearch(mid + 1, this.finishPos, this.number, this.array);
		
		left.fork();
		right.fork();
		
		int l = left.join();
		int r = right.join();
		
		if (l!=-1) {
			return l;
		} else if (r!=-1) {
			return r;
		}
		
		return -1;
	}

}
