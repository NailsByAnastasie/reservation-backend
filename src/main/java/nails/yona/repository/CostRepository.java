package nails.yona.repository;

import nails.yona.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CostRepository extends JpaRepository<Cost, UUID> {
}
