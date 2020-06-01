import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/** HW #7, Sorting ranges.
 *  @author Xiuhui Ming
  */
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals. */
    public static int coveredLength(List<int[]> intervals) {
        // REPLACE WITH APPROPRIATE STATEMENTS.
        int[] points = new int[intervals.size() * 2];
        boolean[] bools = new boolean[intervals.size() * 2];
        for (int i = 0; i < intervals.size(); i += 1) {
            points[2 * i] = intervals.get(i)[0];
            points[2 * i + 1] = intervals.get(i)[1];
            bools[2 * i] = false;
            bools[2 * i + 1] = true;
        }
        sort(points, points.length, bools);
        int counter = 0;
        int result = 0;
        for (int i = 0; i < intervals.size() * 2; i += 1) {
            if (counter > 0) {
                result += (points[i] - points[i - 1]);
            }
            if (!bools[i]) {
                counter += 1;
            } else {
                counter -= 1;
            }
        }
        return result;
    }

    public static void sort(int[] a, int k, boolean[] bool) {
        int bit = 0;
        while (bit < 16) {
            ArrayList<Integer>[] count = new ArrayList[2];
            count[0] = new ArrayList<Integer>();
            count[1] = new ArrayList<Integer>();
            ArrayList<Boolean>[] booleans = new ArrayList[2];
            booleans[0] = new ArrayList<Boolean>();
            booleans[1] = new ArrayList<Boolean>();
            for (int i = 0; i < k; i += 1) {
                int num = a[i];
                boolean boole = bool[i];
                if (((num >> bit) & 1) == 0) {
                    count[0].add(num);
                    booleans[0].add(boole);
                } else {
                    count[1].add(num);
                    booleans[1].add(boole);
                }
            }
            for (int i = 0; i < count[0].size(); i += 1) {
                int num = count[0].get(i);
                boolean boole = booleans[0].get(i);
                a[i] = num;
                bool[i] = boole;
            }
            for (int i = 0; i < count[1].size(); i += 1) {
                int num = count[1].get(i);
                boolean boole = booleans[1].get(i);
                a[count[0].size() + i] = num;
                bool[count[0].size() + i] = boole;
            }
            bit += 1;
        }
    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
