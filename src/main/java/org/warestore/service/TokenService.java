package org.warestore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.warestore.model.Token;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenService {

    public void addCookieToken(HttpServletResponse response, Token token){
        if (token!=null){
            Cookie cookie = new Cookie("WarestoreToken",token.getToken());
            cookie.setMaxAge(24*60*60); //one day
            response.addCookie(cookie);
        }
    }

    public Token tokenMapper(ResponseEntity<?> response){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(response.getBody(),Token.class);
    }
}
