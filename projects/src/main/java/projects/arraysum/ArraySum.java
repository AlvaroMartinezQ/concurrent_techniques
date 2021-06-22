package projects.arraysum;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import java.util.Random;

import es.urjc.etsii.code.concurrency.SimpleSemaphore;

public class ArraySum {

	private static final int N_THREADS = 2;
	
	private static int[] array = new int[16];
	private static int actualPos;
	private static int maxPos;
	private static int insertPos;
	
	private static SimpleSemaphore emData = new SimpleSemaphore(1);
	
	public static void adder() {
		while(true) {
			emData.acquire();
			if(actualPos == maxPos || maxPos == 1) {
				println("iter made: " + maxPos + " " + insertPos + " " + actualPos);
				maxPos = maxPos / 2;
				insertPos = 0;
				actualPos = 0;
				println("iter reset: " + maxPos + " " + insertPos + " " + actualPos);
			}
			if (maxPos <= 1) {
				emData.release();
				break;
			}
			if(maxPos % 2 != 0 && actualPos == maxPos - 1) {
				int posOdd = actualPos;
				if(maxPos == 2) {
					array[0] = array[0] + array[posOdd];
				} else {
					actualPos++;
					println(getThreadName() + " | Positions: " + posOdd + " sum -> " + array[posOdd]);
					array[insertPos] = array[posOdd];
					insertPos++;
				}
			} else {
				int pos1 = actualPos;
				actualPos++;
				int pos2 = actualPos;
				actualPos++;
				int sum = array[pos1] + array[pos2];
				println(getThreadName() + " | Positions: " + pos1 + " " + pos2 + " sum -> " + sum);
				array[insertPos] = sum;
				insertPos++;
			}
			emData.release();
			sleep(500); // For multiple thread array access
		}
	}
	
	public static void main(String[] argv) {
		Random rand = new Random();
		for(int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt(20);
		}
		
		print("Array: [");
		for(int i = 0; i < array.length; i++) {
			print(array[i] + " - ");
		}
		println("]");
		
		actualPos = 0;
		insertPos = 0;
		maxPos = array.length;
		
		for(int i = 0; i < N_THREADS; i++) {
			createThread("adder");
		}
		startThreadsAndWait();
		println("Result is: " + array[0]);
	}
	
}
