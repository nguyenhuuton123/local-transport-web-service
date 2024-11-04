package com.vtb.seatransportservice.controller.admin;

import com.vtb.seatransportservice.domain.dto.request.AccountRequest;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService iAccountService;

    @GetMapping("/list-account")
    public ResponseEntity<ResponsePayload> getAccount(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ResponsePayload responsePayload = iAccountService.getAccount(pageable);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PutMapping("/edit-account/{id}")
    public ResponseEntity<ResponsePayload> updateAccount(@RequestBody AccountRequest accountRequest, @PathVariable Long id) {
        ResponsePayload responsePayload = iAccountService.editAccount(accountRequest, id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PatchMapping("/delete-account/{id}")
    public ResponseEntity<ResponsePayload> deleteAccount(@PathVariable Long id) {
        ResponsePayload responsePayload = iAccountService.deleteAccount(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PostMapping("/create-account")
    public ResponseEntity<ResponsePayload> createAccount(@RequestBody AccountRequest accountRequest) {
        ResponsePayload responsePayload = iAccountService.createAccount(accountRequest);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }
}
