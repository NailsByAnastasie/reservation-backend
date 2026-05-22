package nails.yona.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockedSlotResponse(
        UUID id,
        LocalDateTime dateStart,
        LocalDateTime dateEnd,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}