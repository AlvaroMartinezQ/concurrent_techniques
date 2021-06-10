package conditionalsync.java;

import java.util.concurrent.Exchanger;

public class ClientProxyServer {
	private Exchanger<Double> exchangerCP = new Exchanger<Double>();
	private Exchanger<Double> exchangerPS = new Exchanger<Double>();
	
	private void client() {
		System.out.println("Client online");
		while(true) {
			try {
				System.out.println(Thread.currentThread().getName() + ", client msg send to proxy");
				exchangerCP.exchange(Math.random());
				double proxyMsg = exchangerCP.exchange(null);
				System.out.println(Thread.currentThread().getName() + ", final msg: " + proxyMsg);
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}
	}
	
	private void proxy() {
		System.out.println("Proxy online");
		while(true) {
			try {
				double clientMsg = exchangerCP.exchange(null);
				System.out.println(Thread.currentThread().getName() + ", client msg: " + clientMsg + ". Sending msg to server");
				exchangerPS.exchange(clientMsg);
				double serverMsg = exchangerPS.exchange(null);
				exchangerCP.exchange(serverMsg);
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}
	}
	
	private void server() {
		System.out.println("Server online");
		while(true) {
			try {
				double clientMsg = exchangerPS.exchange(null);
				clientMsg++;
				System.out.println(Thread.currentThread().getName() + " sending msg: " + clientMsg + " back to proxy");
				exchangerPS.exchange(clientMsg);
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}
	}
	
	public void exec() {
		new Thread(() -> client(), "client").start();
		new Thread(() -> proxy(), "proxy").start();
		new Thread(() -> server(), "server").start();
	}
	
	public static void main(String[] argv) {
		new ClientProxyServer().exec();
	}
	
}
