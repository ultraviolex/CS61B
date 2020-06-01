package enigma;

import java.util.ArrayList;
import java.util.Arrays;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Xiuhui Ming
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        String s = cycles.replace("(", "");
        s = s.replace(")", " ");
        _cycles = new ArrayList<>(Arrays.asList(s.split(" ")));
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        if (cycle.isEmpty()) {
            throw new EnigmaException("Cannot have an empty cycle");
        }
        _cycles.add(cycle);
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return alphabet().size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        int inIndex = wrap(p);
        char inChar = _alphabet.toChar(inIndex);
        int outIndex;
        char outChar;
        for (String cycle : _cycles) {
            if (cycle.indexOf(inChar) != -1) {
                outIndex = cycle.indexOf(inChar) + 1;
                if (outIndex >= cycle.length()) {
                    outIndex = 0;
                }
                outChar = cycle.charAt(outIndex);
                return _alphabet.toInt(outChar);
            }
        }
        return inIndex;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        int inIndex = wrap(c);
        char inChar = _alphabet.toChar(inIndex);
        int outIndex;
        char outChar;
        for (String cycle : _cycles) {
            if (cycle.indexOf(inChar) != -1) {
                outIndex = cycle.indexOf(inChar) - 1;
                if (outIndex < 0) {
                    outIndex = cycle.length() - 1;
                }
                outChar = cycle.charAt(outIndex);
                return _alphabet.toInt(outChar);
            }
        }
        return inIndex;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        if (!alphabet().contains(p)) {
            throw new EnigmaException("Not in alphabet");
        }
        int index = _alphabet.toInt(p);
        return _alphabet.toChar(permute(index));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (!alphabet().contains(c)) {
            throw new EnigmaException("Not in alphabet");
        }
        int index = _alphabet.toInt(c);
        return _alphabet.toChar(invert(index));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int len = 0;
        for (String cycle : _cycles) {
            len += cycle.length();
            if (cycle.length() == 1) {
                return false;
            }
        }
        if (len != _alphabet.size()) {
            return false;
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycles of this permutation. */
    private ArrayList<String> _cycles;
}
