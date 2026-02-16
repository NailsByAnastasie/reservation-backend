package nails.yona.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailUpdateRequest(
        @NotBlank(message = "Le nouvel email est obligatoire.")
        @Email(message = "Le format de l'email est invalide.")
        String newEmail
) {}