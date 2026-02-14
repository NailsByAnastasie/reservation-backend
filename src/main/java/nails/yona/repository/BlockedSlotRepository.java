package nails.yona.repository;

import nails.yona.model.BlockedSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlockedSlotRepository extends JpaRepository<BlockedSlot, UUID> {
}
