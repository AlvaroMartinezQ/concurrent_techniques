package exams;

import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class PassCracker extends RecursiveTask<Boolean> {
	
	private static final int M = 3;
	
	private static final char[] letters = {'a', 'e', 'i', 'o', 'u', '1', '2', '3', '4', '5'};
	private static int P = 2;
	private static String password = "";
	private static final int MAX_CHARS = 5;
	
	private static AtomicInteger iters = new AtomicInteger(0);
	
	private char[] guessing;
	private int lower;
	private int upper;
	
	private String guess = "";
	private String part = "";
	
	public static void main(String[] argv) {
		Random rand = new Random();
		int len = rand.nextInt(P);
		for(int i = 0; i < len; i++) {
			int pos = rand.nextInt(letters.length-1);
			String letter = "" + letters[pos];
			password += letter;
		}
		System.out.println("Password length:" + len + ", is: " + password);
		iters.set((int) len / M);
		
		PassCracker pc = new PassCracker(0, len-1);
		pc.compute();
	}

	public PassCracker(int lower, int upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	@Override
	protected Boolean compute() {
		int len = upper - lower;
		if (len <= MAX_CHARS) {
			return computeDirectly();
		} else {
			return divideCompute();
		}
	}
	
	private boolean computeDirectly() {
		Random rand = new Random();
		for(int i = lower; i < upper; i++) {
			part = part + password.charAt(i);
			System.out.println(password.charAt(i));
		}
		System.out.println(part);
		boolean finished = false;
		while(!finished) {
			for(int i = 0; i < part.length(); i++) {
				int pos = rand.nextInt(letters.length-1);
				String letter = "" + letters[pos];
				System.out.println(letter);
				guess += letter;
			}
			System.out.println("Testing: " + guess + ", for: " + part);
			if(guess.equals(part)) {
				System.out.println("Guessed password from " + lower + " to " + upper + ": " + guess);
				finished = true;
			}
		}
		return true;
	}
	
	private boolean divideCompute() {
		int mid = (upper - lower) / 2;
		
		PassCracker left = new PassCracker(lower, lower + mid);
		PassCracker right = new PassCracker(mid + lower, upper);
		
		left.fork();
		right.fork();
		
		return (left.join() && right.join());
	}
}
