package passCracker;

import java.util.concurrent.ForkJoinPool;

public class Main {

	private static final int P_LENGTH = 10;
	
	private static final int M = 4;
	
	public static void main(String[] argv) {
		PasswordGenerator pg = new PasswordGenerator(P_LENGTH);
		String password = pg.generate();
		
		// To read the password
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) { }
		
		Cracker c = new Cracker(0, P_LENGTH, password, password, pg);
		
		ForkJoinPool pool = new ForkJoinPool(M);
		
		System.out.println("Password guessed! Result -> " + pool.invoke(c));
	}
	
}
