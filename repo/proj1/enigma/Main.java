package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Xiuhui Ming
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine enigma = readConfig();
        String settings = _input.nextLine();
        setUp(enigma, settings);
        while (_input.hasNextLine()) {
            String nxt = _input.nextLine();
            if (nxt.startsWith("*")) {
                setUp(enigma, nxt);
            } else {
                nxt = nxt.replaceAll("\\s*", "");
                printMessageLine(enigma.convert(nxt));
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            _alphabet = new Alphabet(_config.next());
            if (_alphabet.contains('(') || _alphabet.contains(')')
                    || _alphabet.contains('*')) {
                throw new EnigmaException("Invalid character in alphabet");
            }
            if (!_config.hasNextInt()) {
                throw new EnigmaException("No rotors.");
            }
            int numRotors = Integer.parseInt(_config.next());
            if (!_config.hasNextInt()) {
                throw new EnigmaException("No pawls.");
            }
            int pawls = Integer.parseInt(_config.next());
            if (pawls < 0 || numRotors < 0) {
                throw new EnigmaException("Not enough pawls/slots.");
            }
            if (pawls >= numRotors) {
                throw new EnigmaException("Too many pawls.");
            }
            ArrayList<Rotor> allRotors = new ArrayList<Rotor>();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
            }
            return new Machine(_alphabet, numRotors, pawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name = _config.next();
            if (name.contains("(") || name.contains(")")) {
                throw new EnigmaException("Invalid rotor name.");
            }
            String type = _config.next();
            String patterns = "";
            while (_config.hasNext("\\(.*")) {
                patterns += _config.next("(\\([^\\)]*\\))*");
            }
            if (type.charAt(0) == 'M') {
                return new MovingRotor(name,
                        new Permutation(patterns, _alphabet),
                        type.substring(1));
            } else if (type.charAt(0) == 'N') {
                return new FixedRotor(name,
                        new Permutation(patterns, _alphabet));
            } else if (type.charAt(0) == 'R') {
                return new Reflector(name,
                        new Permutation(patterns, _alphabet));
            }
            return null;
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        if (!settings.startsWith("*")) {
            throw new EnigmaException("Invalid settings format.");
        }
        Pattern cyclePattern = Pattern.compile("\\s*[^\\s]+");
        Matcher match = cyclePattern.matcher(settings);
        ArrayList<String> settingsArray = new ArrayList<>();
        while (match.find()) {
            settingsArray.add(match.group());
        }
        if (settingsArray.size() < M.numRotors() + 2) {
            throw new EnigmaException("Not enough rotors.");
        }
        int i = 0;
        String[] rotors = new String[M.numRotors()];
        String rotorSettings = "";
        String cycles = "";
        for (String s: settingsArray) {
            s = s.replaceAll("\\s", "");
            if (i != 0) {
                if (i < M.numRotors() + 1) {
                    rotors[i - 1] = s;
                } else if (i == M.numRotors() + 1) {
                    rotorSettings = s;
                } else {
                    if (!s.startsWith("(")) {
                        throw error("Incorrect cycle formatting,"
                                + " must start with '('");
                    }
                    cycles += s;
                }
            }
            i++;
        }
        M.insertRotors(rotors);
        M.setRotors(rotorSettings);
        M.setPlugboard(new Permutation(cycles, _alphabet));
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        if (msg.isEmpty()) {
            _output.println("");
        } else {
            for (int i = 0; i < msg.length(); i += 5) {
                if (msg.length() - i > 5) {
                    _output.print(msg.substring(i, i + 5) + " ");
                } else {
                    _output.println(msg.substring(i));
                }
            }
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
}
