package conditionalsync.java;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CycBarr {

	private static int N_THREADS = 5;
	private static int N_TO_COMPLETE = 2;
	private CyclicBarrier cb;
	private int completed = 0;
	
	public void thread() {
		while (completed < N_TO_COMPLETE) {
			System.out.println("Thread " + Thread.currentThread().getName() + " entering the barrier");
			try {
				Thread.sleep((long) (Math.random() * 10000));
				cb.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void unblockBarrier() {
		System.out.println("Barrier has been unblocked!");
		completed++;
	}
	
	public void exec() {
		cb = new CyclicBarrier(N_THREADS, () -> unblockBarrier());
		
		for (int i = 0; i < N_THREADS; i++) {
			new Thread(() -> thread(), "thread_" + i).start();
		}
	}
	
	public static void main(String[] argv) {
		new CycBarr().exec();
	}
	
}
