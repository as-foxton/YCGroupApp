package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.AccountGegevens;
import nl.yc2306.recruitmentApp.DTOs.LoginRequest;
import nl.yc2306.recruitmentApp.Login.LoginService;
import nl.yc2306.recruitmentApp.Login.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(method= RequestMethod.POST, value="login")
    public String login(@RequestBody LoginRequest request){
        return loginService.login(request.getUsername(),request.getPassword());
    }

    @RequestMapping(method= RequestMethod.GET, value="account/current")
    public AccountGegevens getUser(@RequestHeader String AUTH_TOKEN){
        Account user = loginService.findLoggedinUser(AUTH_TOKEN);
        AccountGegevens response = new AccountGegevens();
        if(user == null){
            response.setId(-1);
        }
        else{
            response.setId(user.getId());
            response.setLocatie(user.getLocatie());
            response.setNaam(user.getNaam());
            response.setRol(user.getRol());
        }
        return response;
    }

    @RequestMapping(method= RequestMethod.GET, value="account/haspermission")
    public boolean authorise(@RequestHeader String AUTH_TOKEN, @RequestParam String page){
        return loginService.isAuthorised(AUTH_TOKEN, page);
    }

    @RequestMapping(method= RequestMethod.GET, value="logout")
    public void logout(@RequestHeader String AUTH_TOKEN){
        loginService.logout(AUTH_TOKEN);
    }
}
