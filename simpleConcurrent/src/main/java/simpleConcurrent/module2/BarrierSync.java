package simpleConcurrent.module2;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class BarrierSync {
	
	private static int N_THREADS = 10;
	
	private static SimpleSemaphore sem = new SimpleSemaphore(1);
	private static int completed = 0;
	private static SimpleSemaphore block = new SimpleSemaphore(0);
	
	public static void thread(int id) {
		// println("Thread: " + id + ", online");
		while (true) {
			print("A");
			sem.acquire();
			completed++;
			if (completed == 9) {
				println("");
				block.release(completed);
				completed = 0;
				sem.release();
			} else {
				sem.release();
				block.acquire();
			}
			print("B");
			sleep(5000);
		}
	}
	
	public static void main(String args[]) {
		for (int i = 0; i < N_THREADS; i++) {
			createThread("thread", i);
		}
		startThreadsAndWait();
	}
	
}
