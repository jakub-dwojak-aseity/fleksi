package pl.com.aseity.attendance.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.aseity.attendance.domain.Attendance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    Optional<Attendance> findByShiftIdAndUserId(UUID shiftId, UUID userId);
    
    List<Attendance> findByShiftId(UUID shiftId);
    
    List<Attendance> findByUserIdOrderByCreatedAtDesc(UUID userId);
    
    List<Attendance> findByApprovedByBusinessFalseOrderByCreatedAtAsc();
    
    boolean existsByShiftIdAndUserId(UUID shiftId, UUID userId);
}