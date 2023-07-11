package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private IAccountRepository accounts;

    public long login(String email, String password){
        Account user = accounts.findByEmail(email);
        if(user!= null && user.getWachtwoord().equals(password)){
            return user.getId();
        }
        return -1;
    }
}
