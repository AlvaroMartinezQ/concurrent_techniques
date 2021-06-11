package datastructures.java.carFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Storage {

	private static final int MAX_PIECES = 5;
	
	private List<BlockingQueue<Double>> storage;
	
	public Storage(int types) {
		storage = new ArrayList<BlockingQueue<Double>>();
		for(int i = 0; i<types; i++) {
			storage.add(new LinkedBlockingQueue<Double>(MAX_PIECES));
		}
	}
	
	public void storePiece(int type, double val) throws InterruptedException {
		storage.get(type).add(val);
	}
	
	public double getPiece(int type) throws InterruptedException {
		return storage.get(type).take();
	}
	
}
