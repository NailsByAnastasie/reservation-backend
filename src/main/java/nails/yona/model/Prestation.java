package nails.yona.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "prestation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prestation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Le nom de la prestation est obligatoire")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    @Column(nullable = false)
    private Integer price;

    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être d'au moins 1 minute")
    @Column(nullable = false)
    private Integer time;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Le statut de la prestation est obligatoire")
    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}