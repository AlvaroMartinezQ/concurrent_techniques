package exams;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PhiloWait {

	private static final int N_PHILOSOPHERS = 5;
	
	private static Semaphore[] forks = new Semaphore[N_PHILOSOPHERS];
	
	private static int completed = 0;
	private static Semaphore varControl = new Semaphore(1);
	
	private static Semaphore block = new Semaphore(0);
	
	private static Semaphore stopBlock = new Semaphore(0);
	
	private ExecutorService executor = Executors.newFixedThreadPool(N_PHILOSOPHERS);
	
	private void philo(int id) {
		while(true) {
			try {
				System.out.println("Philo " + id + " wants to eat");
				if(id == N_PHILOSOPHERS -1) {
					forks[id].acquire();
					System.out.println("Philo " + id + " took fork " + id);
				} else {
					forks[id + 1].acquire();
					System.out.println("Philo " + id + " took fork " + (id + 1));
				}
				if(id == N_PHILOSOPHERS -1) {
					forks[0].acquire();
					System.out.println("Philo " + id + " took fork 0");
				} else {
					forks[id].acquire();
					System.out.println("Philo " + id + " took fork " + id);
				}
				System.out.println("Philo " + id + " eating");
				Thread.sleep((long) (Math.random() * 10000));
				if(id == N_PHILOSOPHERS -1) {
					forks[0].release();
				} else {
					forks[id + 1].release();
				}
				forks[id].release();
				System.out.println("Philo " + id + " leaved its forks");
				
				// wait for all philosophers to eat
				varControl.acquire();
				completed++;
				if(completed == N_PHILOSOPHERS) {
					// Block all others
					System.out.println("Philo " + id + " all philosophers have eaten! Unblocking them...");
					completed = 0;
					block.release(N_PHILOSOPHERS - 1);
					stopBlock.acquire(N_PHILOSOPHERS - 1);
					varControl.release();
				} else {
					varControl.release();
					block.acquire();
					stopBlock.release();
				}
				Thread.sleep((long) (Math.random() * 10000));
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	public void exec() {
		
		for(int i = 0; i < N_PHILOSOPHERS; i++) {
			int id = i;
			executor.submit(() -> philo(id), "philo-" + id);
		}
		
	}
	
	public static void main(String[] argv) {
		for(int i = 0; i < N_PHILOSOPHERS; i++) {
			forks[i] = new Semaphore(1);
		}
		new PhiloWait().exec();
	}
	
}
