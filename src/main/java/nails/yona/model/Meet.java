package nails.yona.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetStatus status;

    @Column(columnDefinition = "TEXT")
    private String note;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dateStart;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dateEnd;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private Integer finalPrice;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String finalEmail;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}