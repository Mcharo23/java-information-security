
package encryption;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.*;


/**
 *
 * @author mcharo
 */

public class SeederDialog  {
    private byte[] seed;
    
    public SeederDialog() {
        generateSeed();
    }
    
    private void generateSeed() {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter your secret Key: ");
        String key = input.nextLine();
        
        byte[] keyBytes = key.getBytes();
        
        SecureRandom secureRandom = new SecureRandom();
        byte[] secureRandomBytes = new byte[16];
        secureRandom.nextBytes(secureRandomBytes);
        
        seed = new byte[keyBytes.length + secureRandomBytes.length];
        
        System.arraycopy(keyBytes, 0, seed, 0, keyBytes.length);
        System.arraycopy(secureRandomBytes, 0, seed, 0, secureRandomBytes.length);
        
        
    }
   
    public static void main(String[] args) {
        SeederDialog dialog = new SeederDialog();
        
        byte[] seed = dialog.seed;
        
        SecureRandom recipe = new SecureRandom(seed);
        byte[] recipeBytes = new byte[32];
        recipe.nextBytes(recipeBytes);
        
        System.out.println(Base64.getEncoder().encodeToString(recipeBytes));
    }
}
