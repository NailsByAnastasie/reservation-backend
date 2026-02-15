package nails.yona.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nails.yona.enums.ContactType;

public record ClientRequest(
        @NotBlank(message = "Le nom complet est obligatoire.")
        String fullName,

        @NotBlank(message = "L'adresse email est obligatoire.")
        @Email(message = "Le format de l'adresse email est invalide.")
        String email,

        @NotNull(message = "Le type de contact est obligatoire.")
        ContactType contactType,

        @NotBlank(message = "Les coordonnées de contact sont obligatoires.")
        String contactValue
) {}
