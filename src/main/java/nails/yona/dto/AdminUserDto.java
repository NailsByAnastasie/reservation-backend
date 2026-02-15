package nails.yona.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminUserDto(
        @NotBlank(message = "L'adresse email est obligatoire.")
        @Email(message = "Le format de l'adresse email est invalide.")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire.")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
        String password
) {}
