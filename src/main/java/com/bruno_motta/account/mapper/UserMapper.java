package com.bruno_motta.account.mapper;

import com.bruno_motta.account.dto.CreateUserRequest;
import com.bruno_motta.account.dto.UserResponse;
import com.bruno_motta.account.entity.User;
import com.bruno_motta.account.entity.enums.AccountStatus;
import com.bruno_motta.account.entity.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request, String passwordHash, UserRole role, AccountStatus accountStatus) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .passwordHash(passwordHash)
                .cpf(request.cpf())
                .role(role)
                .accountStatus(accountStatus)
                .build();
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCpf(),
                user.getRole(),
                user.getAccountStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
