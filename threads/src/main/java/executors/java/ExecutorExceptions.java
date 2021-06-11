package executors.java;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorExceptions {

	private static final int N_THREADS = 10;
	
	ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
	
	CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
	
	public String task(int id) {
		try {
			Thread.sleep((long) Math.random() * 500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random() < 0.98) {
			return "Task " + id + " completed correctly";
		}
		throw new RuntimeException("Task " + id + " didn't complete correctly!");
	}
	
	public void exec() throws InterruptedException, ExecutionException {
		try {
			for(int i = 0; i < N_THREADS; i++) {
				int num = i;
				completionService.submit(() -> task(num));
			}
			for(int i = 0; i < N_THREADS; i++) {
				Future<String> response = completionService.take();
				System.out.println("Task: " + response.get());
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) throws InterruptedException, ExecutionException {
		new ExecutorExceptions().exec();
	}
	
}
