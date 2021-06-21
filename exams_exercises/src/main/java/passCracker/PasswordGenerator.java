package passCracker;

import java.util.Random;

public class PasswordGenerator {

	private int len;
	
	//private final String[] options = {"a", "e", "i", "o", "u"};
	private final String[] options = {"a"};
	
	public PasswordGenerator(int l) {
		this.len = l;
	}
	
	public String generate() {
		String pass = "";
		Random rand = new Random();
		for(int i = 0; i < this.len; i++) {
			pass += options[rand.nextInt(this.options.length)];
		}
		System.out.println("Gnerated password is: " + pass);
		return pass;
	}
	
	public int maxOptions() {
		return this.options.length;
	}
	
	public String giveChar(int id) {
		return this.options[id];
	}
	
}
