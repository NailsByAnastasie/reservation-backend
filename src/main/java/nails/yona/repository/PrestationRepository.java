package nails.yona.repository;

import nails.yona.model.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation, UUID> {

    List<Prestation> findByActiveTrueOrderByPriceAsc();
}
