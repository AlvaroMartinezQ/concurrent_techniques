package exam2019;

import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorTasks2 {

	private final int N = 5;
	private final int M = 10;
	
	ExecutorService executor = Executors.newFixedThreadPool(N);
	
	CompletionService<Long> completed = new ExecutorCompletionService<>(executor);
	
	public long task(int id) {
		Random rand = new Random();
		float time = rand.nextFloat();
		try {
			Thread.sleep((long) time);
		} catch (InterruptedException e) { }
		System.out.println("Task " + id + " has been sleeping " + time + " ms.");
		return (long) time;
	}
	
	public void exec() {
		try {
			for(int i = 0; i < M; i++) {
				int id = i;
				completed.submit( () -> task(id) );
			}
			for(int i = 0; i < M; i++) {
				try {
					Future<Long> returned = completed.take();
					System.out.println("Task:" + returned.get());
				} catch (InterruptedException e) { } catch (ExecutionException e) { }
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) {
		new ExecutorTasks2().exec();
	}
	
}
