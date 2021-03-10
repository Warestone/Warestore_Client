package org.warestore.service;

import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public List<?> getOrPostData(String url){
        log.info("Return data for '"+url+"'");
        HttpEntity<String> entity = new HttpEntity<>("parameters", getHeaders());
        ResponseEntity<List<?>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>(){});
        return response.getBody();
    }

    public String getURL(String url, String page){
        if (page == null) return url+0;
        else return url+page;
    }

    private HttpHeaders getHeaders(){
        //get token
        HttpHeaders headers = new HttpHeaders();
        headers.add("App-Verification",
                passwordEncoder.encode(environment.getProperty("app.secret")));
        //headers.add("Authorization","Bearer "+token);
        return headers;
    }
}
