package datastructures.java;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Duplicates {

	private ConcurrentMap<String, String> duplicates = new ConcurrentHashMap<>();
	
	public void findDuplicates(File root) {
		if(root.isDirectory()) {
			for(File f: root.listFiles()) {
				if(f.isDirectory()) {
					findDuplicates(f);
				} else {
					// Path is the previous value associated with a given key if there's a match
					String path = duplicates.putIfAbsent(f.getName(), f.getAbsolutePath());
					
					if (path != null) {
						System.out.println("--------------------------------------------------");
						System.out.println("Found duplicate file: " + f.getName());
						System.out.println("  " + path);
						System.out.println("  " + f.getAbsolutePath());
						System.out.println("--------------------------------------------------");
					}
				}
			}
		}
	}
	
	public void exec() {
		new Thread(() -> findDuplicates(new File("./"))).start();
		new Thread(() -> findDuplicates(new File("../"))).start();
	}
	
	public static void main(String[] argv) {
		new Duplicates().exec();
	}
	
}
