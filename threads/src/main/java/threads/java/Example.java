package threads.java;

public class Example {
	private volatile boolean produced = false;
	private volatile double product;
	
	public void producer() {
		product = Math.random();
		produced = true;
	}

	public void consumer() {
		while (!produced);
		System.out.println("product: "+product);
	}
	
	public void exec() {
		new Thread( () -> producer() ).start();
		new Thread( () -> consumer() ).start();
	}
		
	public static void main(String[] args) {
		new Example().exec();
	}
}