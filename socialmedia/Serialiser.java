package socialmedia;

import java.io.*;

/**
 *
 * @author ellis mackness
 *
 * @version 1.1
 *
 * Class used for serialising and deserialising objects. {@code Serialiser.deserialise()} will return a new object with the
 * saved data.
 *
 * */

public abstract class Serialiser {
    /**
     * Method for serialising the data of a given object
     *
     * @param fileName path to save object
     * @param obj object to save
     *
     * @throws IOException if file cannot be written to
     *
     * */
    public static void serialise(String fileName, Object obj) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(obj);

        objectOutputStream.close();
        fileOutputStream.close();
    }

    /**
     * Method for reading a serialised object
     *
     * @param fileName path to read object
     *
     * @return Object from file
     *
     * @throws IOException if file doesn't exist
     * @throws ClassNotFoundException if the class cannot be read
     *
     * */
    public static Object deserialise(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object object = objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return object;
    }
}
