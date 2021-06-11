package datastructures.java.carFactory;

public class Pipeline {

	private static final int PIECES_TYPES = 3;
	private static final int N_ROBOTS = 5;
	
	private Storage store = new Storage(PIECES_TYPES);
	
	private double generatePiece(int type) throws InterruptedException {
		Thread.sleep(1000);
		double piece = type + Math.random();
		System.out.println(Thread.currentThread().getName() + ": " + piece);
		return piece;
	}
	
	private void mountPiece(int type, double val) throws InterruptedException {
		Thread.sleep((long) Math.random() * 1000);
		System.out.println(Thread.currentThread().getName() + "-> mounting: " + val);
	}
	
	public void machine(int id, int pieceType) {
		try {
			while(true) {
				double piece = generatePiece(pieceType);
				store.storePiece(pieceType, piece);
			}
		} catch(InterruptedException e) {
			System.out.println("Machine " + id + " was interrupted while execution!");
		}
	}
	
	public void robot(int id) {
		try {
			while(true) {
				for (int i = 0; i < PIECES_TYPES; i++) {
					double val = store.getPiece(i);
					mountPiece(i, val);
				}
				System.out.println("Robot " + id + " finished assembling a car");
			}
		} catch(InterruptedException e) {
			System.out.println("Robot " + id + " was interrupted while execution!");
		}
	}
	
	public void exec() {
		for(int i = 0; i < PIECES_TYPES; i++) {
			int num = i;
			new Thread(() -> machine(num, num), "machine-" + num).start();
		}
		
		for(int i = 0; i < N_ROBOTS; i++) {
			int num = i;
			new Thread(() -> robot(num), "robot-" + num).start();
		}
	}
	
	public static void main(String[] argv) {
		new Pipeline().exec();
	}
	
}
