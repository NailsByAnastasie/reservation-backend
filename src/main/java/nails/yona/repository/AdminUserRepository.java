package nails.yona.repository;

import nails.yona.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<AdminUser> findByEmailIgnoreCase(String email);
}
