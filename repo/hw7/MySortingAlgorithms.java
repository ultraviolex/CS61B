import java.util.ArrayList;
import java.util.Arrays;

/**
 * Note that every sorting algorithm takes in an argument k. The sorting 
 * algorithm should sort the array from index 0 to k. This argument could
 * be useful for some of your sorts.
 *
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Counting Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 1; i < k; i += 1) {
                int current = array[i];
                for (int j = i - 1; j >= 0; j -= 1) {
                    int sorted = array[j];
                    if (current >= sorted) {
                        break;
                    }
                    array[j + 1] = sorted;
                    array[j] = current;
                }
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 0; i < k - 1; i += 1) {
                int secondIndex = i;
                int min = array[i];
                for (int j = i + 1; j < k; j += 1) {
                    if (array[j] < min) {
                        min = array[j];
                        secondIndex = j;
                    }
                }
                int firstInt = array[i];
                array[i] = min;
                array[secondIndex] = firstInt;
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            if (k > 1) {
                int middle = k / 2;
                int[] arr1 = Arrays.copyOfRange(array, 0, middle);
                int[] arr2 = Arrays.copyOfRange(array, middle, k);
                sort(arr1, arr1.length);
                sort(arr2, arr2.length);
                merge(array, arr1, arr2);
            }
        }

        public void merge(int[] array, int[] arr1, int[] arr2) {
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < arr1.length && j < arr2.length) {
                if (arr1[i] <= arr2[j]) {
                    int num = arr1[i];
                    array[k] = num;
                    i += 1;
                } else {
                    int num = arr2[j];
                    array[k] = num;
                    j += 1;
                }
                k += 1;
            }
            while (i < arr1.length) {
                int num = arr1[i];
                array[k] = num;
                i += 1;
                k += 1;
            }
            while (j < arr2.length) {
                int num = arr2[j];
                array[k] = num;
                j += 1;
                k += 1;
            }
        }
        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Counting Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class CountingSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Counting Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            int bit = 0;
            while (bit < 16) {
                ArrayList<Integer>[] count = new ArrayList[2];
                count[0] = new ArrayList<Integer>();
                count[1] = new ArrayList<Integer>();
                for (int i = 0; i < k; i += 1) {
                    int num = a[i];
                    if (((num >> bit) & 1) == 0) {
                        count[0].add(num);
                    } else {
                        count[1].add(num);
                    }
                }
                for (int i = 0; i < count[0].size(); i += 1) {
                    int num = count[0].get(i);
                    a[i] = num;
                }
                for (int i = 0; i < count[1].size(); i += 1) {
                    int num = count[1].get(i);
                    a[count[0].size() + i] = num;
                }
                bit += 1;
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
