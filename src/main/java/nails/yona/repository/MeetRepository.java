package nails.yona.repository;

import nails.yona.model.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeetRepository extends JpaRepository<Meet, UUID> {
}
