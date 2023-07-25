package nl.yc2306.recruitmentApp.Login;

import nl.yc2306.recruitmentApp.Account;
import nl.yc2306.recruitmentApp.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Value("${sec.expiration}")
    private int expirationTime;
    @Autowired
    private IAccountRepository accounts;
    @Autowired
    private IPermissionRepository permissions;

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

    public boolean isAuthorised(String token, String page) {
        Account user = findLoggedinUser(token);
        if(user == null)
            return false;
        List<Permission> allRoles = permissions.findAllByPage(page);

        if(allRoles.stream().anyMatch(perm -> user.getRol().toLowerCase().equals(perm.getRol().toLowerCase())))
            return true;
        return false;
    }

    public boolean isAuthorised(String token, String[] pages){
        for (String page:pages) {
            if(isAuthorised(token, page))
                return true;
        }
        return false;
    }

    public void logout(String token){
        if(activeTokens.containsKey(token))
            activeTokens.remove(token);
    }

    public Iterable<String> getAccessiblePages(String rol){
        List<Permission> per = permissions.findAllByRol(rol);
        List<String> pages = per.stream().map(permission -> permission.getPage()).toList();
        return pages;
    }
}
