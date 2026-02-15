package nails.yona.dto.response;

import nails.yona.enums.ContactType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponseDto(
        UUID id,
        String fullName,
        String email,
        ContactType contactType,
        String contactValue,
        LocalDateTime createdAt
){}
