package nails.yona.repository;

import nails.yona.enums.WorkingDay;
import nails.yona.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, UUID> {
    Optional<WorkingHour> findByDay(WorkingDay day);
}
