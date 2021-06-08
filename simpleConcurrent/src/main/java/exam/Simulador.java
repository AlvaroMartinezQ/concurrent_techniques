package exam;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class Simulador {

	/*
	 * Variables
	 */
	private static volatile int N_GATOS = 5;
	private static volatile int M_VOLUNTARIOS = 5;
	
	private static Dispensador dispensador;
	
	private static int nProcesos = 0;
	private static SimpleSemaphore emNProcesos = new SimpleSemaphore(1);
	
	private static SimpleSemaphore esperaGatos = new SimpleSemaphore(0);
	
	private static SimpleSemaphore desbloqueo = new SimpleSemaphore(0);
	
	
	/*
	 * Gato
	 */
	public static void gato() {
		while (true) {
			comerGato();
			
			emNProcesos.acquire();
			nProcesos++;
			if(nProcesos < N_GATOS) {
				emNProcesos.release();
				vivirGato();
				esperaGatos.acquire();
				desbloqueo.release();
			} else {
				nProcesos = 0;
				println("Todos los gatos pueden volver a comer.");
				esperaGatos.release(N_GATOS - 1);
				desbloqueo.acquire(N_GATOS -1);
				emNProcesos.release();
				vivirGato();
			}
		}
	}
	
	public static void comerGato() {
		println("GLlega");
		dispensador.sacarComida();
		println("GEmpieza");
		sleepRandom(5000);
		println("GTermina");
	}
	
	public static void vivirGato() {
		sleepRandom(5000);
		println("GVive");
		sleepRandom(5000);
	}
	
	/*
	 * Voluntario
	 */
	public static void voluntario() {
		while(true) {
			llevarComida();
			vivirVoluntario();
		}
	}
	
	public static void llevarComida() {
		println("VLlega");
		sleepRandom(5000);
		dispensador.introducirComida();
		println("VSeVa");
	}
	
	public static void vivirVoluntario() {
		sleepRandom(5000);
		println("VVive");
		sleepRandom(5000);
	}
	
	/*
	 * Main
	 */
	public static void main(String[] argv) {
		dispensador = new Dispensador();

		createThreads(N_GATOS, "gato");
		createThreads(M_VOLUNTARIOS, "voluntario");
		startThreadsAndWait();

	}
	
}
