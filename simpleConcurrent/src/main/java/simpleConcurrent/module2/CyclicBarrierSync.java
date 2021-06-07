package simpleConcurrent.module2;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class CyclicBarrierSync {
	
	private static SimpleSemaphore sem = new SimpleSemaphore(1);
	private static int blocked = 0;
	private static SimpleSemaphore block = new SimpleSemaphore(0);
	
	public static void printA() {
		while (true) {
			print("A");
			sem.acquire();
			blocked++;
			sem.release();
			block.acquire();
		}
	}
	
	public static void printB() {
		while (true) {
			print("B");
			sem.acquire();
			blocked++;
			sem.release();
			block.acquire();
		}
	}
	
	public static void printC() {
		while (true) {
			print("C");
			sem.acquire();
			blocked++;
			sem.release();
			block.acquire();
		}
	}
	
	public static void printD() {
		while (true) {
			print("D");
			sem.acquire();
			blocked++;
			sem.release();
			block.acquire();
		}
	}
	
	public static void printSpace() {
		while (true) {
			sem.acquire();
			if (blocked < 4) {
				// Don't do anything
				sem.release();
			} else {
				print("-");
				sleep(3000);
				block.release(blocked);
				blocked = 0;
				sem.release();
			}
		}
	}
	
	public static void main(String args[]) {
		createThread("printA");
		createThread("printB");
		createThread("printC");
		createThread("printD");
		createThread("printSpace");
		startThreadsAndWait();
	}
	
}
