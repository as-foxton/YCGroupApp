package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("${jwt.route.authentication.path}")
public class LoginController {
    /*
    @Autowired
    private LoginService loginService;

    @RequestMapping(method= RequestMethod.POST, value="login")
    public long login(@RequestBody LoginRequest request){
        return loginService.login(request.getUsername(),request.getPassword());
    }*/
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    // try to authenticate
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws AuthenticationException {
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        System.out.println("after auth");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new LoginResponse(token));
    }

    // get the logged in user (JwtUser)
    @GetMapping
    public JwtUser getAuthenticatedUser(@RequestHeader("${jwt.header}")String token) {
        //String token = request.getHeader(tokenHeader).substring(7);
        System.out.println(token);
        String username = tokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
}
