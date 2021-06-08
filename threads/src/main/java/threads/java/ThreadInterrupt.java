package threads.java;

public class ThreadInterrupt {

	private void println(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + ":" + message);
	}
	
	private void msgs() {
		String msg[] = { "Sentence 1", "Sentence 2", "Sentence 3", "Sentence 4" };
		try {
			for (int i = 0; i < msg.length; i++) {
				Thread.sleep(1000);
				println(msg[i]);
			}
		} catch (InterruptedException e) {
			println("MSG thread forced finish!");
		}
	}
	
	public void exec() throws InterruptedException {
		Thread t = new Thread(() -> msgs(), "messages");
		t.start();
		
		int tries = 0;
		while (t.isAlive()) {
			println("Main thread still waiting...");
			t.join(1000); // Wait 1 second
			tries++;
			if (tries >= 2) {
				println("Enough waiting! Main thread terminating messaging thread...");
				t.interrupt();
				// Wait for termination
				t.join();
			}
		}
		
		println("Main thread finally finished!!");
	}
	
	public static void main(String[] argv) throws InterruptedException {
		new ThreadInterrupt().exec();
	}
	
}
