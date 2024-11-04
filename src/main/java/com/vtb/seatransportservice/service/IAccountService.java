package com.vtb.seatransportservice.service;

import com.vtb.seatransportservice.domain.dto.request.AccountRequest;
import com.vtb.seatransportservice.payload.ResponsePayload;
import org.springframework.data.domain.Pageable;

public interface IAccountService {
    ResponsePayload getAccount(Pageable pageable);
    ResponsePayload editAccount(AccountRequest accountRequest, Long id);
    ResponsePayload createAccount(AccountRequest accountRequest);
    ResponsePayload deleteAccount(Long id);
}
