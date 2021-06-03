package simpleConcurrent;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class MuseumInfinite {
	
	public static volatile int persons;

	public static void person() {
		while (true) {
			
			enterMutex();
			persons++;
			println("User: inside the museum. We are " + persons + " persons in the museum");
			sleep(3);
			exitMutex();
			
			println("User: Incredible!!");
			
			enterMutex();
			persons--;
			println("User: Bye. There're " + persons + " inside the museum left");
			sleep(3);
			exitMutex();
			
			println("User: having a walk...");
		}
	}
	
	public static void main(String[] args) {
		persons = 0;
		createThreads(3, "person");
		
		startThreadsAndWait();
	}
	
}
