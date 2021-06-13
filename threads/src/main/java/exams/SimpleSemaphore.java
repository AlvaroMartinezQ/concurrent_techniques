package exams;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class SimpleSemaphore {

	private int perms;
	
	public SimpleSemaphore(int perms) {
		this.perms = perms;
	}
	
	public void acquire() {
		boolean go = true;
		while(go) {
			enterMutex();
			if(perms > 0) {
				perms--;
				go = false;
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
