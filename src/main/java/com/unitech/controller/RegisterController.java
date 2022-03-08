package com.unitech.controller;

import com.unitech.dao.entity.Userrrrr;
import com.unitech.service.UserrrrrService;
import com.unitech.util.MyCallable;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@Validated
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/v1/register")
public class RegisterController {

    private final UserrrrrService userService;
    private final RestTemplate restTemplate;
    private final MyCallable myCallable;

    @GetMapping("/thread")
    public CompletableFuture<List<Userrrrr>> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/a")
    public List<Userrrrr> findAllUserssssss() {
        return userService.findAllUserssssssss();
    }

    @GetMapping("/myApp")
    public String myApp() {

        for(int i = 0; i < 100; i++) {
            restTemplate.getForObject("https://swapi.dev/api/people/1/", String.class);
        }

        return "success";
    }

    @GetMapping("/newApp")
    public String newApp() throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> list = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            Future<String> future = executor.submit(myCallable);
            list.add(future);
        }

        for(Future<String> future: list) {
            future.get();
        }

        executor.shutdown();


        return "success";
    }
}
