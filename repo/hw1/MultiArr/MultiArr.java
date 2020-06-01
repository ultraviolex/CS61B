/** Multidimensional array 
 *  @author Zoe Plaxco, Xiuhui Ming
 */

public class MultiArr {

    /**
    {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
    Rows: 2
    Columns: 3
    
    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    public static void printRowAndCol(int[][] arr) {
        int rows = arr.length;
        int cols = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length > cols) {
                cols = arr[i].length;
            }
        }
        System.out.println("Rows: " + rows);
        System.out.println("Columns: " + cols);
    } 

    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int x = 0; x < arr[i].length; x++) {
                if (arr[i][x] > max) {
                    max = arr[i][x];
                }
            }
        }
        return max;
    }

    /**Return an array where each element is the sum of the 
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] arrSum = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int x = 0; x < arr[i].length; x++) {
                sum += arr[i][x];
            }
            arrSum[i] = sum;
        }
        return arrSum;
    }
}
