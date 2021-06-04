package simpleConcurrent.module1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class Museum {
	public static int N_PERSONS = 3;
	
	public static void person(int id) {
		while (true) {
			enterMutex();
			println("User " + id + ": inside the museum");
			sleep(1);
			println("User " + id + ": Incredible!!");
			println("User " + id + ": Bye");
			exitMutex();
			println("User " + id + ": having a walk...");
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < N_PERSONS; i++) {
			createThread("person", i);
		}
		
		startThreadsAndWait();
	}
}
