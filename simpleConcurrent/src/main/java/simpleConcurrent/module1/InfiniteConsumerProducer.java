package simpleConcurrent.module1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class InfiniteConsumerProducer {
	static volatile double product;
	static volatile boolean produced;
	
	public static void producer() {
		while(true) {
			product = Math.random();
			produced = true;
		}
	}
	
	public static void consumer() {
		while(true) {
			while(!produced);
			println("Product: " + product);
			produced = false;
		}
	}
	
	public static void main(String[] args) {
		produced = false;
		
		createThread("producer");
		createThread("consumer");

		startThreadsAndWait();
	}
	
}
