package simpleConcurrent.module2;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import java.util.Random;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class DataBaseSimulator {
	
	// Simulation of data as an array
	private static int N_DATA = 50;
	private static int[] data = new int[N_DATA];

	private static Random rand = new Random();
	
	private static int readersDB = 0;
	private static int writersDB = 0;
	private static int readersWaiting = 0;
	private static int writersWaiting = 0;
	
	private static SimpleSemaphore control = new SimpleSemaphore(1);
	private static SimpleSemaphore write = new SimpleSemaphore(1);
	
	private static SimpleSemaphore waitRead = new SimpleSemaphore(0);
	private static SimpleSemaphore waitWrite = new SimpleSemaphore(0);
	
	// ------------------------------------------ //
	public static void reader() {
		while (true) {
			startRead();
			sleepRandom(300);
			finishRead();
			println("Processing data");
			sleepRandom(10000);
		}
	}
	
	public static void startRead() {
		control.acquire();
		if (writersDB != 0 && writersWaiting != 0) {
			readersWaiting++;
			control.release();
			waitRead.acquire();
		} else {
			readersDB++;
			int pos = rand.nextInt(N_DATA);
			println("Reading data from DB position: " + pos + ", value -> " + data[pos]);
			control.release();
		}
		
	}
	
	public static void finishRead() {
		control.acquire();
		readersDB--;
		if(readersDB == 0) {
			for(int i = 0; i < writersWaiting; i++) {
				writersDB++;
				waitWrite.release();
			}
			writersWaiting = 0;
		}
		control.release();
	}
	
	// ------------------------------------------ //
	public static void writer() {
		while (true) {
			println("Trying to write data....");
			sleepRandom(2000);
			startWrite();
			println("Writing data");
			sleepRandom(3000);
			finishWrite();
		}
	}
	
	public static void startWrite() {
		control.acquire();
		if (readersDB == 0) {
			writersDB++;
			control.release();
		} else {
			writersWaiting++;
			control.release();
			waitWrite.acquire();
		}
		write.acquire();
		int pos = rand.nextInt(N_DATA);
		data[pos] = rand.nextInt(N_DATA);
		println("Inserting data into DB position: " + pos + ", value -> " + data[pos]);
	}
	
	public static void finishWrite() {
		write.release();
		control.acquire();
		writersDB--;
		if(writersDB == 0) {
			for (int i = 0; i < readersWaiting; i++) {
				readersDB++;
				waitRead.release();
			}
			readersWaiting = 0;
		}
		control.release();
	}
	
	// ------------------------------------------ //
	public static void main(String args[]) {
		// Initialize the data
		for(int i = 0; i < N_DATA; i++) {
			data[i] = 0;
		}
		createThreads(5, "reader");
		createThreads(3, "writer");
		startThreadsAndWait();
	}
	
}
