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
@Table(name = "cost")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Le coût est obligatoire")
    @Min(value = 0)
    @Column(nullable = false)
    private Integer cost;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}