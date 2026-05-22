package nails.yona.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BlockedSlotRequest(
        @NotNull(message = "La date de début du blocage est obligatoire.")
        LocalDateTime dateStart,

        @NotNull(message = "La date de fin du blocage est obligatoire.")
        LocalDateTime dateEnd,

        @NotBlank(message = "Le motif du blocage est obligatoire (ex: Vacances, Maladie...).")
        String reason
) {}
