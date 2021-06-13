package exams;

public class NumberSeacrhTest {

	private static final int N = 20;
	private static final int searchNumber = 23; // <3
	
	public static void main(String[] argv) {
		int[] array = new int[N];
		for(int i = 0; i < N; i++) {
			array[i] = (int) (Math.random() * (100 - 0) + 0);
		}
		System.out.print("Array: [");
		for(int i = 0; i < N; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("] -> Searching for number: " + searchNumber);
		NumberSearch ns = new NumberSearch(array, 0, N, searchNumber);
		int val = ns.compute();
		if(val != -1) {
			System.out.println("Number found in the array in position: " + val);
		} else {
			System.out.println("Number not found in the array");
		}
	}
	
}
