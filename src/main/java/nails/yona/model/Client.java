package nails.yona.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nails.yona.enums.ContactType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Le nom complet est obligatoire")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est invalide")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Le type de contact est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contactType;

    @NotBlank(message = "Le contact est obligatoire")
    @Column(nullable = false)
    private String contactValue;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}