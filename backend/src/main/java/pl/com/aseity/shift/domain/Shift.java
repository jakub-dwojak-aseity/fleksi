package pl.com.aseity.shift.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import pl.com.aseity.common.domain.AuditableEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shifts")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Shift extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal hourlyRate;

    @Column(nullable = false)
    private Instant startsAt;

    @Column(nullable = false)
    private Instant endsAt;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer requiredCount = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftStatus status = ShiftStatus.DRAFT;

}