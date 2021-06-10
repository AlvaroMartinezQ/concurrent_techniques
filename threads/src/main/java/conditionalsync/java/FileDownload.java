package conditionalsync.java;

import java.util.concurrent.CyclicBarrier;

public class FileDownload {

	private static final int N_FILES = 2;
	private static final int N_FRAGMENTS = 10;
	private static final int N_THREADS = 3;

	private int[] file = new int[N_FRAGMENTS];

	private Object nextFragmentLock = new Object();
	private int nextFragment = 0;

	private CyclicBarrier barrier;
	
	private int downloadData(int numFragment) {
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted!");
		}
		return numFragment * 2;
	}

	private void printFile() {
		System.out.println("--------------------------------------------------");
		System.out.print("File = [");
		for (int i = 0; i < N_FRAGMENTS; i++) {
			System.out.print(file[i] + ",");
		}
		System.out.println("]");
	}
	
	public void downloader() {
		for (int i = 0; i < N_FILES; i++) {

			downloadFragments();

			try {
				System.out.println(Thread.currentThread().getName() + "waiting the next file");
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void downloadFragments() {
		do {
			int frag = 0;
			
			synchronized(nextFragmentLock) {
				if (nextFragment < N_FRAGMENTS) {
					frag = nextFragment;
					nextFragment++;
				} else {
					break;
				}
				System.out.println(Thread.currentThread().getName() + " starts downloading fragment " + frag);
				int data = downloadData(frag);
				System.out.println(Thread.currentThread().getName() + " ends downloading fragment " + frag);
				file[frag] = data;
			}
		} while(true);
	}
	
	private void fileRefresh() {
		printFile();
		// Reset attributes
		nextFragment = 0;
		file = new int[N_FRAGMENTS];
	}
	
	public void exec() {

		barrier = new CyclicBarrier(N_THREADS, () -> fileRefresh());

		for (int i = 0; i < N_THREADS; i++) {
			new Thread(() -> downloader(), "Downloader_" + i).start();
		}
	}

	public static void main(String[] args) {
		new FileDownload().exec();
	}
	
}
