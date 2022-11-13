package by.iba.diploma_101_back.helpers;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static String hashingAlgorithm = "MD5";

    public static String securePass(String password) throws NoSuchAlgorithmException {
        byte[] bytesOfMessage = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
        byte[] theMD5digest = md.digest(bytesOfMessage);
        return DatatypeConverter.printHexBinary(theMD5digest);
    }

}
