/** Given a nonempty array of integers, returns true if there is a combination
 * of three not neccessarily distinct integers where the sum is 0
 * @author Xiuhui Ming
 */
import java.util.Arrays;

public class ThreeSum_Distinct{
    
    private static boolean threeSum_Distinct(int[] a) {
        for (int i = 0; i<a.length-1; i++) {
            int x = a[i];
            int[] ay = Arrays.copyOfRange(a, i+1, a.length);
            for (int j=0; j<ay.length; j++) {
                int y = ay[j];
                int[] az = Arrays.copyOfRange(ay, j+1, ay.length);
                for (int r=0; r<az.length; r++) {
                    int z = az[r];
                    if (x+y+z==0) {
                        return true;
                    }
                }
            }
        }
    return false;
}

    public static void main(String[] args) {
        int[] a = {1,2,3};
        if (threeSum_Distinct(a)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
        int[] b = {-6,2,4};
        if (threeSum_Distinct(b)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
        int[] c = {-6,2,5};
        if (threeSum_Distinct(c)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }

        int[] d = {-6,3,10,200};
        if (threeSum_Distinct(d)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }

        int[] e = {8,2,-1,-1,5};
        if (threeSum_Distinct(e)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }

    }
}