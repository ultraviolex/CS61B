/** Class that takes in a non empty array and returns the maximum. 
 * @author Xiuhui Ming
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Max {
	/** Returns if int b is greater than int a */
	static boolean greater(int a, int b) {
		if (b > a) {
			return true;
		}
		return false;
	}

	/** Prints out the maximum of the array */
	private static int max(int[] a) {
		int size = a.length;
		int i = 0;
		int max = a[0];
		while (i<size-1) {
			if (greater(max, a[i+1])) {
				max = a[i+1];
			}
			i += 1;
		}

		return max;
	}

	/** Test cases for max */

	public static void main(String[] args){
		int[] a = {1,2,3,4,5};
		System.out.println("The maximum of the array is " + max(a));
		int[] b = {1,1,1,1,1};
		System.out.println("The maximum of the array is " + max(b));
		int[] c = {5,4,3,2,1};
		System.out.println("The maximum of the array is " + max(c));
		int[] d = {1,3,5,4,2};
		System.out.println("The maximum of the array is " + max(d));
	}

}