package exam2018;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class SimpleSemaphore {

	private int perms;
	
	public SimpleSemaphore (int perms) {
		this.perms = perms;
	}
	
	public void acquire() {
		while (true) {
			enterMutex();
			if(perms != 0) {
				perms--;
				exitMutex();
				break;
			}
			exitMutex();
		}
	}
	
	public void release() {
		enterMutex();
		perms++;
		exitMutex();
	}
	
}
