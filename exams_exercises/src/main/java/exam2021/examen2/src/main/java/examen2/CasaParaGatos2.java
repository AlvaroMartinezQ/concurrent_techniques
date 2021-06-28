package examen2;

/*
 * Alvaro Martinez Quiroga
 */

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CasaParaGatos2 {

	private int N = 10; // cuencos
	private int M = 20; // gatos
	
	private ExecutorService executor = Executors.newFixedThreadPool(N);
	
	public int gato(int id) {
		Random rand = new Random();
		int tiempo = (rand.nextInt(6) + 1) * 1000; // * 1000 por que son ms
		try {
			System.out.println("Gato " + id + " comiendo");
			Thread.sleep(tiempo);
			System.out.println("Gato " + id + " ha terminado");
		} catch (InterruptedException e) { }
		return tiempo;
	}
	
	public void exec() {
		ExecutorCompletionService<Integer> completion = new ExecutorCompletionService<>(executor);
		try {
			for(int i = 0; i < M; i++) {
				int id = i;
				completion.submit( () -> gato(id));
			}
			for(int i = 0; i < M; i++) {
				Future<Integer> resultado = completion.take();
				int tiempo = resultado.get();
				System.out.println(Thread.currentThread().getName() + " - Gato ha terminado! ha estado comiendo: " + tiempo + " ms");
			}
		} catch (InterruptedException | ExecutionException e) {
			
		} finally {
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) {
		new CasaParaGatos2().exec();
	}
	
}
