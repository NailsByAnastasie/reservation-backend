package nails.yona.repository;

import nails.yona.model.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface MeetRepository extends JpaRepository<Meet, UUID> {
    @Query("SELECT COUNT(m) > 0 FROM Meet m WHERE m.status != 'CANCELLED' AND m.dateStart < :end AND m.dateEnd > :start")
    boolean hasOverlap(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
