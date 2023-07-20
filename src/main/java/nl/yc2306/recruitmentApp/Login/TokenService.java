package nl.yc2306.recruitmentApp.Login;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final char[] possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890=+".toCharArray();
    
    @Value("${sec.tokenlength}")
    private int tokenLength;
    
    public String generateToken() {
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i =0; i < tokenLength; i++){
            builder.append(possibleCharacters[r.nextInt(possibleCharacters.length)]);
        }
        return builder.toString();
    }
}
