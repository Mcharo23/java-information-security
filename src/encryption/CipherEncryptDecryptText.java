
package encryption;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;


/**
 *
 * @author mcharo
 */
public class CipherEncryptDecryptText {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    
    private static String encrypt(String plainText, SecretKey key) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] encryptedText = c.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedText);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CipherEncryptDecryptText.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private static String decrypt(String encryptedText, SecretKey key) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            
            byte[] decodedTextBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedTextBytes = c.doFinal(decodedTextBytes);
            
            String plainText = new String(decryptedTextBytes, StandardCharsets.UTF_8);
            return plainText;
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CipherEncryptDecryptText.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    private static SecretKey generateKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
            kg.init(KEY_SIZE);
            SecretKey sk = kg.generateKey();
            
            return sk;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CipherEncryptDecryptText.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String[] args) {
        SecretKey sk = CipherEncryptDecryptText.generateKey();
        String originalText = "Hello";
        System.out.println("Original Text: " + originalText);
        
        String encryptedText = CipherEncryptDecryptText.encrypt(originalText, sk);
        System.out.println("Encrypted Text: " + encryptedText);
        System.out.println("Decrypted Text: " + CipherEncryptDecryptText.decrypt(encryptedText, sk));
    }
}
