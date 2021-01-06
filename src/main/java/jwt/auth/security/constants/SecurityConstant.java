package jwt.auth.security.constants;

public class SecurityConstant {
    public static final String SECRET = "BUSRA";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/signup";
    public static final String LOGIN_URL = "/login";
}
