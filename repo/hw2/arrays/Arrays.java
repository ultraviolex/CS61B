package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Xiuhui Ming
 */
class Arrays {

    /* C1. */

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    static int[] catenate(int[] A, int[] B) {
        /* *Replace this body with the solution. */
        int[] C = new int[A.length + B.length];
        for (int i = 0; i < A.length; i++) {
            C[i] = A[i];
        }
        for (int i = 0; i < B.length; i++) {
            C[A.length + i] = B[i];
        }
        return C;
    }

    /* C2. */

    /**
     * Returns the array formed by removing LEN items from A,
     * beginning with item #START.
     */
    static int[] remove(int[] A, int start, int len) {
        /* *Replace this body with the solution. */
        int[] B = new int[A.length - len];
        for (int i = 0; i < start; i++) {
            B[i] = A[i];
        }
        for (int i = 0; i + start < B.length; i++) {
            B[start + i] = A[start + len + i];
        }
        return B;
    }

    /* C3. */

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     * For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     * returns the three-element array
     * {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */

        int count = 1;
        for (int i = 1; i < A.length; i++) {
            if (A[i] <= A[i - 1]) {
                count++;
            }
        }
        int[][] result = new int[count][];
        int resultIndex = 0;
        int subArrayStart = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i - 1] >= A[i]) {
                result[resultIndex] = Utils.subarray(A, subArrayStart, i - subArrayStart);
                subArrayStart = i;
                resultIndex++;
            }
        }
        if (result[resultIndex] == null) {
            result[resultIndex] = Utils.subarray(A, subArrayStart, A.length - subArrayStart);
        }
        return result;
    }
}

