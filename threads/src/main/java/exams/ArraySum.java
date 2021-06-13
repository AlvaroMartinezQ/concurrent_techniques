package exams;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ArraySum {

	private static final int N_THREADS = 5;
	private static final int M_NUMBERS = 100;
	
	private static float[] array = new float[M_NUMBERS];
	private static float res = 0;
	private static int addedNums = 0;
	private Semaphore dataAccess = new Semaphore(1);
	
	private ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
	
	private CountDownLatch cdl = new CountDownLatch(N_THREADS);
	
	private void add() {
		System.out.println("Adder thread: " + Thread.currentThread().getName() + " online");
		try {
			while(true) {
				dataAccess.acquire();
				if (addedNums == array.length) {
					dataAccess.release();
					cdl.countDown();
					break;
				}
				int pos = addedNums;
				addedNums++;
				dataAccess.release();
				float val = array[pos];
				dataAccess.acquire();
				res += val;
				dataAccess.release();
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	public void exec() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " online");
		try {
			for (int i = 0; i < N_THREADS; i++) {
				executor.submit(() -> add(), "adder-" + i);
			}
			cdl.await();
		} finally {
			executor.shutdown();
		}
		System.out.println("Array add is: " + res);
	}
	
	public static void main(String[] argv) throws InterruptedException {
		for(int i = 0; i < array.length; i++) {
			array[i] = (float) Math.random();
		}
		new ArraySum().exec();
	}
}
