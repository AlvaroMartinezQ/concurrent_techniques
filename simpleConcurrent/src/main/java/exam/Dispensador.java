package exam;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class Dispensador {

	private volatile int P_PORCIONES = 10;
	
	private SimpleSemaphore porciones = new SimpleSemaphore(0);
	
	private SimpleSemaphore huecosLibres = new SimpleSemaphore(P_PORCIONES);
	
	public void introducirComida() {
		huecosLibres.acquire();
		porciones.release();
	}
	
	public void sacarComida() {
		porciones.acquire();
		huecosLibres.release();
	}
	
}
