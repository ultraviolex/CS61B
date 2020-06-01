package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author Xiuhui Ming
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }

    @Test
    public void testInvertChar() {
        Permutation p = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", getNewAlphabet());
        assertEquals('A', p.invert('E'));
        assertEquals('U', p.invert('A'));
        assertEquals('I', p.invert('V'));
        assertEquals('V', p.invert('I'));
        assertEquals('S', p.invert('S'));

    }

    @Test
    public void testInvertInt() {
        Permutation p = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", getNewAlphabet());
        assertEquals(0, p.invert(4));
        assertEquals(20, p.invert(0));
        assertEquals(8, p.invert(21));
        assertEquals(21, p.invert(8));
        assertEquals(18, p.invert(18));
    }

    @Test
    public void testPermuteChar() {
        Permutation p = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", getNewAlphabet());
        assertEquals('U', p.permute('R'));
        assertEquals('A', p.permute('U'));
        assertEquals('S', p.permute('S'));
        assertEquals('V', p.permute('I'));
        assertEquals('I', p.permute('V'));
    }

    @Test
    public void testPermuteInt() {
        Permutation p = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", getNewAlphabet());
        assertEquals(20, p.permute(17));
        assertEquals(0, p.permute(20));
        assertEquals(18, p.permute(18));
        assertEquals(21, p.permute(8));
        assertEquals(8, p.permute(21));
    }

    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        p.invert('E');
        p.permute('E');
        p.invert(5);
        p.permute(5);
    }

    @Test
    public void testModuleOverBound() {
        Permutation p = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", getNewAlphabet());
        assertEquals(4, p.permute(26));
        assertEquals(20, p.invert(26));
        assertEquals(10, p.permute(27));
        assertEquals(22, p.invert(27));
    }

    @Test
    public void testOne() {
        Permutation p = getNewPermutation("(A)", getNewAlphabet("A"));
        assertEquals('A', p.invert('A'));
        assertEquals(0, p.invert(0));
        assertEquals('A', p.permute('A'));
        assertEquals(0, p.permute(0));
    }
}
