package capers;

import java.io.*;

/** Represents a dog that can be serialized.
 * @author Sean Dooher
*/
public class Dog implements Serializable{ // FIXME added implements serializable

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = Main.DOGS_FOLDER; // FIXME

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        _age = age;
        _breed = breed;
        _name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        File fromFile = Utils.join(DOG_FOLDER, name);
        return Utils.readObject(fromFile, Dog.class);
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        _age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        File outFile = Utils.join(DOG_FOLDER, _name);
        try {
            outFile.createNewFile();
        } catch (IOException excp) {
            System.out.println("Error in Dog saveDog");
        }
        Utils.writeObject(outFile, this);
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            _name, _breed, _age);
    }

    /** Age of dog. */
    private int _age;
    /** Breed of dog. */
    private String _breed;
    /** Name of dog. */
    private String _name;
}
