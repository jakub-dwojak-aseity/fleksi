package pl.com.aseity.attendance.api;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class AttendanceResponse {
    private UUID id;
    private UUID shiftId;
    private UUID userId;
    private Instant checkInAt;
    private Instant checkOutAt;
    private Boolean approvedByBusiness;
}