package simpleConcurrent.module2;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class FileDownload {

	private static int N_FILES = 3;
	private static int N_FRAGS = 10;
	
	private static int N_THREADS = 5;
	
	private static SimpleSemaphore fragSem = new SimpleSemaphore(1);
	private static int frags = 0;
	
	private static SimpleSemaphore fileSem = new SimpleSemaphore(1);
	private static int files = 0;
	
	private static int blocked = 0;
	
	private static SimpleSemaphore block = new SimpleSemaphore(0);
	
	private static int[] data = new int[N_FRAGS];
	
	public static void printFile() {
		print("File " + files +": [");
		for(int i = 0; i < N_FRAGS; i++) {
			print(data[i] + "-");
		}
		print("]");
		println("");
		println("");
	}
	
	public static void resetFile() {
		for(int i = 0; i < N_FRAGS; i++) {
			data[i] = 0;
		}
	}
	
	public static int downloadData(int numFragment) {
		sleepRandom(1000);
		return numFragment * 2;
	}
	
	public static void downloader() {
		while (true) {
			fileSem.acquire();
			if (files == N_FILES) {
				println("All files downloaded, terminating...");
				fileSem.release();
				// All files OK, terminate
				break;
			}
			fileSem.release();
			fragSem.acquire();
			if (frags == N_FRAGS) {
				if (blocked < N_THREADS - 1) {
					println("Thread blocking and waiting for file ACK...");
					blocked ++;
					fragSem.release();
					block.acquire();
				} else {
					println("File ACK ->");
					printFile();
					fileSem.acquire();
					files++;
					fileSem.release();
					resetFile();
					block.release(blocked);
					blocked = 0;
					frags = 0;
					fragSem.release();
				}
			} else {
				int datad = downloadData(frags);
				data[frags] = datad;
				frags++;
				fragSem.release();
			}
		}
	}
	
	public static void main(String args[]) {
		for (int i = 0; i < N_THREADS; i++) {
			createThread("downloader");
		}
		startThreadsAndWait();
	}
	
}
