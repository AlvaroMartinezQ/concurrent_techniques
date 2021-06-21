package passCracker;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class Cracker extends RecursiveTask<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int N_LETTERS = 5;
	
	private int start;
	private int finish;
	
	private String password;
	
	private String part;
	
	PasswordGenerator pg;
	Random rand = new Random();
	
	public Cracker(int s, int f, String p, String pa, PasswordGenerator pg) {
		this.start = s;
		this.finish = f;
		this.password = p;
		this.part = pa;
		this.pg = pg;
	}
	
	@Override
	protected String compute() {
		if(this.finish - this.start > this.N_LETTERS) {
			return this.divideCompute();
		} else {
			return this.computeDirectly();
		}
	}
	
	private String computeDirectly() {
		// Guess the password from this.start to this.finish
		String guess = "";
		while (true) {
			if(guess != "") {
				guess = "";
			}
			for(int i = this.start; i < this.finish; i++) {
				guess += this.pg.giveChar(this.rand.nextInt(this.pg.maxOptions()));
			}
			System.out.println("Trying -> " + guess);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) { }
			if (guess.equals(this.part)) {
				return guess;
			}
		}
	}
	
	private String divideCompute() {
		int mid = (this.finish - this.start) / 2;
		
		String leftPassword = this.part.substring(this.start, mid);
		String rightPassword = this.part.substring(mid, this.finish);
		
		System.out.println("Left: " + leftPassword + " | Right: " + rightPassword);
		
		Cracker left = new Cracker(this.start, this.start + mid, this.password, leftPassword, this.pg);
		Cracker right = new Cracker(mid + this.start, this.finish, this.password, rightPassword, this.pg);
		
		left.fork();
		right.fork();
		
		return left.join() + right.join();
	}

}
