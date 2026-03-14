package nails.yona.repository;

import nails.yona.model.BlockedSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BlockedSlotRepository extends JpaRepository<BlockedSlot, UUID> {

    @Query("SELECT COUNT(b) > 0 FROM BlockedSlot b WHERE b.dateStart < :end AND b.dateEnd > :start")
    boolean hasOverlap(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT b FROM BlockedSlot b WHERE b.dateStart <= :end AND b.dateEnd >= :start ORDER BY b.dateStart ASC")
    List<BlockedSlot> findSlotsBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Modifying
    @Query("DELETE FROM BlockedSlot b WHERE b.dateEnd < :date")
    void deleteByDateEndBefore(@Param("date") LocalDateTime date);
}
