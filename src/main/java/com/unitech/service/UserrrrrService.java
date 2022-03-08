package com.unitech.service;

import com.unitech.dao.entity.Userrrrr;
import com.unitech.dao.repository.UserrrrrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserrrrrService {

    private final UserrrrrRepository userrrrrRepository;

    @Async
    public CompletableFuture<List<Userrrrr>> findAllUsers() {
        List<Userrrrr> userrrrrs1 = userrrrrRepository.findAll();
        List<Userrrrr> userrrrrs2 = userrrrrRepository.findAll();
        List<Userrrrr> userrrrrs3 = userrrrrRepository.findAll();

        CompletableFuture.allOf(
                CompletableFuture.completedFuture(userrrrrs1),
                CompletableFuture.completedFuture(userrrrrs2),
                CompletableFuture.completedFuture(userrrrrs3)
        ).join();

        return CompletableFuture.completedFuture(userrrrrs3);
    }


    public List<Userrrrr> findAllUserssssssss() {
        List<Userrrrr> userrrrrs1 = userrrrrRepository.findAll();
        List<Userrrrr> userrrrrs2 = userrrrrRepository.findAll();
        List<Userrrrr> userrrrrs3 = userrrrrRepository.findAll();

        return userrrrrs3;
    }



}
