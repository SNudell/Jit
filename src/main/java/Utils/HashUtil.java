package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

public class HashUtil {
    public static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hashAll (Stream<String> stream) {
        MessageDigest digest = getDigest();
        stream.forEachOrdered(s-> digest.update(s.getBytes()));
        return  hashToString(digest.digest());
    }

    public static String hash(byte[] bytes) {
        MessageDigest digest = getDigest();
        digest.update(bytes);
        return hashToString(digest.digest());
    }

    public static String hash(InputStream in) {
        MessageDigest digest = getDigest();
        byte[] buffer = new byte[8192];
        int n;
        try{
            while((n = in.read(buffer))>-1) {
                digest.update(buffer,0,n);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return hashToString(digest.digest());
    }


    public static String hashToString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(String.format("%02x",bytes[i]));
        }
        return stringBuilder.toString();
    }
}
