package pl.com.aseity.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.aseity.application.domain.Application;
import pl.com.aseity.application.domain.ApplicationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findByShiftIdOrderByCreatedAtAsc(UUID shiftId);
    
    List<Application> findByUserIdOrderByCreatedAtDesc(UUID userId);
    
    Optional<Application> findByShiftIdAndUserId(UUID shiftId, UUID userId);
    
    boolean existsByShiftIdAndUserId(UUID shiftId, UUID userId);
    
    long countByShiftIdAndStatus(UUID shiftId, ApplicationStatus status);
}