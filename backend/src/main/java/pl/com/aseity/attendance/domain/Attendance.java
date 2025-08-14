package pl.com.aseity.attendance.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import pl.com.aseity.common.domain.AuditableEntity;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "attendances")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Attendance extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "shift_id", nullable = false)
    private UUID shiftId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "check_in_at")
    private Instant checkInAt;

    @Column(name = "check_out_at")
    private Instant checkOutAt;

    @Column(name = "approved_by_business", nullable = false)
    private Boolean approvedByBusiness = false;

}