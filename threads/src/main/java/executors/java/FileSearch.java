package executors.java;

import java.io.File;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearch {

	private static final int N_THREADS = 10;
	
	private static final String fileSearched = "FileSearch.java";
	
	private AtomicInteger numTasks = new AtomicInteger();
	
	private ConcurrentMap<String, String> duplicates = new ConcurrentHashMap<>();
	
	ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
	
	CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
	
	private Semaphore prints = new Semaphore(1);
	
	public void searcher(File root) {
		try {
			if(root.isDirectory()) {
				for(File f: root.listFiles()) {
					if (f.isDirectory()) {
						numTasks.incrementAndGet();
						executor.submit(() -> searcher(f));
					} else {
						prints.acquire();
						System.out.println("Comparing file: " + f.getName());
						if(f.getName().equals(fileSearched)) {
							System.out.println("--> Duplicate file found: ");
							System.out.println(f.getAbsolutePath());
						}
						prints.release();
						
						/*
						String path = duplicates.putIfAbsent(f.getName(), f.getAbsolutePath());
						
						if(path != null) {
							System.out.println("--> Duplicate file found: ");
							System.out.println(path);
							System.out.println(f.getAbsolutePath());
						}
						*/
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (numTasks.decrementAndGet() == 0) {
			executor.shutdown();
		}
	}
	
	public void exec() {
		File f1 = new File("./src/main/java");
		// File f2 = new File("../");
		
		System.out.println("Searching in: " + f1.getAbsolutePath());
		
		numTasks.incrementAndGet();
		executor.submit(() -> searcher(f1));
		/*
		numTasks.incrementAndGet();
		executor.submit(() -> searcher(f2));
		*/
		
		try {
			executor.awaitTermination(50, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] argv) {
		new FileSearch().exec();
	}
	
}
