package exams;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorMethod {

	private static int N_THREADS = 10;
	
	private ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
	
	CompletionService<Float> completionService = new ExecutorCompletionService<>(executor);
	
	private float execute(int id) {
		float time = (float) (Math.random() * 5000);
		try {
			Thread.sleep((long) time);
			System.out.println("My id is: " + id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	private void exec() throws InterruptedException, ExecutionException {
		try {
			for(int i = 0; i < N_THREADS; i++) {
				int id = i;
				executor.submit(() -> execute(id), "executor_" + id);
			}
			for(int i = 0; i < N_THREADS; i++) {
				Future<Float> response = completionService.take();
				System.out.println("Task: " + response.get());
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) throws InterruptedException, ExecutionException {
		new ExecutorMethod().exec();
	}
	
}
