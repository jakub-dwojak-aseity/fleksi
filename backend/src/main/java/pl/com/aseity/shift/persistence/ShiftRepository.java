package pl.com.aseity.shift.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.com.aseity.shift.domain.Shift;
import pl.com.aseity.shift.domain.ShiftStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, UUID>, QuerydslPredicateExecutor<Shift> {
    
    List<Shift> findByBusinessIdOrderByStartsAtDesc(UUID businessId);
    
    long countByBusinessIdAndStatus(UUID businessId, ShiftStatus status);
}