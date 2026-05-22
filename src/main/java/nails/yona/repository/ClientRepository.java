package nails.yona.repository;

import nails.yona.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> findByEmailContainingIgnoreCase(String email);

    Boolean existsByEmailIgnoreCase(String email);
}
