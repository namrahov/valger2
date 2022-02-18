package com.unitech.controller;

import com.unitech.model.AccountDto;
import com.unitech.model.TransferDto;
import com.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/all")
    public List<AccountDto> getUserAccountList(@RequestHeader(AUTHORIZATION) String authHeader) {
        return accountService.getUserAccountList(authHeader);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@RequestHeader(AUTHORIZATION) String authHeader,
                         @RequestBody TransferDto transferDto) {
        accountService.transfer(authHeader, transferDto);
    }
}
