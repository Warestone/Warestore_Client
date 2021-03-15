package org.warestore.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class TokenService {

    public boolean addCookieToken(HttpServletResponse response, List<String> token){
        if (token.size()!=0){
            Cookie cookie = new Cookie("WarestoreToken",token.get(0));
            cookie.setMaxAge(24*60*60); //one day
            response.addCookie(cookie);
            return true;
        }
        return false;
    }
}
