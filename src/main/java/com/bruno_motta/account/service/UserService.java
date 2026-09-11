package com.bruno_motta.account.service;

import com.bruno_motta.account.dto.CreateUserRequest;
import com.bruno_motta.account.dto.UserResponse;
import com.bruno_motta.account.entity.User;
import com.bruno_motta.account.entity.enums.AccountStatus;
import com.bruno_motta.account.entity.enums.UserRole;
import com.bruno_motta.account.mapper.UserMapper;
import com.bruno_motta.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        if (userRepository.existsByCpf(request.cpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF already registered");
        }

        User user = userMapper.toEntity(
                request,
                passwordEncoder.encode(request.password()),
                UserRole.ROLE_USER,
                AccountStatus.ACTIVE
        );

        return userMapper.toResponse(userRepository.save(user));
    }
}
