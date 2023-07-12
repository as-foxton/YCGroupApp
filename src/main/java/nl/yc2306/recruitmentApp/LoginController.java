package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(method= RequestMethod.POST, value="login")
    public long login(@RequestBody LoginRequest request){
        return loginService.login(request.getUsername(),request.getPassword());
    }
}
