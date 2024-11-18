package dev.hydris.gptdirect;

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.UUID;

public class AuthController {

    private String passHash;
    private ArrayList<String> validAuthTokens;

    public AuthController() throws NoSuchAlgorithmException {
        // FIXME! Yes this is bad. 
        String password = "pass";
        
        MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
        messagedigest.update(password.getBytes());
        passHash = new String(messagedigest.digest());
    }


    private boolean matchesHash(String inputHash) {
        // If it's a hash, fast comparisons should be fine.
        return passHash == inputHash;
    }

    public boolean matchesPassword(String inputPassword) throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
        messagedigest.update(inputPassword.getBytes());

        return matchesHash(new String(messagedigest.digest()));
    }

    public String generateAuthToken(String inputPassword) throws NoSuchAlgorithmException {
        // This requires an additional check, just in case.
        if (matchesPassword(inputPassword)) {
            String newToken = new String(UUID.randomUUID().toString().replace("-", ""));
            validAuthTokens.add(newToken);
            return newToken;
        }

        return new String();
    }

    public boolean validateAuthToken(String token) {
        // This should avoid most useful sidechannel attecks. I think all that remains is token list size and token length.

        boolean passes = false;
    
        for (String validToken: validAuthTokens) {
            if (token.length() == validToken.length()) {
                boolean tempPasses = true;
                for (int i = 0; i < token.length(); i++) {
                    if (token.charAt(i) != validToken.charAt(i)) {
                        tempPasses = false;
                    }
                }
                if (tempPasses) {
                    passes = true;
                }
            }
        }

        return passes;
    }
    


}


