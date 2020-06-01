package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests to verify that naturalRuns works correctly.
 *
 *  @author Xiuhui Ming
 */

public class ListsTest {
    @Test
    public void naturalRunTest() {
        IntList L1 = IntList.list(1, 3, 7, 5, 4, 6, 9, 10, 10, 11);
        IntListList result1 = Lists.naturalRuns(L1);
        int[][] A1 = {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        IntListList expected1 = IntListList.list(A1);
        assertEquals(expected1, result1);

        IntList L2 = IntList.list();
        IntListList result2 = Lists.naturalRuns(L2);
        int[][] A2 = {};
        IntListList expected2 = IntListList.list(A2);
        assertEquals(expected2, result2);

        IntList L3 = IntList.list(3, 2, 1);
        IntListList result3 = Lists.naturalRuns(L3);
        int[][] A3 = {{3}, {2}, {1}};
        IntListList expected3 = IntListList.list(A3);
        assertEquals(expected3, result3);

    }

    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
