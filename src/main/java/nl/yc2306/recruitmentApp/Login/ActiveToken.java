package nl.yc2306.recruitmentApp.Login;

import nl.yc2306.recruitmentApp.Account;

import java.time.LocalDateTime;

public class ActiveToken {
    private Account user;
    private LocalDateTime expiresAt;

    ActiveToken(Account user, LocalDateTime expiresAt){
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public Account getUser() {
        return user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
