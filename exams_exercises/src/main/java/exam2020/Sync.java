package exam2020;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Sync {

	private Exchanger<Double> clientProxy = new Exchanger<>();
	private Exchanger<Double> proxyServer = new Exchanger<>();
	
	public void client() {
		try {
			System.out.println("client online");
			while(true) {
				double petition = Math.random();
				clientProxy.exchange(petition);
				double result = clientProxy.exchange(null);
				System.out.println("final response :" + result);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
		}
	}
	
	public void proxy() {
		try {
			System.out.println("proxy online");
			while(true) {
				double petition = clientProxy.exchange(null);
				System.out.println("petition from client : " + petition);
				proxyServer.exchange(petition);
				double result = proxyServer.exchange(null);
				System.out.println("response from server : " + result);
				clientProxy.exchange(result);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			
		}
	}
	
	public void server() {
		try {
			System.out.println("server online");
			while(true) {
				double petition = proxyServer.exchange(null);
				petition++;
				proxyServer.exchange(petition);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			
		}
	}
	
	public void exec() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		try {
			executor.submit( () -> client() );
			executor.submit( () -> server() );
			executor.submit( () -> proxy() );
		} catch (Exception e) {
			
		} finally {
			// never gets here
			executor.shutdown();
		}
	}
	
	public static void main(String[] argv) {
		new Sync().exec();
	}
	
}
