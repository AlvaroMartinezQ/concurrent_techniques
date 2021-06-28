package examen1;

/*
 * Alvaro Martinez Quiroga
 */

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import java.util.Random;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class Pool {

	// datos -----------------------------------------------
	
	private static final int MAX_PROC = 50;
	
	private static final int MAX_LANZADORES = 10;
	private static final int MAX_EJECUTRADORES = 10;
	
	private static SimpleSemaphore maxProcesos = new SimpleSemaphore(MAX_PROC);
	
	private static Proceso[] procesosArray = new Proceso[MAX_PROC];
	private static SimpleSemaphore emArray= new SimpleSemaphore(1);
	
	private static int ultimoMetido = 0;
	private static SimpleSemaphore emUltimo = new SimpleSemaphore(1);
	
	private static int posMeter = 0;
	private static SimpleSemaphore emMeter = new SimpleSemaphore(1);
	
	// metodos del pool -----------------------------------------------
	
	public static void procesoAPool(int id, long duracion, Proceso p) {
		maxProcesos.acquire();
		introduceProceso(p);
	}
	
	public static Proceso sacarDePool() {
		Proceso procEjecutar = devuelveProceso();
		if (procEjecutar == null) return null;
		
		println(getThreadName() + " - Proceso: " + procEjecutar.getId() + " comienza su ejecucion");
		println(getThreadName() + " - Durmiendo: " + procEjecutar.getTiempoEjecucion());
		sleep(procEjecutar.getTiempoEjecucion() * 1000); // se multiplica por 100 por que son ms
		println(getThreadName() + " - Proceso: " + procEjecutar.getId() + " finaliza su ejecucion");
		
		maxProcesos.release();
		return procEjecutar;
	}
	
	// metodos de los hilos -----------------------------------------------
	
	public static void lanzador(int id) {
		println("Lanzador " + id + " online");
		Random rand = new Random();
		while(true) {
			// generar id y tiempo
			int i = rand.nextInt() * 1000;
			long tiempoProceso = rand.nextInt(6) + 1;
			
			while(i < 0 || tiempoProceso < 0) {
				if(i < 0) i = rand.nextInt() * 1000;
				if(tiempoProceso < 0) tiempoProceso = rand.nextInt(6) + 1;
			}
			
			Proceso nuevoProceso = new Proceso(i, tiempoProceso);
			
			procesoAPool(i, tiempoProceso, nuevoProceso);
			// el siguiente sleep es para ver la ejecucion
			sleepRandom(5000);
		}
	}
	
	public static void ejecutor(int id) {
		println("Ejecutor " + id + " online");
		while(true) {
			sacarDePool();
			// el siguiente sleep es para ver la ejecucion
			sleepRandom(5000);
		}
	}
	
	// metodos para introducir y recuperar procesos -----------------------------------------------
	
	public static void introduceProceso(Proceso p) {
		emArray.acquire();
		emMeter.acquire();
		if(posMeter == MAX_PROC) {
			posMeter = 0;
		}
		procesosArray[posMeter] = p;
		posMeter++;
		emMeter.release();
		emArray.release();
	}
	
	public static Proceso devuelveProceso() {
		emArray.acquire();
		emUltimo.acquire();
		if(ultimoMetido == MAX_PROC) {
			ultimoMetido  = 0;
		}
		Proceso p = procesosArray[ultimoMetido];
		procesosArray[ultimoMetido] = null;
		ultimoMetido++;
		emUltimo.release();
		emArray.release();
		return p;
	}
	
	// main -----------------------------------------------
	
	public static void main(String[] argv) {
		for(int i = 0; i < MAX_PROC; i++) {
			procesosArray[i] = null;
		}
		
		for(int i = 0; i < MAX_LANZADORES; i++) {
			createThread("lanzador", i);
		}
		
		for(int i = 0; i < MAX_EJECUTRADORES; i++) {
			createThread("ejecutor", i);
		}
		
		startThreadsAndWait();
		
	}
	
}
