package org.linky.application.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.linky.exceptions.AbstractBaseException;
import org.linky.internal.user.IUserHandler;
import org.linky.model.services.UserRequest;
import org.linky.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserRepository userRepository;
    private IUserHandler userHandler;

    public JwtAuthenticationFilter(IUserHandler userHandler, UserRepository userRepository) {
        this.userHandler = userHandler;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserRequest creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserRequest.class);
            userRepository.findOne(Example.of(userHandler.handle(creds))).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User Not found!"));
            return new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AbstractBaseException e) {
            throw new AuthenticationCredentialsNotFoundException("User Not found!");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        String token = JWT.create()
                .withSubject(auth.getPrincipal().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtConstants.SECRET.getBytes()));
        res.addHeader(JwtConstants.HEADER_STRING, JwtConstants.TOKEN_PREFIX + token);
    }
}