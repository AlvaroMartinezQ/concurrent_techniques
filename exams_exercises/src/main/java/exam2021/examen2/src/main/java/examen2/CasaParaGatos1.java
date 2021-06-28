package examen2;

/*
 * Alvaro Martinez Quiroga
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CasaParaGatos1 {

	private int N = 10; // cuencos
	private int M = 20; // gatos
	
	private ExecutorService executor = Executors.newFixedThreadPool(N);
	
	public void gato(int id) {
		try {
			Random rand = new Random();
			int tiempo = (rand.nextInt(6) + 1) * 1000; // * 1000 por que son ms
			System.out.println("Gato " + id + " comiendo");
			Thread.sleep(tiempo);
			System.out.println("Gato " + id + " ha terminado. Ha estado comiendo: " + tiempo + " ms");
		} catch (Exception e) { }
	}
	
	public void exec() {
		try {
			for(int i = 0; i < M; i++) {
				int id = i;
				executor.submit( () -> gato(id));
			}
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) {
		new CasaParaGatos1().exec();
	}
	
}
