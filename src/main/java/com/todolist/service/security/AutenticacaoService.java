package com.todolist.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.todolist.utils.SecurityUtil.*;
import java.util.*;


public class AutenticacaoService {



    /**
     * Recupera as permissões do usuário
     * @param httpServletResponse
     * @param authentication
     */
    static public void addJWTToken(HttpServletResponse httpServletResponse, Authentication authentication) {

        Map<String, Object> claims = new HashMap<> ();

        claims.put (AUTHORITIES, authentication.getAuthorities ()
                .stream ()
                .map (GrantedAuthority :: getAuthority)
                .toList ());

        String jwtToken = Jwts.builder ()
                .setSubject (authentication.getName ())
                .setExpiration (new Date (System.currentTimeMillis () + EXPIRATION_TOKE))
                .signWith(SECRET_KEY)
                .addClaims (claims)
                .compact ();

        httpServletResponse.addHeader (HEADER_AUTHORIZATION, BEARER+" " + jwtToken);
        httpServletResponse.addHeader ("Access-Control-Expose-Headers", HEADER_AUTHORIZATION);
    }

    /**
     * Recuperar token de usuário autenticado afim de fazer o controle das permissões
     * @param httpServletRequest
     * @return
     */

    static public Authentication getAuthentication(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader (HEADER_AUTHORIZATION);

        if(token != null) {
            Claims user = Jwts.parser()
                    .setSigningKey (SECRET_KEY)
                    .parseClaimsJws (token.replace (BEARER+" ", ""))
                    .getBody ();

            if(user != null) {

                List<SimpleGrantedAuthority> permissoes = ((ArrayList<String>) user.get(AUTHORITIES))
                        .stream ()
                        .map (SimpleGrantedAuthority::new)
                        .toList ();


                return new UsernamePasswordAuthenticationToken (user, null, permissoes);
            } else {
                throw new RuntimeException ("Autenticação Falhou");
            }
        }
        return null;
    }



}
