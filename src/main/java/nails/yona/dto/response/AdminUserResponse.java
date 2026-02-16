package nails.yona.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminUserResponse(
        UUID id,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}