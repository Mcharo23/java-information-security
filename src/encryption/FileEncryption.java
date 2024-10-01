
package encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author mcharo
 */
public class FileEncryption {
    private static final String ALORITHM = "PBEWithMD5AndDES";
    private static final int ITERATION_COUNT = 1000;
    private static final int SALT_SIZE = 8;
    
    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secureRandomBytes = new byte[SALT_SIZE];
        secureRandom.nextBytes(secureRandomBytes);
        
        return secureRandomBytes;
    }
    
    public static void encrypt(String password, byte[] salt, String inputFilePath, String outputFilePath) {
        try {
            PBEKeySpec pbeks = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALORITHM);
            SecretKey sk = skf.generateSecret(pbeks);
            
            PBEParameterSpec pbeps = new PBEParameterSpec(salt, ITERATION_COUNT);
            Cipher c = Cipher.getInstance(ALORITHM);
            c.init(Cipher.ENCRYPT_MODE, sk,  pbeps);
            
            FileInputStream inputStream = new FileInputStream(inputFilePath);
            FileOutputStream outputStream = new FileOutputStream(outputFilePath);
            
            byte[] buffer = new byte[1024];
            int length;
            
            while ((length = inputStream.read(buffer)) != -1) {
                byte[] data = c.update(buffer, 0, length);
                
                if (data != null) {
                    outputStream.write(data);
                }
            }
            
            byte[] encryptedFile = c.doFinal();
            
            if (encryptedFile != null) {
                outputStream.write(encryptedFile);
            }
                    
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IOException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(FileEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        byte[] salt = FileEncryption.generateSalt();
        FileEncryption.encrypt("password", salt, "src/files/input.txt", "src/files/output.txt");
    }
}
