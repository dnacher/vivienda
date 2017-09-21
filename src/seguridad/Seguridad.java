/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

import entities.persistence.entities.Usuario;
import java.util.Random;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author Daniel
 */
public final class Seguridad {

    private static final Random RANDOM = new SecureRandom();

    // These constant values are used by the hash algorithm.
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // By using a private constructor, we prevent instances of this class from being created
    private Seguridad() {

    }

    // This function generates a random, 16-byte 
    // salt value. You might be wondering if a longer salt
    // would result in a more secure hash:
    // http://stackoverflow.com/questions/184112/what-is-the-optimal-length-for-user-password-salt
    private static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    // This function uses the PBKDF2 algorithm for generating the hash. If you're 
    // interested in why this particular function was chosen, see:
    // http://security.stackexchange.com/questions/4781/do-any-security-experts-recommend-bcrypt-for-password-storage/6415#6415
    private static byte[] getHash(char[] password, byte[] salt) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return hash;
    }

    /**
     * This function takes the password from the {@link User} class and hashes
     * it. As a side-effect, the original password value is removed for security
     * purposes.
     *
     * @param user The user whose password needs to be hashed.
     * @exception Exception If there is a problem with the chosen hash function.
     */
    public static Usuario hashUserPassword(Usuario usuario) throws Exception {

        // Get the next random salt value to use for this password
        byte[] salt = getNextSalt();
        char[] password = usuario.getPassword().toCharArray();

        // Once we've generated the hash, clear the old password
        // from memory for security purposes
        byte[] hash = getHash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        usuario.setPassword("");

        if (hash != null) {

            // By Base64-encoding the raw bytes, we can store them as strings.
            // This allows us to save the values to a file or database if needed.
            // For more information on Base64 encoding, see:
            // http://stackoverflow.com/questions/201479/what-is-base-64-encoding-used-for
            // https://en.wikipedia.org/wiki/Base64
            String hashString = Base64.getEncoder().encodeToString(hash);
            String saltString = Base64.getEncoder().encodeToString(salt);

            usuario.setHashedPassword(hashString);
            usuario.setSalt(saltString);
        } else {
            usuario.setHashedPassword(null);
            usuario.setSalt(null);
        }
        return usuario;
    }
    
    /**
	* This function uses the password and salt in the {@link User} to generate a hash,
	* then compares that hash to the original hash value.
	* @param user The user whose password needs to be hashed.
	* @return Whether or not the password values match.
	* @exception Exception If there is a problem with the chosen hash function.
	*/
	public static Boolean verifyPassword(Usuario usuario) throws Exception {
		
		// Have to get the raw data values to use on our hash function
		char[] password = usuario.getPassword().toCharArray();
		byte[] salt = Base64.getDecoder().decode(usuario.getSalt());
		
		// Generate the new hash, and retrieve the user's hash
		byte[] expectedHash = getHash(password, salt);
		byte[] userHash = Base64.getDecoder().decode(usuario.getHashedPassword());
		
		// If the new hash came out as null, or the lengths don't match,
		// we know that the original password is different
		if(expectedHash == null || expectedHash.length != userHash.length)
			return false;
		
		// Check each byte of the two hashes and as soon as we find one
		// that is different, we know that the passwords don't match
		for(int i = 0; i < expectedHash.length; i++) {
			if(expectedHash[i] != userHash[i])
				return false;
		}
		
		// If we got this far, it means the password hashes match, so we
		// can assume the passwords do as well.
		return true;
	}
    
}
