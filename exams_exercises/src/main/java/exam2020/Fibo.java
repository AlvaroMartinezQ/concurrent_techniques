package exam2020;

import java.util.concurrent.RecursiveTask;

public class Fibo extends RecursiveTask<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int n;
	
	public Fibo(int n) {
		this.n = n;
	}
	
	@Override
	protected Integer compute() {
		if(this.n == 0) return 0;
		if(this.n == 1) return 1;
		return divide();
	}
	
	private Integer divide() {
		Fibo n1 = new Fibo(n - 1);
		Fibo n2 = new Fibo(n - 2);
		
		n1.fork();
		n2.fork();
		
		return (n1.join() + n2.join());
	}

}
