package simpleConcurrent.module2;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class TrainLine {

	private static final int NUM_TRAINS = 5;
	
	private static SimpleSemaphore pathA = new SimpleSemaphore(1);
	private static SimpleSemaphore pathB = new SimpleSemaphore(1);
	private static SimpleSemaphore pathC = new SimpleSemaphore(1);
	
	public static void train(int trainNum) {
		pathA.acquire();
		sleepRandom(500);
		pathA(trainNum);
		pathA.release();
		pathB.acquire();
		sleepRandom(500);
		pathB(trainNum);
		pathB.release();
		pathC.acquire();
		sleepRandom(500);
		pathC(trainNum);
		pathC.release();
		println(getThreadName() + " is on destination.");
	}
	
	private static void pathA(int trainNum) {
		println("Path A train: " + trainNum);
		sleepRandom(500);
		println("Leaving path A train: " + trainNum);
	}
	
	private static void pathB(int trainNum) {
		println("Path B train: " + trainNum);
		sleepRandom(500);
		println("Leaving path B train: " + trainNum);
	}
	
	private static void pathC(int trainNum) {
		println("Path C train: " + trainNum);
		sleepRandom(500);
		println("Leaving path C train: " + trainNum);
	}

	public static void main(String args[]) {
		for (int i = 0; i < NUM_TRAINS; i++) {
			createThread("train", i);
		}
		startThreadsAndWait();
	}

}
