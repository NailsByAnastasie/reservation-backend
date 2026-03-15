package nails.yona.repository;

import nails.yona.model.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeetRepository extends JpaRepository<Meet, UUID> {

    @Query("SELECT COUNT(m) > 0 FROM Meet m WHERE m.status != 'CANCELED' AND m.dateStart < :end AND m.dateEnd > :start")
    boolean hasOverlap(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Modifying
    @Query("UPDATE Meet m SET m.prestation = null WHERE m.prestation.id = :prestationId")
    void unlinkPrestation(@Param("prestationId") UUID prestationId);

    @Modifying
    @Query("UPDATE Meet m SET m.client = null WHERE m.client.id = :clientId")
    void unlinkClient(@Param("clientId") UUID clientId);

    @Query("SELECT m FROM Meet m WHERE m.status != 'CANCELED' AND m.dateStart >= :start AND m.dateStart <= :end ORDER BY m.dateStart ASC")
    List<Meet> findMeetsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
