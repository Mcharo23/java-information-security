
package encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 *
 * @author mcharo
 */

public class KeySpecFromPassphrase  {
    
    public static SecretKey generateDesFromPassphrase (String passphrase) {
        try {
            byte[] passphraseBytes = passphrase.getBytes();
            DESKeySpec keySpec = new DESKeySpec(passphraseBytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            
            SecretKey secretKey = factory.generateSecret(keySpec);
            return secretKey;
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(KeySpecFromPassphrase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        SecretKey secretKey = KeySpecFromPassphrase.generateDesFromPassphrase("Mcharo12!");
        
        System.out.println(secretKey);
    }
}
