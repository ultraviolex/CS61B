package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests to check that the methods in Arrays.java works
 *  @author Xiuhui Ming
 */

public class ArraysTest {
    @Test
    public void catenateTest() {
        int[] A1 = {1, 2, 3};
        int[] B1 = {1, 2, 3};
        int[] C1 = {1, 2, 3, 1, 2, 3};
        int[] results1 = Arrays.catenate(A1, B1);
        assertArrayEquals(C1, results1);

        int[] A2 = {};
        int[] B2 = {1, 2, 3};
        int[] C2 = {1, 2, 3};
        int[] results2 = Arrays.catenate(A2, B2);
        assertArrayEquals(C2, results2);

        int[] A3 = {1, 2, 3};
        int[] B3 = {1, 2, 3, 9, 10};
        int[] C3 = {1, 2, 3, 1, 2, 3, 9, 10};
        int[] results3 = Arrays.catenate(A3, B3);
        assertArrayEquals(C3, results3);
    }

    @Test
    public void removeTest() {
        int[] A1 = {1, 2, 3};
        int[] expected1 = {1};
        int[] results1 = Arrays.remove(A1, 1, 2);
        assertArrayEquals(expected1, results1);

        int[] A2 = {1, 2, 3, 5, 6};
        int[] expected2 = {1, 2, 6};
        int[] results2 = Arrays.remove(A2, 2, 2);
        assertArrayEquals(expected2, results2);

        int[] A3 = {1, 2, 3, 5, 6};
        int[] expected3 = {1, 2};
        int[] results3 = Arrays.remove(A3, 2, 3);
        assertArrayEquals(expected3, results3);

        int[] A4 = {1, 2, 3};
        int[] expected4 = {};
        int[] results4 = Arrays.remove(A4, 0, 3);
        assertArrayEquals(expected4, results4);
    }

    @Test
    public void naturalRunsTest() {
        int[] A2 = {1, 1, 3};
        int[][] expected2 = {{1}, {1, 3}};
        int[][] results2 = Arrays.naturalRuns(A2);
        assertArrayEquals(expected2, results2);

        int[] A1 = {1, 2, 3, 2, 3, 5, 5, 6, 7, 7};
        int[][] expected1 = {{1, 2, 3}, {2, 3, 5}, {5, 6, 7}, {7}};
        int[][] results1 = Arrays.naturalRuns(A1);
        assertArrayEquals(expected1, results1);

        int[] A3 = {5, 4, 1, 3};
        int[][] expected3= {{5}, {4}, {1, 3}};
        int[][] results3 = Arrays.naturalRuns(A3);
        assertArrayEquals(expected3, results3);

        int[] A4 = {1};
        int[][] expected4 = {{1}};
        int[][] results4 = Arrays.naturalRuns(A4);
        assertArrayEquals(expected4, results4);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
