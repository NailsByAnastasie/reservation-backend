package nails.yona.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CostResponse(
        UUID id,
        String title,
        Integer cost,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}