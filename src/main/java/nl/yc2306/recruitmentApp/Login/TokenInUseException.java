package nl.yc2306.recruitmentApp.Login;

public class TokenInUseException extends Exception{
    public TokenInUseException(String message){
        super(message);
    }
}
