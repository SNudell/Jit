package Utils;

import Constants.SerializeConstant;

import java.io.*;

public class SerializeUtil {

    public static void write (Object object, String path) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(new File(SerializeConstant.OBJECTS_PATH +path)));
            out.writeObject(object);
        }catch (IOException e) {
        e.printStackTrace();
        }
    }

    public static <T> T read (String path) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(new File(SerializeConstant.OBJECTS_PATH+path)));
            return (T) in.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
