package nails.yona.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nails.yona.enums.WorkingDay;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "working_hour")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Le jour est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkingDay day;

    private LocalTime startTime;
    private LocalTime endTime;

    @NotNull(message = "L'état d'ouverture est obligatoire")
    @Column(nullable = false)
    private Boolean isClosed;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}