package nl.yc2306.recruitmentApp.Login;

import nl.yc2306.recruitmentApp.Account;
import nl.yc2306.recruitmentApp.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Value("${sec.expiration}")
    private int expirationTime;
    @Autowired
    private IAccountRepository accounts;

    @Autowired
    private TokenService tokenService;

    private Map<String, ActiveToken> activeTokens = new HashMap<>();

    public String login(String email, String password){
        Account user = accounts.findByEmail(email);
        String token = null;
        if(user!= null && user.getWachtwoord().equals(password)){
            while(token == null || activeTokens.containsKey(token)){
                token = tokenService.generateToken();
            }
            ActiveToken at = new ActiveToken(user, LocalDateTime.now().plusSeconds(expirationTime));
            activeTokens.put(token, at);
        }
        return token;
    }

    public Account findLoggedinUser(String token){
        ActiveToken at = activeTokens.get(token);
        if(at == null)
            return null;
        if(at.getExpiresAt().isBefore(LocalDateTime.now()) ){
            activeTokens.remove(token);
            return null;
        }
        return at.getUser();
    }

    public void logout(String token){
        if(activeTokens.containsKey(token))
            activeTokens.remove(token);
    }
}
