package pl.com.aseity.business.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.aseity.business.domain.Business;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {

    Optional<Business> findByNip(String nip);
    
    boolean existsByNip(String nip);
    
    long countByVerified(boolean verified);
}