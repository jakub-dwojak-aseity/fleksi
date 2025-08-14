package pl.com.aseity.payout.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import pl.com.aseity.common.domain.AuditableEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payouts")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Payout extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "attendance_id", nullable = false)
    private UUID attendanceId;

    @Column(name = "amount_gross", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountGross;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayoutStatus status = PayoutStatus.PENDING;

}