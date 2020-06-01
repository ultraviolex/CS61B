import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int[][] arr1 = {{1}, {2}, {3}};
        assertEquals(3, MultiArr.maxValue(arr1));
        int[][] arr2 = {{1, 5, 9}, {2}, {3}};
        assertEquals(9, MultiArr.maxValue(arr2));
        int[][] arr3 = {{0}, {0}, {0}};
        assertEquals(0, MultiArr.maxValue(arr3));
        int[][] arr4 = {{1, 0, 5}, {7, 100, 0}, {300}};
        assertEquals(300, MultiArr.maxValue(arr4));
        int[][] arr5 = {{0}};
        assertEquals(0, MultiArr.maxValue(arr5));
    }

    @Test
    public void testAllRowSums() {
        int[][] arr1 = {{1}, {2}, {3}};
        int[] arr1Sum = MultiArr.allRowSums(arr1);
        assertArrayEquals(new int[] {1, 2, 3}, arr1Sum);
        int[][] arr2 = {{0, 3}, {1, 3, 5}, {3}};
        int[] arr2Sum = MultiArr.allRowSums(arr2);
        assertArrayEquals(new int[] {3, 9, 3}, arr2Sum);
        int[][] arr3 = {{1}, {0}, {0}, {0, 1}};
        int[] arr3Sum = MultiArr.allRowSums(arr3);
        assertArrayEquals(new int[] {1, 0, 0, 1}, arr3Sum);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
