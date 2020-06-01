import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        int a = 2021;
        assertEquals(1, CompoundInterest.numYears(a));
        int b = 2030;
        assertEquals(10, CompoundInterest.numYears(b));

    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        double presentValueA = 10;
        double rateA = 12;
        int targetYearA = 2022;
        assertEquals(12.544, CompoundInterest.futureValue(presentValueA, rateA, targetYearA),
                tolerance);
        double presentValueB = 1000;
        double rateB = 6;
        int targetYearB = 2030;
        assertEquals(1790.847697, CompoundInterest.futureValue(presentValueB, rateB, targetYearB),
                tolerance);
        double presentValueC = 0;
        assertEquals(0, CompoundInterest.futureValue(presentValueC, rateB, targetYearB),
                tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        double presentValueA = 10;
        double rateA = 12;
        int targetYearA = 2022;
        double inflationRateA = 3;
        assertEquals(11.8026496, CompoundInterest.futureValueReal(presentValueA, rateA, targetYearA,
                inflationRateA), tolerance);
        double presentValueB = 1000;
        double rateB = 6;
        int targetYearB = 2030;
        assertEquals(1320.614299, CompoundInterest.futureValueReal(presentValueB, rateB,
                targetYearB, inflationRateA),
                tolerance);
        double presentValueC = 0;
        assertEquals(0, CompoundInterest.futureValueReal(presentValueC, rateB, targetYearB,
                inflationRateA),
                tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        double perYearA = 5000;
        int targetYearA = 2022;
        double rateA = 10;
        assertEquals(16550, CompoundInterest.totalSavings(perYearA, targetYearA, rateA), tolerance);
        double perYearB = 1000;
        int targetYearB = 2030;
        double rateB = 6;
        assertEquals(14971.64, CompoundInterest.totalSavings(perYearB, targetYearB, rateB),
                tolerance);
        double perYearC = 0;
        assertEquals(0, CompoundInterest.totalSavings(perYearC, targetYearA, rateB), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        double perYearA = 5000;
        int targetYearA = 2022;
        double rateA = 10;
        double inflationRate = 3;
        assertEquals(15571.895, CompoundInterest.totalSavingsReal(perYearA, targetYearA, rateA,
                inflationRate), tolerance);
        int targetYearB = 2021;
        assertEquals(10185, CompoundInterest.totalSavingsReal(perYearA, targetYearB, rateA,
                inflationRate), tolerance);

    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
