package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Xiuhui Ming
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void testInvertChar() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation p = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                + " (DFG) (IV) (JZ) (S)", a);
        assertEquals('A', p.invert('E'));
        assertEquals('U', p.invert('A'));
        assertEquals('I', p.invert('V'));
        assertEquals('V', p.invert('I'));
        assertEquals('S', p.invert('S'));
    }

    @Test
    public void testInvertInt() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation p = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                + " (DFG) (IV) (JZ) (S)", a);
        assertEquals(0, p.invert(4));
        assertEquals(20, p.invert(0));
        assertEquals(8, p.invert(21));
        assertEquals(21, p.invert(8));
        assertEquals(18, p.invert(18));
    }

    @Test
    public void testPermuteChar() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation p = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                + "(DFG) (IV) (JZ) (S)", a);
        assertEquals('U', p.permute('R'));
        assertEquals('A', p.permute('U'));
        assertEquals('S', p.permute('S'));
        assertEquals('V', p.permute('I'));
        assertEquals('I', p.permute('V'));
    }

    @Test
    public void testPermuteInt() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation p = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                + "(DFG) (IV) (JZ) (S)", a);
        assertEquals(20, p.permute(17));
        assertEquals(0, p.permute(20));
        assertEquals(18, p.permute(18));
        assertEquals(21, p.permute(8));
        assertEquals(8, p.permute(21));
    }

    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Alphabet a = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a);
        p.invert('E');
        p.permute('E');
    }

    @Test
    public void testModuleOverBound() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation p = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                + "(DFG) (IV) (JZ) (S)", a);
        assertEquals(4, p.permute(26));
        assertEquals(20, p.invert(26));
        assertEquals(10, p.permute(27));
        assertEquals(22, p.invert(27));
    }

    @Test
    public void testOne() {
        Alphabet a = new Alphabet("A");
        Permutation p = new Permutation("(A)", a);
        assertEquals('A', p.invert('A'));
        assertEquals(0, p.invert(0));
        assertEquals('A', p.permute('A'));
        assertEquals(0, p.permute(0));
    }

    @Test
    public void testDerangement() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation p = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
                + "(DFG) (IV) (JZ) (S)", a);
        assertEquals(false, p.derangement());
        Alphabet b = new Alphabet("ABCD");
        Permutation q = new Permutation("(BACD)", b);
        assertEquals(true, q.derangement());
        Permutation r = new Permutation("(AB)", b);
        assertEquals(false, r.derangement());
    }

}
