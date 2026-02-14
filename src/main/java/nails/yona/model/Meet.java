package nails.yona.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nails.yona.enums.MeetStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "meet")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Le client est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull(message = "La prestation est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private Prestation prestation;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetStatus status;

    @Column(columnDefinition = "TEXT")
    private String note;

    @NotNull(message = "La date de début est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateStart;

    @NotNull(message = "La date de fin est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateEnd;

    @NotNull(message = "Le prix final est obligatoire")
    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    @Column(nullable = false)
    private Integer finalPrice;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}