package com.todolist.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import java.security.Key;


/**
 * Contém as constantes relacionadas a segurança
 */
@UtilityClass
public class SecurityUtil {

        public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        public static final String HEADER_AUTHORIZATION = "Authorization";

        public static final String JWT_KEY = "signinKey";

        public static final String AUTHORITIES = "authorities";

        public static final int EXPIRATION_TOKE = 3600000;

        public static final String BEARER = "Bearer";

}
