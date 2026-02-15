package nails.yona.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrestationResponse(
        UUID id,
        String name,
        Integer price,
        Integer time,
        String description,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
