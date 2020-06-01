/** Find sum of WeirdList.
 * @author Xiuhui Ming **/
public class Sum implements IntUnaryFunction {
    /** Initialize int sum. **/
    private int sum = 0;

    /** First number.
     * @param first **/
    public Sum(int first) {
        this.sum = first;
    }

    @Override
    public int apply(int x) {
        this.sum += x;
        return x;
    }

    /** Return the sum. **/
    public int result() {
        return sum;
    }
}
