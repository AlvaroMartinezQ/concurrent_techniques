package simpleConcurrent.module1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class ClientServerOnePetition {
	static volatile boolean ordered;
	static volatile boolean answered;
	
	static volatile double petition;
	static volatile double response;
	
	
	public static void client() {
		petition = Math.random();
		ordered = true;
		while (!answered);
		println("Answer from server: " + response);
	}
	
	public static void server() {
		while (!ordered);
		println("Petition from client: " + petition);
		response = petition + 1;
		answered = true;
	}

	public static void main(String[] args) {
		
		ordered = false;
		answered = false;
		
		createThread("client");
		createThread("server");

		startThreadsAndWait();
	}
	
}
