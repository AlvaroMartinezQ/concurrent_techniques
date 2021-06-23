package exam2018;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PhilosWait {

	private final int N_PHILOS = 5;
	private Semaphore[] forks = new Semaphore[N_PHILOS];
	
	private CyclicBarrier cb = new CyclicBarrier(N_PHILOS);
	
	private ExecutorService executor = Executors.newFixedThreadPool(N_PHILOS);
	
	private void println(String txt) {
		System.out.println(txt);
	}
	
	public void philo(int id) {
		println("Philo " + id + " online.");
		while(true) {
			try {
				Thread.sleep(500);
				println("Philo " + id + " wants to eat...");
				if(id == N_PHILOS - 1) {
					// pick right fork
					forks[0].acquire();
					println("Philo " + id + " took fork 0.");
				} else {
					// pick left fork
					forks[id].acquire();
					println("Philo " + id + " took fork " + id + ".");
				}
				
				if (id == N_PHILOS - 1) {
					forks[id].acquire();
					println("Philo " + id + " took fork " + id + ".");
				} else {
					forks[id + 1].acquire();
					println("Philo " + id + " took fork " + (id + 1) + ".");
				}
				
				println("Philo " + id + " is eating...");
				Thread.sleep((long) (Math.random() * 5000));
				
				println("Philo " + id + " finished eating, leaving used forks...");
				
				if (id == N_PHILOS - 1) {
					forks[0].release();
				} else {
					forks[id + 1].release();
				}
				forks[id].release();
				
				println("Philo " + id + " finally leaved its forks.");
				
				Thread.sleep(1000);
				
				println("Philo " + id + " waiting for the rest.");
				cb.await();
				
			} catch (Exception e) {
				
			}
		}
	}
	
	public void exec() {
		for(int i = 0; i < this.N_PHILOS; i++) {
			forks[i] = new Semaphore(1);
		}
		
		try {
			for(int i = 0; i < this.N_PHILOS; i++) {
				int id = i;
				executor.submit( () -> philo(id));
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main (String[] argv) {
		new PhilosWait().exec();
	}
	
}
