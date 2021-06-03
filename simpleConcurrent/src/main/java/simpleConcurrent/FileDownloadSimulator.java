package simpleConcurrent;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class FileDownloadSimulator {

	private static final int N_FRAGMENTS = 10;
	private static final int N_THREADS = 3;

	private static volatile int[] file = new int[N_FRAGMENTS];
	
	private static SimpleSemaphore data = new SimpleSemaphore(1);
	private static int dataPosition = 0;
	private static SimpleSemaphore finished = new SimpleSemaphore(1);
	private static int finishedThreads = 0;
	
	private static int downloadData(int numFragment) {
		sleepRandom(1000);
		return numFragment * 2;
	}
	
	private static void showFile() {
		println("--------------------------------------------------");
		print("File = [");
		for (int i = 0; i < N_FRAGMENTS; i++) {
			print(file[i] + ",");
		}
		println("]");
	}
	
	// Download the data, access to the array
	public static void data(int pos) {
		int d = downloadData(pos);
		file[pos] = d;
	}
	
	// Threads main method
	public static void downloader() {
		println(getThreadName() + " online");
		while (true) {
			data.acquire();
			if (dataPosition == N_FRAGMENTS - 1) {
				data.release();
				finished.acquire();
				finishedThreads++;
				if (finishedThreads == N_THREADS) {
					// last thread prints the file
					finished.release();
					showFile();
				} else {
					finished.release();
					println(getThreadName() + " waiting for termination...");
				}
				break;
			}
			
			dataPosition++;
			int pos = dataPosition;
			data.release();
			data(pos);
		}
	}
	
	public static void main(String[] args) {
		println(getThreadName() + " STARTING DOWNLOAD");
		createThreads(N_THREADS, "downloader");
		
		startThreadsAndWait();
	}
	
}
