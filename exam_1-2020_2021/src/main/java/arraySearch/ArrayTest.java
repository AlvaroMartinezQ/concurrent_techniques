package arraySearch;

public class ArrayTest {

	private static final int N = 20;
	private static final int searchNumber = 23;
	
	public static void main(String[] argv) {
		int[] array = new int[N];
		for(int i = 0; i < N; i++) {
			array[i] = (int) (Math.random() * (30 - 0) + 0);
		}
		System.out.print("Array: [");
		for(int i = 0; i < N; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("] -> Searching for number: " + searchNumber);
		
		ArraySearch as = new ArraySearch(0, N, searchNumber, array);
		int val = as.compute();
		
		if (val == -1) {
			System.out.println("Number not in array.");
		} else {
			System.out.println("Number in position: " + val);
		}
		
	}
	
}
