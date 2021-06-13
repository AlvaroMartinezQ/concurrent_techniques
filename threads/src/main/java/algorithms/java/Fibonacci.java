package algorithms.java;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private int i;
	
	public Fibonacci(int i) {
		this.i = i;
	}
	
	@Override
	protected Integer compute() {
		if(this.i == 0) return 0;
		if(this.i == 1) return 1;
		return divideCompute();
	}
	
	private Integer divideCompute() {
		Fibonacci n1 = new Fibonacci(i - 1);
		Fibonacci n2 = new Fibonacci(i - 2);
		
		n1.fork();
		n2.fork();
		
		return n1.join() + n2.join();
	}
	
	public static void main(String[] argv) {
		int j = 10;
		Fibonacci n1 = new Fibonacci(j);
		ForkJoinPool pool = new ForkJoinPool(4);
		System.out.println("Fibonacci for " + j + " is: " + pool.invoke(n1));
	}

}
