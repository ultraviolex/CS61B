/** Given a nonempty array of integers, returns true if there is a combination
 * of three not neccessarily distinct integers where the sum is 0
 * @author Xiuhui Ming
 */
import java.util.Arrays;

public class ThreeSum{
    
    private static boolean threeSum(int[] a) {
        for(int i=0; i<a.length; i++) {
            int x = a[i];
            for(int r=0; r<a.length; r++){
                int y = a[r];
                for(int j=0; j<a.length; j++) {
                    int z = a[j];
                    if (x+y+z == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3};
        if (threeSum(a)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
        int[] b = {-6,2,4};
        if (threeSum(b)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
        int[] c = {-6,2,5};
        if (threeSum(c)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }

        int[] d = {-6,3,10,200};
        if (threeSum(d)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }

        int[] e = {-4,3,100};
        if (threeSum(e)) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }

    }
}