package nails.yona.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import nails.yona.enums.MeetStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MeetRequest(
        @NotNull(message = "Le client est obligatoire pour créer un rendez-vous.")
        UUID clientId,

        @NotNull(message = "La prestation est obligatoire pour créer un rendez-vous.")
        UUID prestationId,

        @NotNull(message = "Le statut du rendez-vous est obligatoire.")
        MeetStatus status,

        String note,

        @NotNull(message = "La date et l'heure de début sont obligatoires.")
        LocalDateTime dateStart,

        @NotNull(message = "La date et l'heure de fin sont obligatoires.")
        LocalDateTime dateEnd,

        @NotNull(message = "Le prix final est obligatoire.")
        @Min(value = 0, message = "Le prix final ne peut pas être négatif.")
        Integer finalPrice
) {}
