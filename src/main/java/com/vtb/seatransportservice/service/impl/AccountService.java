package com.vtb.seatransportservice.service.impl;

import com.vtb.seatransportservice.constant.AccountConstant;
import com.vtb.seatransportservice.domain.dto.RoleDTO;
import com.vtb.seatransportservice.domain.dto.request.AccountRequest;
import com.vtb.seatransportservice.domain.dto.response.AccountResponse;
import com.vtb.seatransportservice.domain.entity.Role;
import com.vtb.seatransportservice.domain.entity.User;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.RoleRepository;
import com.vtb.seatransportservice.repository.UserRepository;
import com.vtb.seatransportservice.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponsePayload getAccount(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Page<User> userList = userRepository.findAllUsers(pageable);
            List<AccountResponse> accountResponses = userList.getContent().stream()
                    .map(user -> {
                        AccountResponse accountResponse = modelMapper.map(user, AccountResponse.class);
                        RoleDTO roleDto = modelMapper.map(user.getRole(), RoleDTO.class);
                        accountResponse.setRole(roleDto.getName());
                        System.out.println(accountResponse);
                        return accountResponse;
                    })
                    .collect(Collectors.toList());
            return ResponsePayload.builder()
                    .data(accountResponses)
                    .message(AccountConstant.GET_LIST_USERS_SUCCESSFULLY)
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponsePayload.builder()
                    .message(AccountConstant.GET_FAILURE_LIST)
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @Override
    public ResponsePayload editAccount(AccountRequest accountRequest, Long id) {
            Optional<User> userOptional = userRepository.findUserById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setUsername(accountRequest.getUsername());
                user.setFullName(accountRequest.getFullName());
                user.setEmail(accountRequest.getEmail());
                user.setPhone(accountRequest.getPhone());
                userRepository.save(user);
                return ResponsePayload.builder()
                        .data(user)
                        .message(AccountConstant.ACCOUNT_UPDATED_SUCCESSFULLY)
                        .status(HttpStatus.OK)
                        .build();
            } else {
                return ResponsePayload.builder()
                        .data(null)
                        .message(AccountConstant.ACCOUNT_UPDATE_FAILED)
                        .status(HttpStatus.FORBIDDEN)
                        .build();
            }
    }

    @Override
    public ResponsePayload createAccount(AccountRequest accountRequest) {
        if (userRepository.existsByUsername(accountRequest.getUsername())) {
            return ResponsePayload.builder()
                    .data(null)
                    .message(AccountConstant.USERNAME_ALREADY_EXISTS)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        User user = modelMapper.map(accountRequest, User.class);
        Role role = roleRepository.findByName(accountRequest.getRole())
            .orElseThrow(() -> new RuntimeException(AccountConstant.ROLE_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        user.setRole(role);
        user.setIsDelete(false);
        user.setIsActive(true);
        userRepository.save(user);
        return ResponsePayload.builder()
                .data(user)
                .message(AccountConstant.ACCOUNT_SUCCESSFULLY_CREATED)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponsePayload deleteAccount(Long id) {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsDelete(true);
            userRepository.save(user);
            return ResponsePayload.builder()
                    .data(null)
                    .message(AccountConstant.ACCOUNT_DELETED)
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponsePayload.builder()
                    .data(null)
                    .message(AccountConstant.ACCOUNT_DELETION_FAILED)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
