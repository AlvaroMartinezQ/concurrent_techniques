package conditionalsync.java;

import java.util.concurrent.CountDownLatch;

public class CountDown {

	private CountDownLatch latchD = new CountDownLatch(3);
	private CountDownLatch latchB = new CountDownLatch(2);
	private CountDownLatch latchG = new CountDownLatch(2);
	private CountDownLatch latchH = new CountDownLatch(2);
	private CountDownLatch latchE = new CountDownLatch(2);
	private CountDownLatch latchC = new CountDownLatch(2);
	
	private void p1() {
		try {
			System.out.println("A");
			latchD.countDown();
			latchB.countDown();
			latchB.await();
			System.out.println("B");
			latchH.countDown();
			latchE.countDown();
			latchC.countDown();
			latchC.await();
			System.out.println("C");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void p2() {
		try {
			latchD.countDown();
			latchD.await();
			System.out.println("D");
			latchB.countDown();
			latchG.countDown();
			latchE.countDown();
			latchE.await();
			System.out.println("E");
			latchC.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void p3() {
		try {
			System.out.println("F");
			latchD.countDown();
			latchG.countDown();
			latchG.await();
			System.out.println("G");
			latchH.countDown();
			latchH.await();
			System.out.println("H");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void exec() {
		new Thread(() -> p1(), "p1").start();
		new Thread(() -> p2(), "p2").start();
		new Thread(() -> p3(), "p3").start();
	}
	
	public static void main(String[] argv) {
		new CountDown().exec();
	}
	
}
