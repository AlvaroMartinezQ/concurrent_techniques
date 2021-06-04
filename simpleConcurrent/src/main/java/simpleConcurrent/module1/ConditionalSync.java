package simpleConcurrent.module1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class ConditionalSync {

	private static volatile int printA = 0; // Needs to be equal to 1 to print A
	private static volatile int printD = 0; // Needs to be equal to 1 to print D
	private static volatile int printE = 0; // Needs to be equal to 3 to print E
	
	// Prints a and b
	public static void executorA() {
		while(printA < 1);
		print("A");
		print("B");
		enterMutex("mutex_e");
		printE++;
		exitMutex("mutex_e");
	}

	// Prints c, d, e
	public static void executorB() {
		print("C");
		enterMutex("mutex_a");
		printA++;
		exitMutex("mutex_a");
		while (printD < 1);
		print("D");
		enterMutex("mutex_e");
		printE++;
		exitMutex("mutex_e");
		while(printE < 3);
		print("E");
	}
	
	// Prints f, g
	public static void executorC() {
		print("F");
		enterMutex("mutex_d");
		printD++;
		exitMutex("mutex_d");
		print("G");
		enterMutex("mutex_e");
		printE++;
		exitMutex("mutex_e");
	}
	
	public static void main(String[] args) {
		println(getThreadName() + " STARTING");
		createThread("executorA");
		createThread("executorB");
		createThread("executorC");
		
		startThreadsAndWait();
	}
	
}
