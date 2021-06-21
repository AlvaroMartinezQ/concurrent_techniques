package exam;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

/*
 * Alvaro Martinez Quiroga 50370386H
 */

public class Simulador {
	
	private static Dispensador d;
	
	private static final int N = 5; // gatos
	private static final int M = 3; // voluntarios
	
	private static int gatosTermina = 0;
	
	private static SimpleSemaphore blockGatos = new SimpleSemaphore(0);
	private static SimpleSemaphore datos = new SimpleSemaphore(1);
	private static SimpleSemaphore blockUltimoGato = new SimpleSemaphore(0);
	
	// gatos -----------------------
	
	public static void gato(int id) {
		println("Gato " + id + " online");
		while (true) {
			println("Gato " + id + " quiere comer...");
			d.comer();
			println("Gato " + id + " ha terminado de comer.");
			datos.acquire();
			gatosTermina++;
			if (gatosTermina < N) {
				println("Gato " + id + " esperando a los demas.");
				datos.release();
				vivirGato(id);
				blockGatos.acquire();
				blockUltimoGato.release();
			} else {
				println("Todos los gatos han comido, desbloqueando...");
				gatosTermina = 0;
				blockGatos.release(N - 1);
				blockUltimoGato.acquire(N - 1);
				datos.release();
				vivirGato(id);
			}
		}
	}
	
	public static void vivirGato(int id) {
		println("Gato " + id + " haciendo sus cosas.");
		sleep(1000);
	}
	
	// voluntarios -----------------
	
	public static void voluntario(int id) {
		println("Voluntario " + id + " online");
		while (true) {
			println("Voluntario " + id + " introduciendo comida...");
			sleepRandom(1000);
			d.meterComida();
			sleepRandom(1000);
			println("Voluntario " + id + " ha terminado de introducir comida.");
			vivirVoluntario(id);
		}
	}
	
	public static void vivirVoluntario(int id) {
		println("Voluntario " + id + " haciendo sus cosas.");
		sleep(1000);
	}
	
	// Main -----------------------
	
	public static void main(String[] argv) {
		d = new Dispensador();
		
		for(int i = 0; i < N; i++) {
			createThread("gato", i);
		}
		
		for(int i = 0; i < M; i++) {
			createThread("voluntario", i);
		}
		
		startThreadsAndWait();
	}
	
}
