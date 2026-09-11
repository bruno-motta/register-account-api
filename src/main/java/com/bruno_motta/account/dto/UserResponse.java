package com.bruno_motta.account.dto;

import com.bruno_motta.account.entity.enums.AccountStatus;
import com.bruno_motta.account.entity.enums.UserRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String cpf,
        UserRole role,
        AccountStatus accountStatus,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
