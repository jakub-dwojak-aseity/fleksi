package pl.com.aseity.payout.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.aseity.payout.domain.Payout;
import pl.com.aseity.payout.domain.PayoutStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayoutRepository extends JpaRepository<Payout, UUID> {

    List<Payout> findByUserIdOrderByCreatedAtDesc(UUID userId);
    
    Optional<Payout> findByAttendanceId(UUID attendanceId);
    
    List<Payout> findByStatusOrderByCreatedAtAsc(PayoutStatus status);
    
    boolean existsByAttendanceId(UUID attendanceId);
}