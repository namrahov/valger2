package com.unitech.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class MyCallable implements Callable<String> {

    private final RestTemplate restTemplate;

    @Override
    public String call() throws Exception {
        return restTemplate.getForObject("https://swapi.dev/api/people/1/", String.class);
    }
}
