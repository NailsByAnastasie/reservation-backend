package nails.yona.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "blocked_slot")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockedSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "La date de début est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateStart;

    @NotNull(message = "La date de fin est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateEnd;

    @NotBlank(message = "La raison est obligatoire")
    @Column(nullable = false)
    private String reason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}