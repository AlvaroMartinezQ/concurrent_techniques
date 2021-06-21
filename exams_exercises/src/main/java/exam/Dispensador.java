package exam;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class Dispensador {
	private final int P = 10;
	
	private SimpleSemaphore sacarComidaSem = new SimpleSemaphore(0);
	
	private SimpleSemaphore meterComidaSem = new SimpleSemaphore(P);
	
	public Dispensador() {
		
	}
	
	public void comer() {
		sacarComidaSem.acquire();
		meterComidaSem.release();
	}
	
	public void meterComida() {
		meterComidaSem.acquire();
		sacarComidaSem.release();
	}
	
}
