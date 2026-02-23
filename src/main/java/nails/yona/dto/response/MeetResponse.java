package nails.yona.dto.response;

import nails.yona.enums.MeetStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MeetResponse(
        UUID id,
        ClientResponse client,
        PrestationResponse prestation,
        MeetStatus status,
        String note,
        LocalDateTime dateStart,
        LocalDateTime dateEnd,
        Integer finalPrice,
        String finalEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}