package mutualexclusion.java;

public class Synchronized {
	
	private int persons = 0;
	private Object xLock = new Object(); // Use a generic object as lock
	
	private void person(int id) {
		boolean gift = false;
		try {
			while(true) {
				System.out.println("Person " + id + " wants to enter the museum");
				synchronized (xLock) {
					if (persons == 0) {
						gift = true;
					}
					persons ++;
				}
				if (gift) {
					System.out.println("Person " + id + " inside the museum with gift");
				} else {
					System.out.println("Person " + id + " inside the museum");
				}
				gift = false;
				Thread.sleep(5000);
				synchronized (xLock) {
					persons --;
				}
				System.out.println("Person " + id + " leaving the museum");
				Thread.sleep((long)(Math.random() * 1500));
			}
		} catch (InterruptedException e) {
			System.out.println("Thread " + id + " has been interrupted!");
		}
	}
	
	public void exec() throws InterruptedException {
		Thread person1 = new Thread( () -> person(1), "person 1");
		Thread person2 = new Thread( () -> person(2), "person 2");
		Thread person3 = new Thread( () -> person(3), "person 3");
		
		person1.start();
		person2.start();
		person3.start();
		
		while(person1.isAlive() && person2.isAlive() && person3.isAlive()) {
			person1.join();
			person2.join();
			person3.join();
		}
	}
	
	public static void main(String[] argv) throws InterruptedException {
		new Synchronized().exec();
	}
	
}
