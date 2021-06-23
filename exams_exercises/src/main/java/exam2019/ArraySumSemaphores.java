package exam2019;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ArraySumSemaphores {

	private final int N = 2;
	private final int M = 10;
	
	private float[] array = new float[M];
	private float counter = 0;
	private int pos = 0;
	
	private Semaphore emPos = new Semaphore(1);
	private Semaphore emCount = new Semaphore(1);
	
	private Semaphore awaitTermination = new Semaphore(0);
	
	ExecutorService executor = Executors.newFixedThreadPool(N);
	
	public void adder() {
		boolean finished = false;
		int myPos = 0;
		try {
			while(!finished) {
				emPos.acquire();
				myPos = pos;
				if(myPos == array.length) {
					finished = true;
					emPos.release();
					awaitTermination.release();
				} else {
					pos++;
					emPos.release();
					emCount.acquire();
					counter += array[myPos];
					emCount.release();
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public void exec() {
		Random rand = new Random();
		System.out.print("Array -> [ ");
		for(int k = 0; k < M; k++) {
			array[k] = rand.nextFloat() * 10;
			System.out.print(array[k] + " - ");
		}
		System.out.println("]");
		
		try {
			for(int i = 0; i < N; i++) {
				executor.submit( () -> adder() );
			}
			awaitTermination.acquire(N);
		} catch (InterruptedException e){
			
		} finally {
			executor.shutdown();
		}
		System.out.println("RESULT IS: " + counter);
	}
	
	public static void main(String[] argv) {
		new ArraySumSemaphores().exec();
	}
	
}
