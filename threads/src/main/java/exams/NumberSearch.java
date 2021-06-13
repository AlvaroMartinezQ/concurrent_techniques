package exams;

import java.util.concurrent.RecursiveTask;

public class NumberSearch extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private static final int MAX_SEARCH = 5;
	
	private int[] array;
	private int min;
	private int max;
	
	private int number;
	
	public NumberSearch(int[] array, int posMin, int posMax, int num) {
		this.array = array;
		this.min = posMin;
		this.max = posMax;
		this.number = num;
	}
	
	@Override
	protected Integer compute() {
		int size = max - min + 1;
		if (size <= MAX_SEARCH) {
			return computeDirectly();
		} else {
			return divideCompute();
		}
	}
	
	private int computeDirectly() {
		for(int i = min; i < max; i++) {
			if(this.array[i] == this.number) {
				return i;
			}
		}
		// Not found in the given limits
		return -1;
	}
	
	private int divideCompute() {
		int mid = (min + max) / 2;
		
		NumberSearch left = new NumberSearch(this.array, this.min, mid, this.number);
		NumberSearch right = new NumberSearch(this.array, mid + 1, this.max, this.number);
		
		left.fork();
		right.fork();
		
		int res1 = left.join();
		int res2 = right.join();
		if(res1 != -1) return res1;
		if(res2 != -1) return res2;
		return -1;
	}
	
}
