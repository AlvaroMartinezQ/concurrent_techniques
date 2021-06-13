package exams;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Scalextric {
	
	private static final int N = 5;
	private static final int M = 3;
	
	private static int[] tracks = new int[N];
	private static Semaphore[] perms = new Semaphore[N];
	
	private static int carsFinish = 0;
	private static Semaphore finish = new Semaphore(1);
	
	private static ExecutorService executor = Executors.newFixedThreadPool(M);
	
	private void car(int id) {
		int actualTrack = 0;
		while(true) {
			try {
				perms[actualTrack].acquire();
				int pos = (int) (Math.random() * tracks[actualTrack]);
				System.out.println("Car " + id + " going into track: " + actualTrack + " through line: " + pos);
				Thread.sleep((long) (1000 + Math.random() * 3000));
				if(actualTrack == N - 1) {
					finish.acquire();
					perms[actualTrack].release();
					carsFinish++;
					System.out.println("Car " + id + " has finished in position: " + carsFinish);
					finish.release();
					break;
				} else {
					System.out.println("Car " + id + " has passed track: " + actualTrack);
					perms[actualTrack].release();
					actualTrack++;
				}
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void exec() {
		try {
			for(int i = 0; i < M; i++) {
				int id = i;
				executor.submit(() -> car(id), "car-" + id);
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) {
		System.out.println("Circuit -> " + N + " tracks");
		for(int i = 0; i < N; i++) {
			if (i == 0) {
				tracks[i] = M;
				System.out.println("Track -> " + i + " has " + M + " slots");
			} else {
				int slots = (int) (1 + Math.random() * M);
				tracks[i] = slots;
				System.out.println("Track -> " + i + " has " + slots + " slots");
			}
			perms[i] = new Semaphore(1);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Scalextric().exec();
	}

}
