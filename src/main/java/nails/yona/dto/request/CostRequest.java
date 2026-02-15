package nails.yona.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CostRequest(
        @NotBlank(message = "Le titre de la dépense est obligatoire.")
        String title,

        @NotNull(message = "Le montant de la dépense est obligatoire.")
        @Min(value = 0, message = "Le montant de la dépense ne peut pas être négatif.")
        Integer cost
) {}
