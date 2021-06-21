package passCracker;

public class Main {

	private static final int P_LENGTH = 10;
	
	public static void main(String[] argv) {
		PasswordGenerator pg = new PasswordGenerator(P_LENGTH);
		String password = pg.generate();
		String guess = "";
		
		// To read the password
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) { }
		
		Cracker c = new Cracker(0, P_LENGTH, password, password, pg);
		
		guess = c.compute();
		
		System.out.println("Guessed password! Password is -> " + guess);
	}
	
}
