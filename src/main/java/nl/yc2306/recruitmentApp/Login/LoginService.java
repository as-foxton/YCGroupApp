package nl.yc2306.recruitmentApp.Login;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yc2306.recruitmentApp.Account;
import nl.yc2306.recruitmentApp.IAccountRepository;

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
    
    @Autowired
    private AccountTokenService accountTokenService;

    public AccountToken login(String email, String password){
        Account account = accounts.findByEmail(email);

        if (account != null && account.getWachtwoord().equals(password)) {
        	AccountToken accountToken = account.getAccountToken();
        	if (accountToken == null) {
        		accountToken = new AccountToken();
        		accountToken.setAccount(account);
        	}

        	accountToken.setToken(tokenService.generateToken());
        	accountToken.setExpiresAt(LocalDateTime.now().plusSeconds(expirationTime));
    		accountToken = accountTokenService.save(accountToken);

            return accountToken;
        }

        return null;
    }

    public Account findLoggedinUser(String token) {
    	Optional<AccountToken> optional = this.accountTokenService.findByToken(token);
    	if (optional.isEmpty())
    		return null;
    	
    	AccountToken accountToken = optional.get();
    	
        if (accountToken.getExpiresAt().isBefore(LocalDateTime.now()) ){
            this.accountTokenService.delete(accountToken);
            return null;
        }

        return accountToken.getAccount();
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
    	Optional<AccountToken> optional = this.accountTokenService.findByToken(token);
    	if (optional.isPresent()) {
	    	AccountToken accountToken = optional.get();
	        this.accountTokenService.delete(accountToken);
    	}
    }

    public Iterable<String> getAccessiblePages(String rol){
        List<Permission> per = permissions.findAllByRol(rol);
        List<String> pages = per.stream().map(permission -> permission.getPage()).toList();
        return pages;
    }
}
