package encryption;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;

/**
 *
 * @author mcharo
 */
public class FileMessageDigest {
    private static final String ALGORITHM = "MD5";
    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            FileInputStream in = new FileInputStream("src/files/input.txt");
            byte[] buffer = new byte[1024];
            int length;
            
            while ((length = in.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            
            in.close();
            
            byte[] digest = md.digest();
            
            
            System.out.println("Digested Data: " + Base64.getEncoder().encodeToString(digest));
            
        } catch (NoSuchAlgorithmException | FileNotFoundException ex) {
            Logger.getLogger(FileMessageDigest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileMessageDigest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
