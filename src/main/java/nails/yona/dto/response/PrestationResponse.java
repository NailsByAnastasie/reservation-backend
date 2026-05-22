package nails.yona.dto.response;

import nails.yona.enums.PrestationCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrestationResponse(
        UUID id,
        String name,
        Integer price,
        Integer time,
        String description,
        Boolean active,
        PrestationCategory prestationCategory,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
