package simpleConcurrent.module1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class ProducerConsumer {

	static volatile boolean produced;
	static volatile double product;

	public static void producer() {
		
		product = Math.random();
		produced = true;
	}

	public static void consumer() {
		
		while (!produced);
		print("Product: "+product);
	}

	public static void main(String[] args) {

		produced = false;

		createThread("producer");
		createThread("consumer");

		startThreadsAndWait();
	}
}
