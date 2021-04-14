package org.warestore.service;

import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.Cookie;
import java.util.List;

@Log
@Service
public class RequestService {

    private final Environment environment;
    private final RestTemplate restTemplate = new RestTemplate();
    private final PasswordEncoder passwordEncoder;

    public RequestService(Environment environment, PasswordEncoder passwordEncoder) {
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> getOrPostData(String url, Cookie token, HttpMethod method, HttpEntity<?> httpEntity){
        HttpEntity<?> entity;
        if (httpEntity!=null)
            entity = new HttpEntity<>(httpEntity.getBody(), getHeaders(token));
        else
            entity = new HttpEntity<>(getHeaders(token));

        ResponseEntity<?> response = restTemplate.exchange(
                url,
                method,
                entity,
                new ParameterizedTypeReference<>(){});
        log.info("Return data for '"+url+"'");
        return response;
    }

    public String getURL(String url, String page){
        return url+getPage(page);
    }

    public int getPage(String page){
        if (page==null)
            return 0;
        else return Integer.parseInt(page);
    }

    private HttpHeaders getHeaders(Cookie token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("App-Verification",
                passwordEncoder.encode(environment.getProperty("app.secret")));
        if (token!=null)
            if (token.getValue()!=null)
                headers.add("Authorization","Bearer "+token.getValue());
        return headers;
    }

}
