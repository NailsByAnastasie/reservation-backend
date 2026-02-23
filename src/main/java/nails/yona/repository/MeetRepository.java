package nails.yona.repository;

import nails.yona.model.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface MeetRepository extends JpaRepository<Meet, UUID> {
    @Query("SELECT COUNT(m) > 0 FROM Meet m WHERE m.status != 'CANCELLED' AND m.dateStart < :end AND m.dateEnd > :start")
    boolean hasOverlap(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Modifying
    @Query("UPDATE Meet m SET m.prestation = null WHERE m.prestation.id = :prestationId")
    void unlinkPrestation(@Param("prestationId") UUID prestationId);

    @Modifying
    @Query("UPDATE Meet m SET m.client = null WHERE m.client.id = :clientId")
    void unlinkClient(@Param("clientId") UUID clientId);
}
