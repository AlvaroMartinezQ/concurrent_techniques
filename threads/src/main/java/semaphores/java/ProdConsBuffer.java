package semaphores.java;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ProdConsBuffer {
	
	private final int N_SLOTS = 10;
	private int[] buffer = new int[N_SLOTS];
	
	private int insertPos = 0;
	private int readPos = 0;
	private boolean readerBlocked = false;
	private boolean writerBlocked = false;
	private Semaphore exclusion = new Semaphore(1);
	
	private Semaphore blockRead = new Semaphore(0);
	private Semaphore blockWrite = new Semaphore(0);
	
	private void consumer() {
		try {
			Thread.sleep(2000);
			while(true) {
				exclusion.acquire();
				if (insertPos == readPos) {
					// data not ready, wait
					readerBlocked = true;
					exclusion.release();
					blockRead.acquire();
					exclusion.acquire();
				}
				// data is ready
				int value = buffer[readPos];
				System.out.println("Data read from buffer position " + readPos + " is -> " + value);
				if(readPos == N_SLOTS - 1) {
					readPos = 0;
				} else {
					readPos++;
				}
				if (writerBlocked) {
					writerBlocked = false;
					blockWrite.release();
				}
				exclusion.release();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Consumer thread was interrupted!");
		}
	}
	
	private void producer() {
		try {
			while(true) {
				exclusion.acquire();
				if(readPos == insertPos + 1) {
					// data has to be processed
					writerBlocked = true;
					exclusion.release();
					blockWrite.acquire();
					exclusion.acquire();
				}
				// data is ready to be processed
				int value = ThreadLocalRandom.current().nextInt();
				System.out.println("Data inserted to buffer position " + insertPos + " is -> " + value);
				buffer[insertPos] = value;
				if (insertPos == N_SLOTS -1) {
					insertPos = 0;
				} else {
					insertPos++;
				}
				if (readerBlocked) {
					readerBlocked = false;
					blockRead.release();
				}
				exclusion.release();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Producer thread was interrupted!");
		}
	}
	
	public void exec() throws InterruptedException {
		for(int i = 0; i<N_SLOTS; i++) {
			buffer[i] = 0;
		}
		Thread producer = new Thread( () -> producer(), "producer");
		Thread consumer = new Thread( () -> consumer(), "consumer");
		producer.start();
		consumer.start();
		while(producer.isAlive() && consumer.isAlive()) {
			producer.join();
			consumer.join();
		}
	}
	
	public static void main(String[] argv) throws InterruptedException {
		new ProdConsBuffer().exec();
	}
	
}
