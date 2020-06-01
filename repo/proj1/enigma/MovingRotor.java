package enigma;

import java.util.ArrayList;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Xiuhui Ming
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = new ArrayList<>();
        for (int i = 0; i < notches.length(); i += 1) {
            _notches.add(perm.alphabet().toInt(notches.charAt(i)));
        }
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        return _notches.contains(setting());
    }

    @Override
    void advance() {
        set(permutation().wrap(setting() + 1));
    }

    /** Integer array of the notches, converted to ints from notches. */
    private ArrayList<Integer> _notches;

}
