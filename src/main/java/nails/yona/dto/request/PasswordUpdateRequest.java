package nails.yona.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest(
        @NotBlank(message = "L'ancien mot de passe est obligatoire.")
        String oldPassword,

        @NotBlank(message = "Le nouveau mot de passe est obligatoire.")
        @Size(min = 8, message = "Le mot de passe doit faire au moins 8 caractères.")
        String newPassword
) {}