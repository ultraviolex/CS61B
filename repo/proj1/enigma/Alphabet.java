package enigma;

import java.util.ArrayList;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Xiuhui Ming
 */
class Alphabet {

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        for (int i = 0; i < chars.length(); i += 1) {
            if (_alphabet.contains(chars.charAt(i))) {
                throw new EnigmaException("Repeated char in alph.");
            }
            _alphabet.add(chars.charAt(i));
        }
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _alphabet.size();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        for (char c : _alphabet) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        if (index < 0 || index >= _alphabet.size()) {
            throw new EnigmaException("Invalid index.");
        }
        return _alphabet.get(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        for (int i = 0; i < _alphabet.size(); i += 1) {
            if (_alphabet.get(i) == ch) {
                return i;
            }
        }
        throw EnigmaException.error("Character not found in alphabet");
    }

    /** Character array containing the characters in alphabet. */
    private ArrayList<Character> _alphabet = new ArrayList<Character>();

}
