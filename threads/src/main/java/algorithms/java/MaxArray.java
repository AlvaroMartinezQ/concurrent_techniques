package algorithms.java;

import java.util.concurrent.RecursiveTask;

public class MaxArray extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] data;
	private int start;
	private int end;
	
	public MaxArray(int[] data, int start, int end) {
		this.data = data;
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		int length = end - start;
		if(length < 5) {
			return computeDirectly();
		} else {
			return subTasks();
		}
	}
	
	private int computeDirectly() {
		int max = 0;
		
		for(int i = start; i < end; i++) {
			if (data[i] > max) {
				max = data[i];
			}
		}
		
		return max;
	}
	
	private int subTasks() {
		int mid = (end - start) / 2;
		
		MaxArray left = new MaxArray(data, start, start + mid);
		MaxArray right = new MaxArray(data, mid + start, end);
		
		left.fork();
		right.fork();
		
		return Math.max(left.join(), right.join());
	}
	
}
