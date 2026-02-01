package com.softka.account_service.infrastructure.utils;

import com.softka.account_service.infrastructure.dto.CustomerResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
public class CustomerRestClient {

    @Value("${external.service.url}")
    private String serviceUrl;

    private static final RestTemplate restTemplate = new RestTemplate();


    public Optional<CustomerResponseDto> findClientById(Long id){
        try {
            String url = serviceUrl+"/api/clientes/"+id;
            log.warn("url: {}", url);
            ResponseEntity<CustomerResponseDto> clientResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    CustomerResponseDto.class
            );
            return Optional.ofNullable(clientResponse.getBody());

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
