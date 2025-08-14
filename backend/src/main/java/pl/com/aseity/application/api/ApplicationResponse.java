package pl.com.aseity.application.api;

import lombok.Builder;
import lombok.Value;
import pl.com.aseity.application.domain.ApplicationStatus;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class ApplicationResponse {
    private UUID id;
    private UUID shiftId;
    private UUID userId;
    private ApplicationStatus status;
    private Instant createdAt;
}