package by.epamtc.courses.controller.command.user;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class for hash some string
 *
 * @author DEA
 */
public class PasswordHasher {
    private static final Logger logger = Logger.getLogger(PasswordHasher.class);

    /**
     * Field containing name of hash algorithm
     */
    private static final String ALGORITHM_NAME = "MD5";

    /**
     * Method to hash user's password
     *
     * @param password original representation of password
     * @return hashed representation of password
     */
    public static String hashPassword(String password) {
        byte[] digest;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_NAME);
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Wrong hash algorithm.");
            return null;
        }

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

        while (md5Hex.length() < 32) {
            md5Hex.insert(0, "0");
        }

        return md5Hex.toString();
    }
}
