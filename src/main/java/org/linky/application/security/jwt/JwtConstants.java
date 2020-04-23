package org.linky.application.security.jwt;

public class JwtConstants {
    public static final String SECRET = "This is very secure token";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
