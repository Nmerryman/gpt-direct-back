package dev.hydris.gptdirect;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


@RestController
final public class AuthController {

    private final String passHash;
    private ArrayList<String> validAuthTokens;

    public AuthController() throws NoSuchAlgorithmException {
        // FIXME! Yes this is bad. 
        String password = "pass";
        validAuthTokens = new ArrayList<String>();
        
        MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
        messagedigest.update(password.getBytes());
        passHash = new String(messagedigest.digest());
    }


    private boolean matchesHash(String inputHash) {
        // If it's a hash, fast comparisons should be fine.
        return Objects.equals(passHash, inputHash);
    }

    public boolean matchesPassword(String inputPassword) throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
        messagedigest.update(inputPassword.getBytes());

        return matchesHash(new String(messagedigest.digest()));
    }

    public String generateAuthToken(String inputPassword) throws NoSuchAlgorithmException {
        // This requires an additional check, just in case.
        if (matchesPassword(inputPassword)) {
            String newToken = UUID.randomUUID().toString().replace("-", "");
            validAuthTokens.add(newToken);
            return newToken;
        }

        return "";
    }

    public boolean validateAuthToken(String token) {
        // This should avoid most useful side channel attacks. I think all that remains is token list size and token length.

        boolean passes = false;

        for (String validToken: validAuthTokens) {
            if (token.length() == validToken.length()) {
                boolean tempPasses = true;
                // Always looping through the entire token should avoid timing attacks
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

    @PostMapping("/api/auth")
    public AuthResponse getAuthResponse(@RequestBody AuthRequest authRequest) throws NoSuchAlgorithmException {

        return new AuthResponse(generateAuthToken(authRequest.password()));

    }

//    @PostMapping("/api/authtest")
//    public PingMessage testAuth()

}


