package mutualexclusion.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
	
	private void reader(Lock readLock) {
		while(true) {
			readLock.lock();
			System.out.println("Read data");
			readLock.unlock();
			System.out.println("Process data");
		}
	}
	
	private void writer(Lock writeLock) {
		while(true) {
			System.out.println("Generate data");
			writeLock.lock();
			System.out.println("Write data");
			writeLock.unlock();
		}
	}
	
	public void exec() {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		Lock readLock = readWriteLock.readLock();
		Lock writeLock = readWriteLock.writeLock();
	
		for (int i = 0; i < 5; i++) {
			new Thread(()->reader(readLock)).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(()->writer(writeLock)).start();
		}
	}
	
	public static void main(String[] args){
		new Database().exec();
	}
	
}
