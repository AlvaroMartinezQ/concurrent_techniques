package simpleConcurrent.module1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class MuseumGift {
	private static volatile int N_PERSONS = 3;
	
	private static volatile int persons;
	
	public static void person (int id) {
		while (true) {
			enterMutex();
			persons++;
			println("User: inside the museum. We are " + persons + " persons in the museum");
			if (persons == 1) {
				exitMutex();
				println("User: I have a gift!");
			} else {
				exitMutex();
				println("User: I don't have a gift :(");
			}
			
			sleepRandom(5000);
			println("User: Incredible!!");
			
			enterMutex();
			persons--;
			println("User: Bye. There're " + persons + " persons inside the museum left");
			sleepRandom(5000);
			exitMutex();
			
			println("User: having a walk...");
		}
	}
	
	public static void main(String[] args) {
		
		persons = 0;
		
		for (int i = 0; i < N_PERSONS; i++) {
			createThread("person", i);
		}
		
		startThreadsAndWait();
	}
}
