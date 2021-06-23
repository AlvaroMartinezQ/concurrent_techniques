package exam2019;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTasks1 {

	private final int N = 5;
	private final int M = 10;
	
	ExecutorService executor = Executors.newFixedThreadPool(N);
	
	public void task(int id) {
		try {
			Thread.sleep((long) Math.random() * 1000);
			System.out.println("Task: " + id + " finished");
		} catch (InterruptedException e) {
			
		}
	}
	
	public void exec() {
		try {
			for(int i = 0; i < M; i++) {
				int id = i;
				executor.submit( () -> task(id) );
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) {
		new ExecutorTasks1().exec();
	}
	
}
