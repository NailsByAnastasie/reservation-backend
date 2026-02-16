package nails.yona.repository;

import nails.yona.model.BlockedSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface BlockedSlotRepository extends JpaRepository<BlockedSlot, UUID> {
    @Query("SELECT COUNT(b) > 0 FROM BlockedSlot b WHERE b.dateStart < :end AND b.dateEnd > :start")
    boolean hasOverlap(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
