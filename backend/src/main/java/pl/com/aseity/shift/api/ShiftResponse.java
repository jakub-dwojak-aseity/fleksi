package pl.com.aseity.shift.api;

import lombok.Builder;
import lombok.Value;
import pl.com.aseity.shift.domain.ShiftStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class ShiftResponse {
    private UUID id;
    private UUID businessId;
    private String title;
    private String description;
    private BigDecimal hourlyRate;
    private Instant startsAt;
    private Instant endsAt;
    private String city;
    private String address;
    private Integer requiredCount;
    private ShiftStatus status;
    private Instant createdAt;
}