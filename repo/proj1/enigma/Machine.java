package enigma;
import java.util.Collection;
import java.util.HashMap;


/** Class that represents a complete enigma machine.
 *  @author Xiuhui Ming
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _rotorsInUse = new Rotor[numRotors];
        _allRotors = new HashMap<String, Rotor>(allRotors.size());
        for (Rotor r : allRotors) {
            _allRotors.put(r.name(), r);
        }

    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        for (int i = 0; i < rotors.length; i += 1) {
            for (int j = i + 1; j < rotors.length; j += 1) {
                if (rotors[i].equals(rotors[j])) {
                    throw new EnigmaException("Repeated rotors.");
                }
            }
        }
        if (rotors.length != _numRotors) {
            throw new EnigmaException("Incorrect rotor num.");
        }
        for (int i = 0; i < _rotorsInUse.length; i += 1) {
            if (_allRotors.containsKey(rotors[i])) {
                _rotorsInUse[i] = _allRotors.get(rotors[i]);
            }
        }
        if (!_rotorsInUse[0].reflecting()) {
            throw new EnigmaException("Rotor 0 not reflector.");
        }

    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Incorrect setting length");
        }
        for (int i = 0; i < setting.length(); i += 1) {
            int settingInt = _alphabet.toInt(setting.charAt(i));
            _rotorsInUse[i + 1].set(settingInt);
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        boolean[] rotates = new boolean[numRotors()];
        for (int i = 0; i < rotates.length; i += 1) {
            rotates[i] = _rotorsInUse[i].rotates();
        }
        boolean[] atNotch = new boolean[numRotors()];
        for (int i = 0; i < atNotch.length; i += 1) {
            atNotch[i] = _rotorsInUse[i].atNotch();
        }
        boolean[] advance = new boolean[numRotors()];
        for (int i = 0; i < advance.length - 1; i += 1) {
            advance[i] = false;
        }
        advance[numRotors() - 1] = true;
        for (int i = numRotors() - 1; i > 0; i -= 1) {
            if (atNotch[i] && rotates[i - 1]) {
                advance[i] = true;
                advance[i - 1] = true;
            }
        }
        for (int i = 0; i < advance.length; i += 1) {
            if (advance[i]) {
                _rotorsInUse[i].advance();
            }
        }
        int output = _plugboard.permute(c);
        for (int i = numRotors() - 1; i >= 0; i -= 1) {
            output = _rotorsInUse[i].convertForward(output);
        }
        for (int i = 1; i < _numRotors; i += 1) {
            output = _rotorsInUse[i].convertBackward(output);
        }
        return _plugboard.permute(output);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String msgOut = "";
        for (int i = 0; i < msg.length(); i += 1) {
            int intIn = _alphabet.toInt(msg.charAt(i));
            int intOut = convert(intIn);
            char charOut = _alphabet.toChar(intOut);
            msgOut += charOut;
        }
        return msgOut;
    }


    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotor slots. */
    private int _numRotors;

    /** Number of pawls. */
    private int _pawls;

    /** List of rotors of machine in use. */
    private Rotor[] _rotorsInUse;

    /** Array of all rotors available. */
    private HashMap<String, Rotor> _allRotors;

    /** Plugboard permutation. **/
    private Permutation _plugboard;
}
