package algorithms.java;

public class MaxArrayTest {
	public static void main(String[] argv) {
		int MAX = 100;
		int minN = 0;
		int maxN = MAX -1;
		int[] array = new int[MAX];
		
		for(int i = 0; i < MAX; i++) {
			array[i] = (int) ((Math.random() * ((10000 - 0) + 1)) + 0);
		}
		
		System.out.print("Array: [");
		for(int i = 0; i < MAX; i++) {
			System.out.print(" " + array[i]);
		}
		System.out.println("]");
		
		MaxArray ma = new MaxArray(array, minN, maxN);
		int res = ma.compute();
		System.out.println(res);
	}
}
