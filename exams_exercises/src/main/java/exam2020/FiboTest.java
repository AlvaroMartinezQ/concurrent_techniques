package exam2020;

import java.util.concurrent.ForkJoinPool;

public class FiboTest {

	public static void main(String[] argv) {
		int n = 10;
		Fibo f = new Fibo(n);
		ForkJoinPool pool = new ForkJoinPool(4);
		Integer res = pool.invoke(f);
		System.out.println("Result: " + res);
	}
	
}
