package nl.yc2306.recruitmentApp.security;

import nl.yc2306.recruitmentApp.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(Account user) {
        System.out.println("create user");
        return new JwtUser(
                user.getPersoons_nr(),
                user.getEmail(),
                user.getWachtwoord(),
                mapToGrantedAuthorities(Arrays.asList("test","test2"))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
}