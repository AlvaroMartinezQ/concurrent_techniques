package simpleConcurrent.module2;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class Philosophers {

	private static int N_PHILOSOPHERS = 5;
	
	private static SimpleSemaphore[] ableToEat = new SimpleSemaphore[N_PHILOSOPHERS];
	
	public static void philosopher(int id) {
		while(true) {
			println("Philosopher " + id + " thinking...");
			sleepRandom(1000);
			println("Philosopher " + id + " is hungry...");
			
			// To avoid inter-blocking the last philosopher takes the sticks in the inverse order
			if (id == N_PHILOSOPHERS - 1) {
				// Philosopher number 4 takes the stick number 4 first
				ableToEat[id].acquire();
				println("Philosopher " + id + " took stick number " + id);
			} else {
				ableToEat[id + 1].acquire();
				println("Philosopher " + id + " took stick number " + (id + 1));
			}
			
			if (id == N_PHILOSOPHERS - 1) {
				ableToEat[0].acquire();
				println("Philosopher " + id + " took stick number 0");
			} else {
				ableToEat[id].acquire();
				println("Philosopher " + id + " took stick number " + id);
			}
			
			println("Philosopher " + id + " eating.");
			sleep(2000);
			println("Philosopher " + id + " has finished eating");
			
			if (id == N_PHILOSOPHERS - 1) {
				ableToEat[0].release();
			} else {
				ableToEat[id + 1].release();
			}
			ableToEat[id].release();
		}
	}
	
	public static void main(String[] argv) {
		for(int i = 0; i < N_PHILOSOPHERS; i++) {
			ableToEat[i] = new SimpleSemaphore(1);
			createThread("philosopher", i);
		}
		startThreadsAndWait();
	}
	
}
