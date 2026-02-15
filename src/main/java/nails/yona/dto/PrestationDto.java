package nails.yona.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrestationDto(
        @NotBlank(message = "Le nom de la prestation est obligatoire.")
        String name,

        @NotNull(message = "Le prix de la prestation est obligatoire.")
        @Min(value = 0, message = "Le prix ne peut pas être négatif.")
        Integer price,

        @NotNull(message = "La durée de la prestation est obligatoire.")
        @Min(value = 1, message = "La durée doit être d'au moins 1 minute.")
        Integer time,

        String description,

        @NotNull(message = "Le statut d'activation (active/inactive) est obligatoire.")
        Boolean active
) {}
