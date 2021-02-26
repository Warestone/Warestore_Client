package org.warestore.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class ResponseService {
    private RestTemplate restTemplate = new RestTemplate();

    public List<?>getData(String url){
        ResponseEntity<List<?>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<?>>(){});
        return response.getBody();
    }
}
