package pl.asiewiera.zuul;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {

    private final String KEY="6ff2695a-ce16-4e71-9899-6c9191a0bee3";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken == null || !bearerToken.contains("Bearer ")){
            System.out.println("No bearer token found, Security Context not modified");
            filterChain.doFilter(request, response);
            return;
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(KEY)).build();
        System.out.println(bearerToken);
        DecodedJWT decodedJWT;
        try{
            decodedJWT = jwtVerifier.verify(bearerToken.substring(7));
        }catch (TokenExpiredException | InvalidClaimException | SignatureVerificationException ex){
            System.out.println(ex.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            return;
        }
        String name = decodedJWT.getClaim("name").asString();
        String role = decodedJWT.getClaim("role").asString();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, Collections.singleton(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        System.out.println("Hello " + name );

        filterChain.doFilter(request,response);
    }

}
