package com.example.taskApp.nbpApiDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class NbpService {

    private final RestClient restClient = RestClient.create();

    public List<NbpTableDto> getTableA() {
        return restClient
                .get()
                .uri("http://api.nbp.pl/api/exchangerates/tables/A?format=json")
                .retrieve()
                .body(new ParameterizedTypeReference<List<NbpTableDto>>() {});
    }
}